package dev.j3c.mspractice.usecases;

import dev.j3c.mspractice.collection.Account;
import dev.j3c.mspractice.mapper.AccountMapper;
import dev.j3c.mspractice.repository.AccountRepository;
import dev.j3c.mspractice.usecases.interfaces.DeleteAccountById;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

@Service
@Validated
public class DeleteAccountByIdUsecase implements DeleteAccountById {

    private final AccountRepository accountRepository;

    @Autowired
    public DeleteAccountByIdUsecase(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    private Mono<Void> hasTheAccountAnyFunds(String accountId, Account account) {
        return account.getCurrentValue() == 0.0D ?
                this.accountRepository.deleteById(accountId).then() :
                Mono.error(new IllegalArgumentException("Error, Cannot delete an account with funds"));
    }

    @Override
    public Mono<Void> accept(String accountId) {
        return this.accountRepository.existsById(accountId)
                .flatMap(exists -> Boolean.TRUE.equals(exists) ?
                        this.accountRepository.findById(accountId)
                                .flatMap(account -> hasTheAccountAnyFunds(accountId, account)) :
                        Mono.error(new IllegalArgumentException("The account with id " + accountId + " doesn't exist")));
    }

}
