package ru.practicum.shareit.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.dto.UserMapper;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserServiceImpl implements UserService {

    private UserStorage userStorage;

    @Autowired
    public UserServiceImpl(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    @Override
    public UserDto add(UserDto userDto) {
        User user = UserMapper.DtoToUser(userDto);
        User addedUser = userStorage.add(user);
        return UserMapper.userToDto(addedUser);
    }

    @Override
    public UserDto update(long id, UserDto userDto) {
        User user = UserMapper.DtoToUser(userDto);
        User updatedUser = userStorage.update(id, user);
        return UserMapper.userToDto(updatedUser);
    }

    @Override
    public void delete(long id) {
        userStorage.delete(id);
    }

    @Override
    public UserDto getUserById(long id) {
        User user = userStorage.getUserById(id);
        return UserMapper.userToDto(user);
    }

    @Override
    public List<UserDto> getUsers() {
        List<User> users = userStorage.getUsers();
        List<UserDto> usersDTO = new ArrayList<>();
        for (User user : users) {
            usersDTO.add(UserMapper.userToDto(user));
        }
        return usersDTO;
    }

}
