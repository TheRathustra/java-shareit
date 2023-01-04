package ru.practicum.shareit.user;

import ru.practicum.shareit.user.dto.UserDto;

import java.util.List;

public interface UserStorage {

    User add(UserDto userDto);

    User update(long id, UserDto userDto);

    void delete(long id);

    User getUserById(long id);

    List<User> getUsers();
}
