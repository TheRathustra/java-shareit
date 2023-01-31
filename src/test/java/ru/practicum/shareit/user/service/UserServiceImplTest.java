package ru.practicum.shareit.user.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    private User user = new User(
            1L,
            "userName",
            "user@email.com"
    );

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void beforeEach() {
        userService = new UserServiceImpl(userRepository);
    }

    @Test
    void getUserById_whenUserNotFound_ThenThrowIllegalArgumentException() {
        when(userRepository.findById(-1L))
                .thenReturn(Optional.empty());
        final IllegalArgumentException exception = Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> userService.getUserById(-1L));
        Assertions.assertEquals("Пользователь с ID=-1 не найден!", exception.getMessage());
    }

    @Test
    void getUserById_whenValid_thenReturnUser() {
        when(userRepository.findById(user.getId()))
                .thenReturn(Optional.of(user));
        User actualUser = userService.getUserById(1L);
        verify(userRepository).findById(1L);
        assertThat(actualUser, equalTo(user));
    }
    @Test
    void add_WhenExistingEmail_thenThrowDataIntegrityViolationException() {
        when(userRepository.saveAndFlush(user))
                .thenThrow(new DataIntegrityViolationException(""));
        Assertions.assertThrows(
                DataIntegrityViolationException.class,
                () -> userService.add(user));
    }

    @Test
    void add_whenValid_thenReturnUser() {
        when(userRepository.saveAndFlush(user)).thenReturn(user);
        User actualUser = userService.add(user);
        assertThat(user, equalTo(actualUser));
        verify(userRepository).saveAndFlush(user);
    }

    @Test
    void update_whenValid_thenReturnUser() {
        when(userRepository.saveAndFlush(user)).thenReturn(user);
        when(userRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(user));

        User actualUser = userService.update(user.getId(), user);
        assertThat(user, equalTo(actualUser));
        verify(userRepository).saveAndFlush(user);
    }

}