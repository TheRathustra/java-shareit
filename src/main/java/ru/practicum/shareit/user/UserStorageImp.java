package ru.practicum.shareit.user;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.user.error.DuplicateEmailException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class UserStorageImp implements UserStorage {

    private long id = 1;
    private Map<Long, User> users = new HashMap<>();

    @Override
    public User getUserById(long id) {

        User user = users.get(id);

        if (user == null) {
            throw new IllegalArgumentException();
        }
        return user;

    }

    @Override
    public List<User> getUsers() {
        return new ArrayList<>(users.values());
    }

    @Override
    public User add(User user) {
        emailIsDuplicate(user);

        user.setId(id);
        users.put(id, user);

        id++;
        return user;
    }

    @Override
    public User update(long id, User user) {

        user.setId(id);
        emailIsDuplicate(user);

        User userDB = users.get(id);

        if (userDB == null) {
            throw new IllegalArgumentException();
        }

        if (user.getName() != null) {
            userDB.setName(user.getName());
        }
        if (user.getEmail() != null) {
            userDB.setEmail(user.getEmail());
        }

        return userDB;

    }

    @Override
    public void delete(long id) {

        User user = users.get(id);
        if (user == null) {
            throw new IllegalArgumentException();
        }

        users.remove(id);

    }

    private void emailIsDuplicate(User user) {

        if (user.getEmail() == null || users.values().isEmpty()) {
            return;
        }

        for (User userDB : users.values()) {
            if (user.getEmail().equals(userDB.getEmail())
                    && user.getId() != userDB.getId()) {
                    throw new DuplicateEmailException();
            }
        }

    }

}
