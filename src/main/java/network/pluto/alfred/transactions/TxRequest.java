package network.pluto.alfred.transactions;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import network.pluto.bibliotheca.models.Transaction;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@ToString(exclude = "transaction")
@EqualsAndHashCode
public class TxRequest<T> {

    // metadata
    private long memberId;
    private TxName txName;
    private LocalDateTime createdAt;

    // transaction data
    private T data;

    @JsonIgnore
    private Transaction transaction;

    public static <T> TxRequest<T> create(long memberId, TxName txName, T data) {
        TxRequest<T> txRequest = new TxRequest<>();
        txRequest.memberId = memberId;
        txRequest.txName = txName;
        txRequest.createdAt = LocalDateTime.now();
        txRequest.data = data;
        return txRequest;
    }

    public <S> TxRequest<S> convert(Class<S> dataClass) {
        TxRequest<S> txRequest = new TxRequest<>();
        txRequest.memberId = this.memberId;
        txRequest.txName = this.txName;
        txRequest.createdAt = this.createdAt;

        // convert data
        txRequest.data = JsonUtil.convert(this.data, dataClass);

        return txRequest;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }
}
