package ru.practicum.shareit.item;

import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemService {

    ItemDto add(Long userId, ItemDto itemDto);

    Item update(Long userId, long id, ItemDto itemDto);

    void delete(long id);

    Item getItemById(long id);

    List<Item> getItems(Long userId);

    List<Item> getItemsByText(String text);

}
