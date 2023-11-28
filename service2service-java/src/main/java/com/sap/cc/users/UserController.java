package com.sap.cc.users;

import com.sap.cc.NotFoundException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private PrettyUserPageCreator prettyUserPageCreator;
    private UserStorage userStorage;

    public UserController(UserStorage userStorage, PrettyUserPageCreator prettyUserPageCreator) {
        this.userStorage = userStorage;
        this.prettyUserPageCreator = prettyUserPageCreator;
    }

    @GetMapping("/pretty/{id}")
    public ResponseEntity<String> printPrettyPage(@PathVariable("id") Long id) {

        if (id < 1) {
            throw new IllegalArgumentException("Id must not be less than 1");
        }

        Optional<User> user = userStorage.retrieveUserById(id);

        if (user.isPresent()) {

            String prettyPage = prettyUserPageCreator.getPrettyPage(user.get());

            return ResponseEntity.ok().contentType(MediaType.TEXT_PLAIN).body(prettyPage);
        }

        throw new NotFoundException();
    }
}
