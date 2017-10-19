package network.pluto.alfred.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import network.pluto.bibliotheca.enums.TransactionStatus;
import network.pluto.bibliotheca.models.Transaction;

@NoArgsConstructor
@Data
public class TransactionDto {
    @ApiModelProperty(readOnly = true)
    private Long id;

    private TransactionStatus transactionStatus;

    private String transactionData;

    public TransactionDto(Transaction transaction) {
        this.id = transaction.getId();
        this.transactionStatus = transaction.getTransactionStatus();
        this.transactionData = transaction.getTransactionData();
    }
}
