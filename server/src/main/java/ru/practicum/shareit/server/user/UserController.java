package ru.practicum.shareit.server.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.server.user.dto.UserDto;
import ru.practicum.shareit.server.user.model.User;
import ru.practicum.shareit.server.user.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable("id") Long id) {
        User user = userService.getUserById(id);
        return UserDto.userToDto(user);
    }

    @GetMapping
    public List<UserDto> getUsers() {
        List<User> users = userService.getUsers();
        return users.stream().map(UserDto::userToDto).collect(Collectors.toList());
    }

    @PostMapping
    @Transactional
    public UserDto create(@RequestBody UserDto userDto) {
        User user = UserDto.dtoToUser(userDto);
        User newUser = userService.add(user);
        return UserDto.userToDto(newUser);
    }

    @PatchMapping("/{id}")
    @Transactional
    public UserDto update(@PathVariable("id") Long id, @RequestBody UserDto userDto) {
        User user = UserDto.dtoToUser(userDto);
        User updatedUser = userService.update(id, user);
        return UserDto.userToDto(updatedUser);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public void delete(@PathVariable("id") Long id) {
        userService.delete(id);
    }

}
