package network.pluto.alfred.transactions;

import lombok.Data;

@Data
public class TxRequest {
    private PlutoTxName txName;

    public TxRequest(PlutoTxName txName) {
        this.txName = txName;
    }
}
