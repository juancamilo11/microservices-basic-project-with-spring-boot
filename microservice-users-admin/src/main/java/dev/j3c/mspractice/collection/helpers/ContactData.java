package dev.j3c.mspractice.collection.helpers;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class ContactData {

    private String address;
    private String email;
    private String phoneNumber;

}
