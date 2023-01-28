package ru.practicum.shareit.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.user.model.User;

import java.util.List;

@Component
public class UserServiceImpl implements UserService {

    private final UserStorage userStorage;

    @Autowired
    public UserServiceImpl(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    @Override
    public User add(User user) {
        return userStorage.add(user);
    }

    @Override
    public User update(long id, User user) {
        return userStorage.update(id, user);
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
