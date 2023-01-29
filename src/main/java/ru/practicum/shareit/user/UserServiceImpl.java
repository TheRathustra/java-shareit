package ru.practicum.shareit.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.user.dto.UserRepository;
import ru.practicum.shareit.user.model.User;

import java.util.List;
import java.util.Optional;

@Component
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository repository) {
        this.userRepository = repository;
    }

    @Override
    public User add(User user) {
        return userRepository.saveAndFlush(user);
    }

    @Override
    public User update(long id, User user) {
        User userDB = userRepository.getById(id);

        if (userDB == null) {
            throw new IllegalArgumentException();
        }

        if (user.getName() != null) {
            userDB.setName(user.getName());
        }
        if (user.getEmail() != null) {
            userDB.setEmail(user.getEmail());
        }

        userRepository.saveAndFlush(userDB);

        return userDB;
    }

    @Override
    public void delete(long id) {
        User user = userRepository.getById(id);
        if (user == null) {
            throw new IllegalArgumentException();
        }

        userRepository.delete(user);
    }

    @Override
    public User getUserById(long id) {
        Optional<User> userDto = userRepository.findById(id);

        if (userDto.isEmpty()) {
            throw new IllegalArgumentException();
        }
        return userDto.get();
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

}
