package com.sap.cc.users;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

public class UserStorageTest {

    private UserStorage userStorage = new InMemoryUserStorage(Collections.emptyList());

    private final User JOHN = new User("John Lennon", "51351 83511", "0");
    private final User RINGO = new User("Ringo Star", "31866 3516835", "1");

    @BeforeEach
    public void beforeEach() {
        userStorage.deleteAllUsers();
    }

    @Test
    public void testRetrieveUserByIdNonExistingUser() {
        Optional<User> returnedUser = userStorage.retrieveUserById(1L);
        assertThat(returnedUser.isPresent()).isEqualTo(false);
    }

    @Test
    public void testSaveUser() {
        User returnedUser = userStorage.saveUser(JOHN);

        assertThat(returnedUser.getName()).isEqualTo(JOHN.getName());
        assertThat(returnedUser.getId()).isEqualTo(1L);
    }

    @Test
    public void testSaveTwoUsers() {
        userStorage.saveUser(JOHN);

        User returnedUser = userStorage.saveUser(RINGO);

        assertThat(returnedUser.getName()).isEqualTo(RINGO.getName());
        assertThat(returnedUser.getId()).isEqualTo(2L);
    }

    @Test
    public void testSaveUserTryToForceId() {
        User user = JOHN;
        user.setId(10L);
        User returnedUser = userStorage.saveUser(user);

        assertThat(returnedUser.getId()).isEqualTo(1L);
    }

    @Test
    public void testSaveAndRetrieveUserById() {
        userStorage.saveUser(JOHN);

        Optional<User> returnedUser = userStorage.retrieveUserById(1L);

        if (returnedUser.isPresent()) {
            assertThat(returnedUser.get().getId()).isEqualTo(1L);
            assertThat(returnedUser.get().getName()).isEqualTo(JOHN.getName());
        } else {
            fail("retrieve user failed");
        }

    }

    @Test
    public void testUpdateTitleOfExistingUser() {
        User returnedUser = userStorage.saveUser(JOHN);
        assertThat(returnedUser.getId()).isEqualTo(1L);

        final String newAuthor = "Johnny Lennon";
        returnedUser.setName(newAuthor);

        userStorage.saveUser(returnedUser);

        assertThat(returnedUser.getName()).isEqualTo(newAuthor);
        assertThat(returnedUser.getId()).isEqualTo(1L);

    }

    @Test
    public void testGetAllEmpty() {
        List<User> returnedUsers = userStorage.retrieveAllUsers();
        assertThat(returnedUsers.size()).isEqualTo(0);
    }

    @Test
    public void testGetAllFirstOneThenTwoEntries() {

        userStorage.saveUser(JOHN);

        List<User> returnedUsers = userStorage.retrieveAllUsers();
        assertThat(returnedUsers.size()).isEqualTo(1);
        assertThat(returnedUsers.iterator().next().getName()).isEqualTo(JOHN.getName());
        assertThat(returnedUsers.iterator().next().getId()).isEqualTo(1L);

        userStorage.saveUser(RINGO);

        returnedUsers = userStorage.retrieveAllUsers();
        assertThat(returnedUsers.size()).isEqualTo(2);

    }

    @Test
    public void testDeleteSingle() {

        userStorage.saveUser(JOHN);
        userStorage.saveUser(RINGO);

        List<User> returnedUsers = userStorage.retrieveAllUsers();
        assertThat(returnedUsers.size()).isEqualTo(2);

        userStorage.deleteUser(1L);

        returnedUsers = userStorage.retrieveAllUsers();

        assertThat(returnedUsers.size()).isEqualTo(1);
        assertThat(returnedUsers.iterator().next().getName()).isEqualTo(RINGO.getName());
        assertThat(returnedUsers.iterator().next().getId()).isEqualTo(2L);

    }

    @Test
    public void testDeleteAll() {

        userStorage.saveUser(JOHN);
        userStorage.saveUser(RINGO);

        List<User> returnedUsers = userStorage.retrieveAllUsers();
        assertThat(returnedUsers.size()).isEqualTo(2);

        userStorage.deleteAllUsers();

        returnedUsers = userStorage.retrieveAllUsers();
        assertThat(returnedUsers.size()).isEqualTo(0);
    }
}
