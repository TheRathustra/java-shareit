package ru.practicum.shareit.user;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.user.dto.UserDto;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserStorageImp implements UserStorage {

    private long id = 1;
    private List<User> users = new ArrayList<>();

    @Override
    public User getUserById(long id) {

        User user = null;
        for (User userDB : users) {
            if (userDB.getId() == id) {
                user = userDB;
                break;
            }
        }
        if (user == null) {
            throw new IllegalArgumentException();
        }
        return user;

    }

    @Override
    public List<User> getUsers() {
        return users;
    }

    @Override
    public User add(UserDto userDto) {
        boolean invalidData = emailIsDuplicate(userDto);
        if (invalidData) {
            throw new IllegalArgumentException();
        }
        User user = new User();
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setId(id);
        id++;
        users.add(user);
        return user;
    }

    @Override
    public User update(long id, UserDto userDto) {

        userDto.setId(id);
        boolean invalidData = emailIsDuplicate(userDto);
        if (invalidData) {
            throw new IllegalArgumentException();
        }

        User user = null;
        for (User userDB : users) {
            if (userDB.getId() == id) {
                user = userDB;
                break;
            }
        }

        if (user == null) {
            throw new IllegalArgumentException();
        }

        if (userDto.getName() != null) {
            user.setName(userDto.getName());
        }
        if (userDto.getEmail() != null) {
            user.setEmail(userDto.getEmail());
        }

        return user;

    }

    @Override
    public void delete(long id) {

        User user = null;
        for (User userDB : users) {
            if (userDB.getId() == id) {
                user = userDB;
                break;
            }
        }
        if (user == null) {
            throw new IllegalArgumentException();
        }

        users.remove(user);

    }

    private boolean emailIsDuplicate(UserDto userDto) {

        if (userDto.getEmail() == null) {
            return false;
        }

        for (User user : users) {
            if (userDto.getEmail().equals(user.getEmail())
                    && userDto.getId() != user.getId()) {
                return true;
            }
        }

        return false;

    }

}
