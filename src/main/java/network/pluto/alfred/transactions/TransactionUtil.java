package network.pluto.alfred.transactions;

import com.fasterxml.jackson.core.JsonProcessingException;

public class TransactionUtil {
    public static String getJSONString(TxRequest txRequest) throws JsonProcessingException {
        switch (txRequest.getTxName()) {
            case CREATE_WALLET:
                return ((PlutoCreateWalletTxRequest) txRequest).toJson();
            default:
                return null;
        }
    }
}
