package dev.j3c.mspractice.usecases;

import dev.j3c.mspractice.usecases.interfaces.VerifyUserExistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
@Valid
public class VerifyUserExistenceUsecase implements VerifyUserExistence {

    private static final Logger logger = LoggerFactory.getLogger(VerifyUserExistenceUsecase.class);

    @Value("${microservice.users.admin.url}")
    private String usersAdministrationMSBasicURL;

    @Override
    public Boolean apply(String userId) {
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest httpRequest = HttpRequest
                .newBuilder()
                .GET()
                .uri(URI.create(usersAdministrationMSBasicURL + "/get/exists-user/" + userId))
                .build();
        boolean userVerificationSuccessful = false;
        try {
            HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            if(httpResponse.statusCode() == 200)
                userVerificationSuccessful = true;
        } catch(IOException | InterruptedException e) {
            logger.error("[MS-ACCOUNTS_ADMIN] Error, el usuario con Id " + userId + " no existe en el sistema.");
        }
        return userVerificationSuccessful;
    }
}
