package dev.j3c.mspractice.dto;

import dev.j3c.mspractice.dto.helpers.EnumTransactionTypeDto;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@Builder(toBuilder = true)
public class TransactionDto {

    @NotBlank
    private String id;
    @NotNull
    private String type;
    @NotBlank
    private String userId;
    @NotBlank
    private String accountId;
    @NotNull
    @Min(value = 0)
    private double value;
    @NotNull
    @DateTimeFormat(style = "yyyy-MM-dd")
    private LocalDate date;

    public TransactionDto(String id, String type, String userId, String accountId, double value, LocalDate date) {
        this.id = id;
        this.type = type;
        this.userId = userId;
        this.accountId = accountId;
        this.value = value;
        this.date = date;
        this.validateItemFormat(type);
    }

    private void validateItemFormat(String format) throws IllegalArgumentException {
        if(!EnumTransactionTypeDto.enumValueIsValid(format)) {
            throw new IllegalArgumentException("El tipo de transacción no es válido");
        }
    }
}




