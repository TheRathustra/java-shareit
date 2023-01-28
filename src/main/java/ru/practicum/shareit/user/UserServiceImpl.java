package ru.practicum.shareit.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserServiceImpl implements UserService {

    private UserStorage userStorage;

    @Autowired
    public UserServiceImpl(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    @Override
    public User add(User user) {
        User addedUser = userStorage.add(user);
        return addedUser;
    }

    @Override
    public User update(long id, User user) {
        User updatedUser = userStorage.update(id, user);
        return updatedUser;
    }

    @Override
    public void delete(long id) {
        userStorage.delete(id);
    }

    @Override
    public User getUserById(long id) {
        User user = userStorage.getUserById(id);
        return user;
    }

    @Override
    public List<User> getUsers() {
        List<User> users = userStorage.getUsers();
        return users;
    }

}
