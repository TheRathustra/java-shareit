package ru.practicum.shareit.user;

import java.util.List;

public interface UserStorage {

    User add(User user);

    User update(long id, User user);

    void delete(long id);

    User getUserById(long id);

    List<User> getUsers();
}
