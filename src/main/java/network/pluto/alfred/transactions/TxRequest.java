package network.pluto.alfred.transactions;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.IOException;

@NoArgsConstructor
@Data
public class TxRequest {
    private PlutoTxName txName;

    public TxRequest(PlutoTxName txName) {
        this.txName = txName;
    }

    public static TxRequest fromJson(String jsonStr) throws IOException {
        return new ObjectMapper().readValue(jsonStr, TxRequest.class);
    }
}
