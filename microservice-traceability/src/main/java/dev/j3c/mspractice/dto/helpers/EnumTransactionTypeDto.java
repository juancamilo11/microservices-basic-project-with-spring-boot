package dev.j3c.mspractice.dto.helpers;

public enum EnumTransactionTypeDto {
    DEPOSIT("Book"),
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
}
