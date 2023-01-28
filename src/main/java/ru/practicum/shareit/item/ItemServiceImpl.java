package ru.practicum.shareit.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.BookingStorage;
import ru.practicum.shareit.item.comment.Comment;
import ru.practicum.shareit.item.comment.CommentAnswerDto;
import ru.practicum.shareit.item.dto.ItemAnswer;
import ru.practicum.shareit.item.dto.ItemMapper;
import ru.practicum.shareit.item.error.CommentWithoutBooking;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ItemServiceImpl implements ItemService {

    private final ItemStorage itemStorage;
    private final UserService userService;
    private final BookingStorage bookingStorage;

    @Autowired
    public ItemServiceImpl(ItemStorage itemStorage, UserService userService, BookingStorage bookingStorage) {
        this.itemStorage = itemStorage;
        this.userService = userService;
        this.bookingStorage = bookingStorage;
    }

    @Override
    public Item add(Long userId, Item item) {
        User user = userService.getUserById(userId);
        return itemStorage.add(user, item);
    }

    @Override
    public Item update(Long userId, long id, Item item) {
        User user = userService.getUserById(userId);
        return itemStorage.update(user, id, item);
    }

    @Override
    public void delete(long id) {
        itemStorage.delete(id);
    }

    @Override
    public ItemAnswer getItemById(Long id, Long userId) {
        Item item = itemStorage.getItemById(id);
        ItemAnswer answer = ItemMapper.itemToAnswerDTO(item);

        if (item.getOwner().getId().equals(userId)) {
            Booking lastBooking = bookingStorage.getLastBooking(id);
            Booking nextBooking = bookingStorage.getNextBooking(id);
            answer.setLastBooking(lastBooking);
            answer.setNextBooking(nextBooking);
        }

        List<Comment> comments = itemStorage.getCommentsByItemId(id);
        if (!comments.isEmpty()) {
            List<CommentAnswerDto> commentDTO = comments.stream().map(CommentAnswerDto::commentToDto).
                    collect(Collectors.toList());
            answer.setComments(commentDTO);
        }

        return answer;
    }

    @Override
    public List<ItemAnswer> getItems(Long userId) {
        List<Item> items = itemStorage.getItems(userId);

        List<ItemAnswer> itemAnswer = new ArrayList<>();
        for (Item item : items) {

            ItemAnswer answer = ItemMapper.itemToAnswerDTO(item);

            if (item.getOwner().getId().equals(userId)) {
                Booking lastBooking = bookingStorage.getLastBooking(item.getId());
                Booking nextBooking = bookingStorage.getNextBooking(item.getId());
                answer.setLastBooking(lastBooking);
                answer.setNextBooking(nextBooking);
            }

            itemAnswer.add(answer);
        }

        return itemAnswer;
    }

    @Override
    public List<Item> getItemsByText(String text) {
        return itemStorage.findItemsByText(text);
    }

    @Override
    public Comment addComment(Long userId, Long itemId, Comment comment) {
        Item item = itemStorage.getItemById(itemId);
        User user = userService.getUserById(userId);
        List<Booking> bookings = bookingStorage.getBookingsByItemIdAndBookerInPast(itemId, userId);
        if (bookings.isEmpty())
            throw new CommentWithoutBooking();

        comment.setItem(item);
        comment.setAuthor(user);
        return itemStorage.addComment(comment);
    }
}
