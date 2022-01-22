package dev.j3c.mspractice.usecases;

import dev.j3c.mspractice.dto.AccountDto;
import dev.j3c.mspractice.mapper.AccountMapper;
import dev.j3c.mspractice.repository.AccountRepository;
import dev.j3c.mspractice.usecases.interfaces.GetAccountsByUserId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Flux;

@Service
@Validated
public class GetAccountsByUserIdUsecase implements GetAccountsByUserId {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

    @Autowired
    public GetAccountsByUserIdUsecase(AccountRepository accountRepository, AccountMapper accountMapper) {
        this.accountRepository = accountRepository;
        this.accountMapper = accountMapper;
    }

    @Override
    public Flux<AccountDto> apply(String userId) {
        return this.accountRepository
                .findAllByUserId(userId)
                .map(account -> this.accountMapper
                        .mapFromEntityToDto()
                        .apply(account));
    }

}
