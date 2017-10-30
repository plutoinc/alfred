package network.pluto.alfred.transactions;

import lombok.extern.slf4j.Slf4j;
import network.pluto.alfred.services.MemberService;
import network.pluto.alfred.services.TransactionService;
import network.pluto.bibliotheca.enums.TransactionStatus;
import network.pluto.bibliotheca.models.Member;
import network.pluto.bibliotheca.models.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class TransactionHandlerManager {

    private final List<TransactionHandler> transactionHandlers;
    private final TransactionService transactionService;
    private final MemberService memberService;

    @Autowired
    public TransactionHandlerManager(List<TransactionHandler> transactionHandlers,
                                     TransactionService transactionService,
                                     MemberService memberService) {
        this.transactionHandlers = transactionHandlers;
        this.transactionService = transactionService;
        this.memberService = memberService;
    }

    private void handleTransaction(TxRequest<?> txRequest) throws Exception {
        for (TransactionHandler transactionHandler : transactionHandlers) {
            if (!transactionHandler.supports(txRequest)) {
                continue;
            }

            transactionHandler.handleTransaction(txRequest);
            return;
        }

        throw new NoTransactionHandlerException(txRequest);
    }

    @JmsListener(destination = "${pluto.aws.sqs.tx_queue}")
    public void handleTxReq(String jsonStr) {
        try {
            TxRequest<?> txRequest = JsonUtil.fromJson(jsonStr, TxRequest.class);

            Transaction transaction = registerTransaction(jsonStr, txRequest);
            txRequest.setTransaction(transaction);

            handleTransaction(txRequest);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    private Transaction registerTransaction(String jsonStr, TxRequest<?> txRequest) {
        Member member = memberService.getMemberById(txRequest.getMemberId());

        Transaction transaction = new Transaction();
        transaction.setMember(member);
        transaction.setTransactionData(jsonStr);
        transaction.setTransactionStatus(TransactionStatus.TX_REQUESTED);

        // save transaction
        transaction = transactionService.saveTransaction(transaction);
        return transaction;
    }
}
