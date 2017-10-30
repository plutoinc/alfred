package network.pluto.alfred.transactions;

import lombok.Getter;

@Getter
public class NoTransactionHandlerException extends RuntimeException {

    private TxRequest<?> txRequest;

    public NoTransactionHandlerException(TxRequest<?> txRequest) {
        this("no transaction handler found for tx request", txRequest);
    }

    public NoTransactionHandlerException(String message, TxRequest<?> txRequest) {
        super(message);
        this.txRequest = txRequest;
    }
}
