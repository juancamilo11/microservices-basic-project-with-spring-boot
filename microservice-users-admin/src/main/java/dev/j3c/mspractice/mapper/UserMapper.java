package dev.j3c.mspractice.mapper;

import dev.j3c.mspractice.collection.User;
import dev.j3c.mspractice.collection.helpers.ContactData;
import dev.j3c.mspractice.dto.UserDto;
import dev.j3c.mspractice.dto.helpers.ContactDataDto;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class UserMapper {

    public Function<UserDto, User> mapFromDtoToEntity() {
        return (UserDto userDto) -> User.builder()
                .id(userDto.getId())
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .age(userDto.getAge())
                .contactData(ContactData
                        .builder()
                        .address(userDto
                                .getContactDataDto()
                                .getAddress())
                        .email(userDto
                                .getContactDataDto()
                                .getEmail())
                        .phoneNumber(userDto
                                .getContactDataDto()
                                .getPhoneNumber())
                        .build())
                .build();
    }

    public Function<User, UserDto> mapFromEntityToDto() {
        return (User user) -> UserDto.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .age(user.getAge())
                .contactDataDto(ContactDataDto
                        .builder()
                        .address(user
                                .getContactData()
                                .getAddress())
                        .email(user
                                .getContactData()
                                .getEmail())
                        .phoneNumber(user
                                .getContactData()
                                .getPhoneNumber())
                        .build())
                .build();
    }
}
