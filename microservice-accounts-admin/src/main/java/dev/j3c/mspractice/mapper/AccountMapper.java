package dev.j3c.mspractice.mapper;

import dev.j3c.mspractice.collection.Account;
import dev.j3c.mspractice.dto.AccountDto;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class AccountMapper {

    public Function<Account, AccountDto> mapFromEntityToDto() {
        return (Account account) -> AccountDto.builder()
                .accountId(account.getAccountId())
                .userId(account.getUserId())
                .currentValue(account.getCurrentValue())
                .lastModificationDate(account.getLastModificationDate())
                .build();
    }

    public Function<AccountDto, Account> mapFromDtoToEntity() {
        return (AccountDto accountDto) -> Account.builder()
                .accountId(accountDto.getAccountId())
                .userId(accountDto.getUserId())
                .currentValue(accountDto.getCurrentValue())
                .lastModificationDate(accountDto.getLastModificationDate())
                .build();
    }
}
