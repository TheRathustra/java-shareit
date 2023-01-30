package ru.practicum.shareit.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.user.dto.UserRepository;
import ru.practicum.shareit.user.model.User;

import java.util.List;
import java.util.Optional;

@Service
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
        User userDB = getUserById(id);

        if (user.getName() != null)
            userDB.setName(user.getName());
        if (user.getEmail() != null)
            userDB.setEmail(user.getEmail());

        userRepository.saveAndFlush(userDB);

        return userDB;
    }

    @Override
    public void delete(long id) {
        User user = getUserById(id);
        userRepository.delete(user);
    }

    @Override
    public User getUserById(long id) {
        Optional<User> userOptional = userRepository.findById(id);

        if (userOptional.isEmpty())
            throw new IllegalArgumentException();

        return userOptional.get();
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

}
