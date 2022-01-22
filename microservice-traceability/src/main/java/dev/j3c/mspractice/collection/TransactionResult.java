package dev.j3c.mspractice.collection;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Document
public class TransactionResult {

    @Id
    private String id;
    private String userId;
    private boolean hasErrors;
    private String message;
    private LocalDate date;

}
