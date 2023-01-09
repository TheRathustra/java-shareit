package ru.practicum.shareit.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.user.dto.UserDto;

import java.util.List;

@Component
public class UserServiceImpl implements UserService {

    private UserStorage userStorage;

    @Autowired
    public UserServiceImpl(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    @Override
    public User add(UserDto userDto) {
        return userStorage.add(userDto);
    }

    @Override
    public User update(long id, UserDto userDto) {
        return userStorage.update(id, userDto);
    }

    @Override
    public void delete(long id) {
        userStorage.delete(id);
    }

    @Override
    public User getUserById(long id) {
        return userStorage.getUserById(id);
    }

    @Override
    public List<User> getUsers() {
        return userStorage.getUsers();
    }

}
