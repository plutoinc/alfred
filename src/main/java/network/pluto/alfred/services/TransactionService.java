package network.pluto.alfred.services;


import network.pluto.alfred.transactions.TxRequest;
import network.pluto.bibliotheca.enums.TransactionStatus;
import network.pluto.bibliotheca.models.Member;
import network.pluto.bibliotheca.models.Transaction;

public interface TransactionService {
    public Transaction sendTransaction(Member member, TxRequest txRequest);
    public void updateTransactionStatus(long txId, TransactionStatus transactionStatus);
    public Transaction getTransaction(long transactionId);
}
