package dev.j3c.mspractice.collection;

import dev.j3c.mspractice.collection.helpers.ContactData;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Document
public class User {

    @Id
    private String id;
    private String firstName;
    private String lastName;
    private Long age;
    private ContactData contactData;

}
