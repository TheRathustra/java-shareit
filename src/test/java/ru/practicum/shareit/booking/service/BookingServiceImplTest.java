package ru.practicum.shareit.booking.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import ru.practicum.shareit.booking.error.ItemUnavailableException;
import ru.practicum.shareit.booking.error.UpdateBookingException;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingState;
import ru.practicum.shareit.booking.model.BookingStatus;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.user.error.UndefinedUserException;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.service.UserService;

import java.time.LocalDateTime;
import java.util.List;
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
    void getById_whenOwnerNotEqualsUserId_thenThrowIllegalArgumentException() {
        when(repository.findById(Mockito.any())).thenReturn(Optional.of(booking));
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> service.getById(booking.getId(), user2.getId()));
    }

    @Test
    void getById_whenNotFoundBooking_thenThrowIllegalArgumentException() {
        when(repository.findById(Mockito.any())).thenReturn(Optional.empty());
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> service.getById(booking.getId(), user.getId()));
    }

    @Test
    void getById_whenValid_thenReturnBooking() {
        when(repository.findById(Mockito.any())).thenReturn(Optional.of(booking));
        Booking actualBooking = service.getById(booking.getId(), user.getId());
        assertThat(actualBooking, equalTo(booking));
    }

    @Test
    void approveBooking_whenNotApprovedAndBookerEqualsUser_thenStatusCanceled() {
        booking.setStatus(BookingStatus.WAITING);
        booking.getItem().setOwner(user2);
        when(repository.findById(Mockito.any())).thenReturn(Optional.of(booking));
        when(repository.saveAndFlush(Mockito.any())).thenReturn(booking);

        service.approveBooking(booking.getId(), false, user.getId());

        assertThat(booking.getStatus(), equalTo(BookingStatus.CANCELED));
    }

    @Test
    void approveBooking_whenApprovedAndBookerNotEqualsUser_thenThrowIllegalArgumentException() {
        booking.setStatus(BookingStatus.WAITING);
        booking.getItem().setOwner(user2);
        when(repository.findById(Mockito.any())).thenReturn(Optional.of(booking));

        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> service.approveBooking(booking.getId(), true, user.getId()));
    }

    @Test
    void approveBooking_whenNotApprovedAndBookerNotEqualsUser_thenStatusApproved() {
        booking.setStatus(BookingStatus.WAITING);
        booking.setBooker(user2);
        when(repository.findById(Mockito.any())).thenReturn(Optional.of(booking));
        when(repository.saveAndFlush(Mockito.any())).thenReturn(booking);

        service.approveBooking(booking.getId(), true, user.getId());

        assertThat(booking.getStatus(), equalTo(BookingStatus.APPROVED));
    }

    @Test
    void approveBooking_whenNotApprovedAndBookerNotEqualsUser_thenStatusRejected() {
        booking.setStatus(BookingStatus.WAITING);
        booking.setBooker(user2);
        when(repository.findById(Mockito.any())).thenReturn(Optional.of(booking));
        when(repository.saveAndFlush(Mockito.any())).thenReturn(booking);

        service.approveBooking(booking.getId(), false, user.getId());

        assertThat(booking.getStatus(), equalTo(BookingStatus.REJECTED));
    }

    @Test
    void approveBooking_statusIsNotWaiting_thenThrowUpdateBookingException() {
        booking.getItem().setOwner(user);
        booking.setBooker(user2);
        when(repository.findById(Mockito.any())).thenReturn(Optional.of(booking));

        Assertions.assertThrows(
                UpdateBookingException.class,
                () -> service.approveBooking(booking.getId(), true, user.getId()));
    }

    @Test
    void getBookingsByState_whenUserNotFound_thenThrowUndefinedUserException() {
        when(userService.getUserById(Mockito.anyLong())).thenReturn(null);
        Assertions.assertThrows(
                UndefinedUserException.class,
                () -> service.getBookingsByState(user.getId(), BookingState.ALL, null));
    }

    @Test
    void getBookingsByState_whenValid_thenReturnListOfBooking() {
        when(userService.getUserById(Mockito.anyLong())).thenReturn(user);
        when(repository.findAll(Mockito.any(Specification.class), Mockito.any(Sort.class)))
                .thenReturn(List.of(booking));

        List<Booking> actualList = service.getBookingsByState(user.getId(), BookingState.ALL, null);
        assertThat(actualList, equalTo(List.of(booking)));
    }

    @Test
    void getBookingsByOwner_whenValid_thenReturnListOfBooking() {
        when(userService.getUserById(Mockito.anyLong())).thenReturn(user);
        when(repository.findAll(Mockito.any(Specification.class), Mockito.any(Sort.class)))
                .thenReturn(List.of(booking));

        List<Booking> actualList = service.getBookingsByOwner(user.getId(), BookingState.ALL, null);
        assertThat(actualList, equalTo(List.of(booking)));

    }

    @Test
    void getLastBooking_whenValid_thenReturnBooking() {
        when(repository.findAll(Mockito.any(Specification.class), Mockito.any(Sort.class)))
                .thenReturn(List.of(booking));
        Booking actualBooking = service.getLastBooking(item.getId());
        assertThat(actualBooking, equalTo(booking));
    }

    @Test
    void getNextBooking_whenValid_thenReturnBooking() {
        when(repository.findAll(Mockito.any(Specification.class), Mockito.any(Sort.class)))
                .thenReturn(List.of(booking));
        Booking actualBooking = service.getNextBooking(item.getId());
        assertThat(actualBooking, equalTo(booking));
    }

    @Test
    void getBookingsByItemIdAndBookerInPast() {
        when(repository.findAll(Mockito.any(Specification.class), Mockito.any(Sort.class)))
                .thenReturn(List.of(booking));
        List<Booking> actualList = service.getBookingsByItemIdAndBookerInPast(item.getId(), user.getId());
        assertThat(actualList, equalTo(List.of(booking)));
    }
}