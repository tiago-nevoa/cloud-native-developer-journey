package com.sap.cc.users;

import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class InMemoryUserStorage implements UserStorage {

    private final Map<Long, User> users = new HashMap<>();

    public InMemoryUserStorage(List<User> defaultUsers) {
        defaultUsers.forEach(this::saveUser);
    }

    @Override
    public User saveUser(final User user) {
        boolean isUserIdEmptyOrNonExisting = user.getId() == null || !users.containsKey(user.getId());

        if (isUserIdEmptyOrNonExisting) {
            Long id = users.size() + 1L;
            user.setId(id);
        }

        users.put(user.getId(), user);
        return user;
    }

    @Override
    public Optional<User> retrieveUserById(Long id) {
        return Optional.ofNullable(users.get(id));
    }

    @Override
    public List<User> retrieveAllUsers() {
        return new ArrayList<>(users.values());
    }

    @Override
    public void deleteAllUsers() {
        users.clear();
    }

    @Override
    public void deleteUser(Long id) {
        users.remove(id);
    }
}
