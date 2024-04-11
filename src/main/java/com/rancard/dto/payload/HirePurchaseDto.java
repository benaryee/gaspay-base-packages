package com.rancard.dto.payload;

import com.rancard.mongo.HirePurchase;
import com.rancard.mongo.Payment;
import com.rancard.mongo.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@SuperBuilder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
public class HirePurchaseDto<S,R>  implements Serializable {
    private String id;
    private String agreementId;
    private UserDto user;
    private BigDecimal amount;
    private BigDecimal outstandingAmount;
    private LocalDateTime dueDate;
    private boolean isPaid;
    private List<PaymentDto<S,R>> payments;


}
