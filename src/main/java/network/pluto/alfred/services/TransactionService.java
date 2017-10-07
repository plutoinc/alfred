package network.pluto.alfred.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TransactionService {
    @Value("${pluto.aws.sqs.tx_queue}")
    private String txQueueName;

    @JmsListener(destination = "${pluto.aws.sqs.tx_queue}")
    public void sendTransaction(String requestJSON) {
        log.debug(requestJSON);
    }

}
