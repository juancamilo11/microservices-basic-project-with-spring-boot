package dev.j3c.mspractice.dto.helpers;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class ContactDataDto {

    @NotBlank
    private String address;
    @NotBlank
    private String email;
    @NotBlank
    private String phoneNumber;

}
