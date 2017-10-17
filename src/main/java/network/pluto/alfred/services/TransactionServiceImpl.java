package network.pluto.alfred.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import network.pluto.alfred.transactions.PlutoCreateWalletTxRequest;
import network.pluto.alfred.transactions.TxRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TransactionServiceImpl implements TransactionService {
    private final JmsTemplate jmsTemplate;

    @Value("${pluto.aws.sqs.tx_queue}")
    private String txQueueName;

    @Autowired
    public TransactionServiceImpl(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    @JmsListener(destination = "${pluto.aws.sqs.tx_queue}")
    public void handleTxReq(String requestJSON) {
        log.debug(requestJSON);
    }

    @Override
    public Boolean sendTxReq(TxRequest transaction) {
        try {
            String jsonStr = this.getJSONString(transaction);
            this.jmsTemplate.convertAndSend(this.txQueueName, jsonStr);
        } catch (JsonProcessingException e) {
            log.error(e.getLocalizedMessage());
            
            return false;
        }

        return true;
    }

    private String getJSONString(TxRequest txRequest) throws JsonProcessingException {
        switch (txRequest.getTxName()) {
            case CREATE_WALLET:
                return ((PlutoCreateWalletTxRequest) txRequest).toJson();
            default:
                return null;
        }
    }
}
