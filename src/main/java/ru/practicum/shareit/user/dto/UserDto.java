package ru.practicum.shareit.user.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Data
public class UserDto {
    private long id;
    private String name;
    @Email
    @NotEmpty
    @NotBlank
    private String email;

    public UserDto(String name, String email) {
        this.name = name;
        this.email = email;
    }
}
