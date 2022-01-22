package dev.j3c.mspractice.usecases;

import dev.j3c.mspractice.dto.AccountDto;
import dev.j3c.mspractice.mapper.AccountMapper;
import dev.j3c.mspractice.repository.AccountRepository;
import dev.j3c.mspractice.usecases.interfaces.GetAccountById;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

@Service
@Validated
public class GetAccountByIdUsecase implements GetAccountById {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

    @Autowired
    public GetAccountByIdUsecase(AccountRepository accountRepository, AccountMapper accountMapper) {
        this.accountRepository = accountRepository;
        this.accountMapper = accountMapper;
    }

    @Override
    public Mono<AccountDto> apply(String id) {
        return this.accountRepository
                .findById(id)
                .map(account -> this.accountMapper
                        .mapFromEntityToDto()
                        .apply(account));
    }

}
