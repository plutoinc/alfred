package network.pluto.alfred.services;


import network.pluto.alfred.transactions.TxRequest;
import network.pluto.bibliotheca.enums.TransactionStatus;
import network.pluto.bibliotheca.models.Member;
import network.pluto.bibliotheca.models.Transaction;

public interface TransactionService {
    void sendTransaction(Member member, TxRequest<?> txRequest);
    void updateTransactionStatus(long txId, TransactionStatus transactionStatus);
    Transaction getTransaction(long transactionId);
    Transaction saveTransaction(Transaction transaction);
}
