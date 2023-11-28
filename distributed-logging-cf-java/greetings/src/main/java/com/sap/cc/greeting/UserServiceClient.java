package com.sap.cc.greeting;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientException;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class UserServiceClient {

    @Value("${USERS_URL:#{null}}")
    private String usersUrl;

    @Value("${USERS_SERVICE_HOST:localhost}")
    private String usersServiceHost;

    @Value("${USERS_SERVICE_PORT:8081}")
    private String usersServicePort;

    @Autowired
    WebClient webClient;

    Logger logger = LoggerFactory.getLogger(getClass());

    public User findById(Long id) {
        String userServicePath = getUserServiceUrl();
        logger.info("users host: {}", userServicePath);
        try {
            logger.info("Requesting User-Service to get user by id {}", id);
            User user = webClient.get()
                    .uri(userServicePath + id)
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(User.class)
                    .block();

            logger.info("Retrieved user from User-Service: {}", user.toString());
            return user;

        } catch (WebClientException webClientException) {
            logger.warn("User-Service did not return a user. Using default user.", webClientException);
            return new User("User", "en-US");
        }
    }

    private String getUserServiceUrl() {
        if(usersUrl != null){
            return usersUrl;
        }
        return "http://" + usersServiceHost + ":" + usersServicePort + "/api/v1/users/";
    }

    public List<User> getAllUsers() {

        String userServicePath = getUserServiceUrl();
        try {
            logger.info("Requesting User-Service to get all users");
            ResponseEntity<User[]> users = webClient.get()
                    .uri(userServicePath)
                    .retrieve()
                    .toEntity(User[].class)
                    .block();

            logger.info("Retrieved all users from User-Service: {}", users.getBody());
            return Arrays.asList(users.getBody());

        } catch (WebClientException webClientException) {
            logger.warn("User-Service did not return all users. Check if the User-Service is healthy");
            return Collections.singletonList(new User("defaultUser", "en-US"));
        }
    }
}
