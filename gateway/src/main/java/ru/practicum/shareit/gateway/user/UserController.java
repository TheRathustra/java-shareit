package ru.practicum.shareit.gateway.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.gateway.user.client.UserClient;
import ru.practicum.shareit.gateway.user.dto.UserDto;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/users")
public class UserController {

    private final UserClient client;

    @Autowired
    public UserController(UserClient userClient) {
        this.client = userClient;
    }

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody @Valid UserDto userDto) {
        return client.create(userDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getUserById(@PathVariable("id") Long id) {
        return client.getUserById(id);
    }

    @GetMapping
    public ResponseEntity<Object> getUsers() {
        return client.getUsers();
    }

    @PatchMapping("/{id}")
    public  ResponseEntity<Object> update(@PathVariable("id") Long id, @RequestBody UserDto userDto) {
        return client.update(id, userDto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        client.delete(id);
    }

}
