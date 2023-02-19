package ru.practicum.shareit.server.item.service;

import org.springframework.data.domain.Pageable;
import ru.practicum.shareit.server.item.comment.Comment;
import ru.practicum.shareit.server.item.dto.ItemAnswer;
import ru.practicum.shareit.server.item.model.Item;

import java.util.List;

public interface ItemService {

    Item add(Long userId, Item item);

    Item update(Long userId, Long id, Item item);

    void delete(Long id);

    ItemAnswer getItemById(Long id, Long userId);

    List<ItemAnswer> getItems(Long userId, Pageable pageRequest);

    List<Item> getItemsByText(String text, Pageable pageRequest);

    Comment addComment(Long userId, Long itemId, Comment comment);

    List<Item> getItemsByRequestId(Long requestId);

}
