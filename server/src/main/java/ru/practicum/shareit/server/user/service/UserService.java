package ru.practicum.shareit.server.user.service;

import ru.practicum.shareit.server.user.model.User;

import java.util.List;

public interface UserService {

    User add(User user);

    User update(Long id, User user);

    void delete(Long id);

    User getUserById(Long id);

    List<User> getUsers();

}
