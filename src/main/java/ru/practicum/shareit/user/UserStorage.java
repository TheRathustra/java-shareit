package ru.practicum.shareit.user;

import ru.practicum.shareit.user.model.User;

import java.util.List;

public interface UserStorage {

    User getUserById(long id);

    User add(User user);

    User update(long id, User user);

    void delete(long id);

    List<User> getUsers();
}
