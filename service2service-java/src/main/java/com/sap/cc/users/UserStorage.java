package com.sap.cc.users;

import java.util.List;
import java.util.Optional;

public interface UserStorage {

    User saveUser(User user);

    Optional<User> retrieveUserById(Long id);

    List<User> retrieveAllUsers();

    void deleteUser(Long id);

    void deleteAllUsers();
}
