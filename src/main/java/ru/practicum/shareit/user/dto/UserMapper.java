package ru.practicum.shareit.user.dto;

import ru.practicum.shareit.user.model.User;

public class UserMapper {

    public static UserDto userToDto(User user) {
        UserDto userDto = new UserDto(
                user.getName(),
                user.getEmail()
        );
        userDto.setId(user.getId());
        return userDto;
    }

    public static User dtoToUser(UserDto userDto) {
        User user = new User();
        user.setId(userDto.getId());
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        return user;
    }

}
