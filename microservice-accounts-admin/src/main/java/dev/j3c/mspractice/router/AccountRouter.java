package dev.j3c.mspractice.router;

import dev.j3c.mspractice.dto.AccountDto;
import dev.j3c.mspractice.usecases.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.function.Function;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class AccountRouter {

    private static final Logger logger = LoggerFactory.getLogger(AccountRouter.class);

    @Bean
    public RouterFunction<ServerResponse> addAccountRoute(AddAccountUsecase addAccountUsecase) {
        Function<AccountDto, Mono<ServerResponse>> executor = (AccountDto accountDto) ->  addAccountUsecase
                .apply(accountDto)
                .doOnNext(account -> logger.info("[MS-ACCOUNTS_ADMIN] Add New Account"))
                .flatMap(account -> ServerResponse
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(account));
        return route(POST("/post/create-account")
                .and(accept(MediaType.APPLICATION_JSON)), request -> request
                .bodyToMono(AccountDto.class)
                .flatMap(executor));
    }

    @Bean
    public RouterFunction<ServerResponse> getAccountByIdRoute(GetAccountByIdUsecase getAccountByIdUsecase) {
        return route(GET("/get/account/{id}")
                .and(accept(MediaType.APPLICATION_JSON)), request -> ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromPublisher(getAccountByIdUsecase.apply(request.pathVariable("id"))
                        .doOnNext(account -> logger.info("[MS-ACCOUNTS_ADMIN] Get Account By Id")), AccountDto.class)));
    }

    @Bean
    public RouterFunction<ServerResponse> getAccountByUserIdRoute(GetAccountsByUserIdUsecase getAccountsByUserIdUsecase) {
        return route(GET("/get/accounts-user/{id}")
                .and(accept(MediaType.APPLICATION_JSON)), request -> ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromPublisher(getAccountsByUserIdUsecase.apply(request.pathVariable("id"))
                        .doOnNext(account -> logger.info("[MS-ACCOUNTS_ADMIN] Get Account By Id")), AccountDto.class)));
    }

    @Bean
    public RouterFunction<ServerResponse> getAllAccountsRoute(GetAllAccountsUsecase getAllAccountsUsecase) {
        return route(GET("/get/accounts")
                .and(accept(MediaType.APPLICATION_JSON)), request ->
                ServerResponse
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(getAllAccountsUsecase.get()
                                .doOnNext(account -> logger.info("[MS-ACCOUNTS_ADMIN] Get All Accounts")), AccountDto.class)));
    }

    @Bean
    public RouterFunction<ServerResponse> deleteAccountByIdRoute(DeleteAccountByIdUsecase deleteAccountByIdUsecase) {
        return route(DELETE("/delete/account/{id}")
                .and(accept(MediaType.APPLICATION_JSON)), request -> ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromPublisher(deleteAccountByIdUsecase.accept(request.pathVariable("id"))
                        .doOnNext(result -> logger.info("[MS-ACCOUNTS_ADMIN] Delete Account By Id")), Void.class)));
    }

}
