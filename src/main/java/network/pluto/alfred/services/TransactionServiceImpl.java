package network.pluto.alfred.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import network.pluto.alfred.transactions.JsonUtil;
import network.pluto.alfred.transactions.TxRequest;
import network.pluto.bibliotheca.enums.TransactionStatus;
import network.pluto.bibliotheca.models.Member;
import network.pluto.bibliotheca.models.Transaction;
import network.pluto.bibliotheca.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final JmsTemplate jmsTemplate;

    @Value("${pluto.aws.sqs.tx_queue}")
    private String txQueueName;

    @Autowired
    public TransactionServiceImpl(TransactionRepository transactionRepository,
                                  JmsTemplate jmsTemplate) {
        this.transactionRepository = transactionRepository;
        this.jmsTemplate = jmsTemplate;
    }

    @Override
    public void sendTransaction(Member member, TxRequest<?> txRequest) {
        try {
            String jsonStr = JsonUtil.toJson(txRequest);
            this.jmsTemplate.convertAndSend(this.txQueueName, jsonStr);
        } catch (JsonProcessingException e) {
            log.error(e.getLocalizedMessage());
        }
    }

    @Transactional
    @Override
    public void updateTransactionStatus(long txId, TransactionStatus transactionStatus) {
        Transaction transaction = this.transactionRepository.getOne(txId);
        transaction.setTransactionStatus(transactionStatus);
    }

    @Override
    public Transaction getTransaction(long transactionId) {
        return this.transactionRepository.findOne(transactionId);
    }

    @Transactional
    @Override
    public Transaction saveTransaction(Transaction transaction) {
        return this.transactionRepository.save(transaction);
    }
}
