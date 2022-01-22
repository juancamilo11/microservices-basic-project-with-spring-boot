package dev.j3c.mspractice.usecases;

import dev.j3c.mspractice.dto.AccountDto;
import dev.j3c.mspractice.mapper.AccountMapper;
import dev.j3c.mspractice.repository.AccountRepository;
import dev.j3c.mspractice.usecases.interfaces.GetAllAccounts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Flux;

@Service
@Validated
public class GetAllAccountsUsecase implements GetAllAccounts {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

    @Autowired
    public GetAllAccountsUsecase(AccountRepository accountRepository, AccountMapper accountMapper) {
        this.accountRepository = accountRepository;
        this.accountMapper = accountMapper;
    }

    @Override
    public Flux<AccountDto> get() {
        return this.accountRepository
                .findAll()
                .map(account -> this.accountMapper
                        .mapFromEntityToDto()
                        .apply(account));
    }

}
