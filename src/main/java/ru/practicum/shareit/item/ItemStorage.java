package ru.practicum.shareit.item;

import ru.practicum.shareit.item.comment.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.User;

import java.util.List;

public interface ItemStorage {

    Item add(User user, Item item);

    Item update(User user, long id, Item item);

    void delete(long id);

    Item getItemById(long id);

    List<Item> getItems(Long idOwner);

    List<Item> findItemsByText(String text);

    Comment addComment(Comment comment);

    List<Comment> getCommentsByItemId(Long itemId);

}
