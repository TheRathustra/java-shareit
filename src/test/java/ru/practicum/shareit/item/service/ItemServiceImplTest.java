package ru.practicum.shareit.item.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.service.BookingService;
import ru.practicum.shareit.item.comment.Comment;
import ru.practicum.shareit.item.comment.CommentRepository;
import ru.practicum.shareit.item.dto.ItemAnswer;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.service.UserService;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.*;

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

    private final User user = new User(
            1L,
            "userName",
            "user@email.com"
    );

    private final Item item = new Item(
            1L,
            "test",
            "test",
            true,
            user,
            1L
    );

    private final Comment comment = new Comment(
            1L,
            "test",
            item,
            user,
            LocalDateTime.now()
    );

    private final Booking booking = new Booking();

    private final ItemAnswer itemAnswer = ItemAnswer.itemToAnswerDTO(item);

    @Test
    void add() {

        when(userService.getUserById(Mockito.anyLong())).thenReturn(user);
        when(repository.saveAndFlush(Mockito.any())).thenReturn(item);

        Item actualItem = itemService.add(user.getId(), item);
        assertThat(actualItem, equalTo(item));

    }

    @Test
    void update() {

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
    void getItemById() {

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

        String text = "test";
        when(repository.search(Mockito.anyString())).thenReturn(List.of(item));
        List<Item> actualItems = itemService.getItemsByText(text, null);

        assertThat(actualItems, equalTo(List.of(item)));
    }

    @Test
    void getItemsByText_whenTextIsEmpty_thenReturnEmptyList() {

        String text = "";
        List<Item> actualItems = itemService.getItemsByText(text, null);

        assertThat(actualItems, equalTo(Collections.emptyList()));
        verify(repository, never()).search(text);

    }

    @Test
    void addComment_whenItemNotFound_thenThrowIllegalArgumentException() {
        when(repository.findById(Mockito.anyLong())).thenReturn(Optional.empty());

        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> itemService.addComment(user.getId(), item.getId(), new Comment()));
    }

    @Test
    void addComment() {
        when(repository.findById(Mockito.anyLong())).thenReturn(Optional.of(item));
        when(userService.getUserById(Mockito.anyLong())).thenReturn(user);
        when(bookingStorage.getBookingsByItemIdAndBookerInPast(Mockito.anyLong(), Mockito.anyLong()))
                .thenReturn(List.of(booking));

        Comment actualComment = itemService.addComment(user.getId(), item.getId(), comment);
        assertThat(actualComment, equalTo(comment));
        verify(commentRepository).saveAndFlush(comment);
    }

    @Test
    void getItemsByRequestId() {
        when(repository.findAllByRequestId(Mockito.anyLong(), Mockito.any())).thenReturn(List.of(item));
        List<Item> actualList = itemService.getItemsByRequestId(1L);
        assertThat(actualList, equalTo(List.of(item)));
    }

    @Test
    void delete() {
        when(repository.findById(Mockito.anyLong())).thenReturn(Optional.of(item));
        itemService.delete(item.getId());
        verify(repository).delete(item);
    }

    @Test
    void delete_whenNoItem_thenThrowIllegalArgumentException() {
        when(repository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> itemService.delete(item.getId()));
    }

}