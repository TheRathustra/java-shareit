package ru.practicum.shareit.user;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.error.DuplicateEmailException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class UserStorageImp implements UserStorage {

    private long id = 1;
    private Map<Long, User> users = new HashMap<>();

    @Override
    public User getUserById(long id) {

        User user = users.get(id);

        if (user == null) {
            throw new IllegalArgumentException();
        }
        return user;

    }

    @Override
    public List<User> getUsers() {
        return new ArrayList<>(users.values());
    }

    @Override
    public User add(UserDto userDto) {
        emailIsDuplicate(userDto);

        User user = new User();
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setId(id);
        users.put(id, user);

        id++;
        return user;
    }

    @Override
    public User update(long id, UserDto userDto) {

        userDto.setId(id);
        emailIsDuplicate(userDto);

        User user = users.get(id);

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

        User user = users.get(id);
        if (user == null) {
            throw new IllegalArgumentException();
        }

        users.remove(id);

    }

    private void emailIsDuplicate(UserDto userDto) {

        if (userDto.getEmail() == null) {
            return;
        }

        for (User user : users.values()) {
            if (userDto.getEmail().equals(user.getEmail())
                    && userDto.getId() != user.getId()) {
                    throw new DuplicateEmailException();
            }
        }

    }

}
