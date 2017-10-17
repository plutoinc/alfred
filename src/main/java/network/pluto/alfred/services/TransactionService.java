package network.pluto.alfred.services;


import network.pluto.alfred.transactions.TxRequest;

public interface TransactionService {
    public Boolean sendTxReq(TxRequest txRequest);
}
