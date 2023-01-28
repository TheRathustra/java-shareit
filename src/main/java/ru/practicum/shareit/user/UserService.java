package ru.practicum.shareit.user;

import ru.practicum.shareit.user.model.User;

import java.util.List;

public interface UserService {

    User add(User user);

    User update(long id, User user);

    void delete(long id);

    User getUserById(long id);

    List<User> getUsers();

}
