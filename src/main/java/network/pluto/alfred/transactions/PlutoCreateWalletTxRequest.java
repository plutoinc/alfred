package network.pluto.alfred.transactions;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.IOException;

@NoArgsConstructor
@Data
public class PlutoCreateWalletTxRequest extends TxRequest {
    private long memberId;
    private long walletId;

    public PlutoCreateWalletTxRequest(long memberId, long walletId) {
        super(PlutoTxName.CREATE_WALLET);
        this.memberId = memberId;
        this.walletId = walletId;
    }

    public String toJson() throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(this);
    }

    public static PlutoCreateWalletTxRequest fromJson(String jsonStr) throws IOException {
        return new ObjectMapper().readValue(jsonStr, PlutoCreateWalletTxRequest.class);
    }
}
