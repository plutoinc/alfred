package network.pluto.alfred.transactions;

public interface TransactionHandler {
    void handleTransaction(TxRequest<?> txRequest) throws Exception;
    boolean supports(TxRequest<?> txRequest);
}
