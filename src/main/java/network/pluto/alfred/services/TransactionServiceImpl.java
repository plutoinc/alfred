package network.pluto.alfred.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import network.pluto.alfred.transactions.PlutoCreateWalletTxRequest;
import network.pluto.alfred.transactions.TransactionUtil;
import network.pluto.alfred.transactions.TxRequest;
import network.pluto.bibliotheca.enums.TransactionStatus;
import network.pluto.bibliotheca.models.Member;
import network.pluto.bibliotheca.models.Transaction;
import network.pluto.bibliotheca.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

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
    public Transaction sendTransaction(Member member, TxRequest txRequest) {
        try {
            String jsonStr = TransactionUtil.getJSONString(txRequest);

            Transaction transaction = new Transaction();
            transaction.setMember(member);
            transaction.setTransactionData(jsonStr);
            transaction.setTransactionStatus(TransactionStatus.TX_REQUESTED);

            transaction = this.transactionRepository.save(transaction);

            this.jmsTemplate.convertAndSend(this.txQueueName, transaction.getId());

            return transaction;
        } catch (JsonProcessingException e) {
            log.error(e.getLocalizedMessage());

            return null;
        }
    }

    @Override
    public void updateTransactionStatus(long txId, TransactionStatus transactionStatus) {
        Transaction transaction = this.transactionRepository.getOne(txId);
        if (transaction != null) {
            transaction.setTransactionStatus(transactionStatus);
            this.transactionRepository.save(transaction);
        }
    }

    @Override
    public Transaction getTransaction(long transactionId) {
        return this.transactionRepository.findOne(transactionId);
    }
}
