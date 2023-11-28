package com.sap.cc.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    List<User> users = Arrays.asList(
            new User("Jane", "en-US"),
            new User("Juan Pérez", "es-ES"),
            new User("Erika Mustermann", "de-DE")
    );

    @GetMapping("/{id}")
    public ResponseEntity<User> findUserById(@PathVariable("id") Long id) {
        try {
            int index = id.intValue() - 1;
            logger.info("Fetching user for id {} at index {}", id, index);
            return ResponseEntity.ok(users.get(index));
        } catch (IndexOutOfBoundsException ex) {
            logger.warn("No user found for id {}", id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage(), ex);
        }
    }

    @GetMapping
    public ResponseEntity<List<User>> findAllUsers() {
        return ResponseEntity.ok(users);
    }
}
