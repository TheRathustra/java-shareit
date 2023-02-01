package ru.practicum.shareit.booking.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.shareit.booking.error.ItemUnavailableException;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingStatus;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.service.UserService;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookingServiceImplTest {

    @InjectMocks
    private BookingServiceImpl service;

    @Mock
    private BookingRepository repository;

    @Mock
    private UserService userService;

    @Mock
    private ItemRepository itemRepository;

    private final User user = new User(
            1L,
            "userName",
            "user@email.ru"
    );

    private final User user2 = new User(
            2L,
            "userName",
            "user@email.ru"
    );

    private final Item item = new Item(
            1L,
            "test",
            "test",
            true,
            user,
            1L
    );

    private final Booking booking = new Booking(
            1L,
            LocalDateTime.now().plusDays(1),
            LocalDateTime.now().plusDays(2),
            item,
            user,
            BookingStatus.APPROVED
    );

    @Test
    void add_whenValid_thenReturnBooking() {
        when(userService.getUserById(Mockito.anyLong())).thenReturn(user2);
        when(itemRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(item));
        when(repository.saveAndFlush(Mockito.any())).thenReturn(booking);

        Booking actualBooking = service.add(booking, user2.getId(), item.getId());
        assertThat(actualBooking, equalTo(booking));
    }

    @Test
    void add_whenNotFoundItem_thenThrowIllegalArgumentException() {
        when(itemRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());

        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> service.add(booking, user.getId(), item.getId()));

        verify(repository, never()).saveAndFlush(Mockito.any());
    }

    @Test
    void add_whenItemUnavailable_thenThrowIllegalArgumentException() {
        item.setAvailable(false);
        when(itemRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(item));

        Assertions.assertThrows(
                ItemUnavailableException.class,
                () -> service.add(booking, user.getId(), item.getId()));

        verify(repository, never()).saveAndFlush(Mockito.any());
    }

    @Test
    void add_whenOwnerEqualsUserId_thenThrowIllegalArgumentException() {
        when(userService.getUserById(Mockito.anyLong())).thenReturn(user2);
        when(itemRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(item));

        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> service.add(booking, user.getId(), item.getId()));

        verify(repository, never()).saveAndFlush(Mockito.any());
    }


    @Test
    void getById() {
    }

    @Test
    void approveBooking() {
    }

    @Test
    void getBookingsByState() {
    }

    @Test
    void getBookingsByOwner() {
    }

    @Test
    void getLastBooking() {
    }

    @Test
    void getNextBooking() {
    }

    @Test
    void getBookingsByItemIdAndBookerInPast() {
    }
}