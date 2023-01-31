package ru.practicum.shareit.item.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.shareit.booking.service.BookingService;
import ru.practicum.shareit.item.comment.CommentRepository;
import ru.practicum.shareit.item.dto.ItemAnswer;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.service.UserService;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ItemServiceImplTest {

    @InjectMocks
    ItemServiceImpl itemService;

    @Mock
    private ItemRepository repository;
    @Mock
    private UserService userService;
    @Mock
    private BookingService bookingStorage;
    @Mock
    private CommentRepository commentRepository;

    private User user = new User(
            1L,
            "userName",
            "user@email.com"
    );

    private Item item = new Item(
            1L,
            "test",
            "test",
            true, user,
            1L
    );

    private ItemDto itemDto = ItemDto.itemToDTO(item);

    private ItemAnswer itemAnswer = ItemAnswer.itemToAnswerDTO(item);

    @Test
    void add_whenValid_thenReturnItem() {

        when(userService.getUserById(Mockito.anyLong())).thenReturn(user);
        when(repository.saveAndFlush(Mockito.any())).thenReturn(item);

        Item actualItem = itemService.add(user.getId(), item);
        assertThat(actualItem, equalTo(item));

    }

    @Test
    void update_whenValid_thenReturnItem() {

        when(userService.getUserById(Mockito.anyLong())).thenReturn(user);
        when(repository.findById(Mockito.anyLong())).thenReturn(Optional.of(item));
        when(repository.saveAndFlush(Mockito.any())).thenReturn(item);

        Item actualItem = itemService.update(user.getId(), item.getId(), item);
        assertThat(actualItem, equalTo(item));

    }

    @Test
    void update_whenNotOwner_thenThrowIllegalArgumentException() {

        User newUser = new User(2L, "test", "test");
        Item itemFromDb = new Item(1L, "test", "test", true, newUser, 1L);

        when(userService.getUserById(Mockito.anyLong())).thenReturn(user);
        when(repository.findById(Mockito.anyLong())).thenReturn(Optional.of(itemFromDb));

        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> itemService.update(user.getId(), item.getId(), item));

    }

    @Test
    void update_whenNoItemInDB_thenThrowIllegalArgumentException() {

        when(userService.getUserById(Mockito.anyLong())).thenReturn(user);
        when(repository.findById(Mockito.anyLong())).thenReturn(Optional.empty());

        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> itemService.update(user.getId(), item.getId(), item));

    }

    @Test
    void getItemById_whenValid_thenReturnItem() {

        when(repository.findById(Mockito.anyLong())).thenReturn(Optional.of(item));
        when(bookingStorage.getLastBooking(Mockito.anyLong())).thenReturn(null);
        when(bookingStorage.getNextBooking(Mockito.anyLong())).thenReturn(null);
        when(commentRepository.findAllByItem_Id(Mockito.anyLong())).thenReturn(Collections.emptyList());

        ItemAnswer actualItem = itemService.getItemById(itemAnswer.getId(), user.getId());

        assertThat(actualItem, equalTo(itemAnswer));

    }

    @Test
    void getItemById_whenNoItemInDB_thenThrowIllegalArgumentException() {

        when(repository.findById(Mockito.anyLong())).thenReturn(Optional.empty());

        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> itemService.update(user.getId(), item.getId(), item));

    }

    @Test
    void getItems() {

        when(repository.findAllByUser(Mockito.anyLong())).thenReturn(List.of(item));
        when(bookingStorage.getLastBooking(Mockito.anyLong())).thenReturn(null);
        when(bookingStorage.getNextBooking(Mockito.anyLong())).thenReturn(null);

        List<ItemAnswer> actualItems = itemService.getItems(user.getId(), null);

        assertThat(actualItems, equalTo(List.of(ItemAnswer.itemToAnswerDTO(item))));

    }

    @Test
    void getItemsByText() {
    }

    @Test
    void addComment() {
    }

    @Test
    void getItemsByRequestId() {
    }
}