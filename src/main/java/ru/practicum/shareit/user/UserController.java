package ru.practicum.shareit.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.dto.UserMapper;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/users")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable("id") long id) {
        User user = userService.getUserById(id);
        return UserMapper.userToDto(user);
    }

    @GetMapping
    public List<UserDto> getUsers() {
        List<User> users = userService.getUsers();
        List<UserDto> usersDto = users.stream().map(UserMapper::userToDto).collect(Collectors.toList());
        return usersDto;
    }

    @PostMapping
    @Transactional
    public UserDto create(@RequestBody @Valid UserDto userDto) {
        User user = UserMapper.dtoToUser(userDto);
        User newUser = userService.add(user);
        return UserMapper.userToDto(newUser);
    }

    @PatchMapping("/{id}")
    @Transactional
    public UserDto update(@PathVariable("id") long id, @RequestBody UserDto userDto) {
        User user = UserMapper.dtoToUser(userDto);
        User updatedUser = userService.update(id, user);
        return UserMapper.userToDto(updatedUser);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public void delete(@PathVariable("id") long id) {
        userService.delete(id);
    }

}
