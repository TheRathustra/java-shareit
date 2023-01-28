package ru.practicum.shareit.item;

import ru.practicum.shareit.item.comment.Comment;
import ru.practicum.shareit.item.dto.ItemAnswer;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemService {

    Item add(Long userId, Item item);

    Item update(Long userId, long id, Item item);

    void delete(long id);

    ItemAnswer getItemById(Long id, Long userId);

    List<ItemAnswer> getItems(Long userId);

    List<Item> getItemsByText(String text);

    Comment addComment(Long userId, Long itenId, Comment comment);

}
