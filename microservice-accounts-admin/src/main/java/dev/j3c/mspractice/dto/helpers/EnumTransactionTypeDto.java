package dev.j3c.mspractice.dto.helpers;

import java.util.Arrays;

public enum EnumTransactionTypeDto {
    DEPOSIT("Deposit"),
    WITHDRAWAL("Withdrawal");

    private final String type;

    EnumTransactionTypeDto(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public EnumTransactionTypeDto getEnumByStringType(String type) {
        return EnumTransactionTypeDto.valueOf(type);
    }

    public static boolean enumValueIsValid(String type) {
        return Arrays
                .stream(EnumTransactionTypeDto.values())
                .anyMatch(enumValue -> enumValue.getType().equalsIgnoreCase(type));
    }
}
