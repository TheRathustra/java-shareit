package ru.practicum.shareit.gateway.user;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.gateway.user.client.UserClient;
import ru.practicum.shareit.gateway.user.dto.UserDto;

@RestController
@RequestMapping(path = "/users")
public class UserController {

    private final UserClient client;

    @Autowired
    public UserController(UserClient userClient) {
        this.client = userClient;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<Object> create(@RequestBody @Valid UserDto userDto) {
        ResponseEntity<Object> newUser = client.create(userDto);
        return newUser;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getUserById(@PathVariable("id") Long id) {
        ResponseEntity<Object> user = client.getUserById(id);
        return user;
    }

    @GetMapping
    public ResponseEntity<Object> getUsers() {
        ResponseEntity<Object> users = client.getUsers();
        return users;
    }

    @PatchMapping("/{id}")
    @Transactional
    public  ResponseEntity<Object> update(@PathVariable("id") Long id, @RequestBody UserDto userDto) {
        ResponseEntity<Object> updatedUser = client.update(id, userDto);
        return updatedUser;
    }

    @DeleteMapping("/{id}")
    @Transactional
    public void delete(@PathVariable("id") Long id) {
        client.delete(id);
    }

}
