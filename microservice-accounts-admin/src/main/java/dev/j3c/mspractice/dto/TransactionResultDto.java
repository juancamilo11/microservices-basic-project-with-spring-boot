package dev.j3c.mspractice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class TransactionResultDto {

    @NotBlank
    private String id;
    @NotBlank
    private String userId;
    private boolean hasErrors;
    @NotBlank
    private String message;
    @NotNull
    @DateTimeFormat(style = "yyyy-MM-dd")
    private LocalDate date;

}
