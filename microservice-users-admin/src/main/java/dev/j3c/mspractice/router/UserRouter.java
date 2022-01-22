package dev.j3c.mspractice.router;

import dev.j3c.mspractice.dto.UserDto;
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
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class UserRouter {

    private static final Logger logger = LoggerFactory.getLogger(UserRouter.class);

    @Bean
    public RouterFunction<ServerResponse> addUserRoute(AddUserUsecase addUserUsecase) {
        Function<UserDto, Mono<ServerResponse>> executor = (UserDto userDto) ->  addUserUsecase
                .apply(userDto)
                .doOnNext(user -> logger.info("[MS-ADMIN_USERS] Add New User"))
                .flatMap(user -> ServerResponse
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(user));
        return route(POST("/post/create-user")
                .and(accept(MediaType.APPLICATION_JSON)), request -> request
                .bodyToMono(UserDto.class)
                .flatMap(executor));
    }

    @Bean
    public RouterFunction<ServerResponse> getUserByIdRoute(GetUserByIdUsecase getUserByIdUsecase) {
        return route(GET("/get/user/{id}")
                .and(accept(MediaType.APPLICATION_JSON)), request -> ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromPublisher(getUserByIdUsecase.apply(request.pathVariable("id"))
                        .doOnNext(user -> logger.info("[MS-ADMIN_USERS] Get User By Id")), UserDto.class)));
    }

    @Bean
    public RouterFunction<ServerResponse> getAllUsersRoute(GetAllUsersUsecase getAllUsersUsecase) {
        return route(GET("/get/users")
                .and(accept(MediaType.APPLICATION_JSON)), request ->
                ServerResponse
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(getAllUsersUsecase.get()
                                .doOnNext(user -> logger.info("[MS-ADMIN_USERS] Get User All Users")), UserDto.class)));
    }

}
