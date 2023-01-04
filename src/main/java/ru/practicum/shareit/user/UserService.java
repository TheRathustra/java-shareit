package ru.practicum.shareit.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.user.dto.UserDto;

import java.util.List;

@Component
public class UserService {

    private UserStorage userStorage;

    @Autowired
    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public User add(UserDto userDto) {
        return userStorage.add(userDto);
    }

    public User update(long id, UserDto userDto) {
        return userStorage.update(id, userDto);
    }

    public void delete(long id) {
        userStorage.delete(id);
    }

    public User getUserById(long id) {
        return userStorage.getUserById(id);
    }

    public List<User> getUsers() {
        return userStorage.getUsers();
    }

}
