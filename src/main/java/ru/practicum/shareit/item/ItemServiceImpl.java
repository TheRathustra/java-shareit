package ru.practicum.shareit.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.booking.BookingService;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.item.comment.Comment;
import ru.practicum.shareit.item.comment.CommentRepository;
import ru.practicum.shareit.item.dto.ItemAnswer;
import ru.practicum.shareit.item.dto.ItemRepository;
import ru.practicum.shareit.item.error.CommentWithoutBooking;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.UserService;
import ru.practicum.shareit.user.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class ItemServiceImpl implements ItemService {

    private final ItemRepository repository;
    private final UserService userService;
    private final BookingService bookingStorage;

    private final CommentRepository commentRepository;

    @Autowired
    public ItemServiceImpl(ItemRepository itemRepository, UserService userService, BookingService bookingStorage, CommentRepository commentRepository) {
        this.repository = itemRepository;
        this.userService = userService;
        this.bookingStorage = bookingStorage;
        this.commentRepository = commentRepository;
    }

    @Override
    public Item add(Long userId, Item item) {
        User user = userService.getUserById(userId);

        item.setOwner(user);
        repository.saveAndFlush(item);

        return item;
    }

    @Override
    public Item update(Long userId, Long id, Item item) {
        User user = userService.getUserById(userId);
        Item itemDB = repository.getById(id);

        if (!itemDB.getOwner().getId().equals(user.getId())) {
            throw new IllegalArgumentException();
        }

        if (itemDB == null)
            throw new IllegalArgumentException();

        if (item.getName() != null)
            itemDB.setName(item.getName());

        if (item.getDescription() != null)
            itemDB.setDescription(item.getDescription());

        if (item.getAvailable() != null)
            itemDB.setAvailable(item.getAvailable());

        repository.saveAndFlush(itemDB);
        return itemDB;
    }

    @Override
    public void delete(Long id) {
        Optional<Item> item = repository.findById(id);

        if (item.isEmpty())
            throw new IllegalArgumentException();

        repository.delete(item.get());
    }

    @Override
    public ItemAnswer getItemById(Long id, Long userId) {

        Optional<Item> itemOptional = repository.findById(id);

        if (itemOptional.isEmpty())
            throw new IllegalArgumentException();

        Item item = itemOptional.get();

        ItemAnswer answer = ItemAnswer.itemToAnswerDTO(item);
        if (item.getOwner().getId().equals(userId)) {
            Booking lastBooking = bookingStorage.getLastBooking(id);
            Booking nextBooking = bookingStorage.getNextBooking(id);
            answer.setLastBooking(lastBooking);
            answer.setNextBooking(nextBooking);
        }

        List<Comment> comments = commentRepository.findAllByItem_Id(id);
        answer.setComments(comments);

        return answer;
    }

    @Override
    public List<ItemAnswer> getItems(Long userId) {

        List<Item> items = repository.findAllByUser(userId);

        List<ItemAnswer> itemAnswer = new ArrayList<>();
        for (Item item : items) {

            ItemAnswer answer = ItemAnswer.itemToAnswerDTO(item);

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
        if (text.isEmpty()) {
            return new ArrayList<>();
        }
        return repository.search(text);
    }

    @Override
    public Comment addComment(Long userId, Long itemId, Comment comment) {

        Optional<Item> itemOptional = repository.findById(itemId);

        if (itemOptional.isEmpty())
            throw new IllegalArgumentException();

        Item item = itemOptional.get();

        User user = userService.getUserById(userId);
        List<Booking> bookings = bookingStorage.getBookingsByItemIdAndBookerInPast(itemId, userId);
        if (bookings.isEmpty())
            throw new CommentWithoutBooking();

        comment.setItem(item);
        comment.setAuthor(user);
        commentRepository.saveAndFlush(comment);
        return comment;

    }
}
