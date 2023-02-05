package ru.practicum.shareit.server.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.server.user.model.User;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private Long id;

    private String name;

    private String email;

    public UserDto(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
    }

    public static UserDto userToDto(User user) {
        if (user == null)
            return null;

        return new UserDto(user);
    }

    public static User dtoToUser(UserDto userDto) {
        if (userDto == null)
            return null;

        User user = new User();
        user.setId(userDto.getId());
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        return user;
    }

}
