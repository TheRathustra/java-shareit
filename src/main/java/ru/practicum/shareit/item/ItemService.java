package ru.practicum.shareit.item;

import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.User;

import java.util.List;

public interface ItemService {

    Item add(User user, ItemDto itemDto);

    Item update(User user, long id, ItemDto itemDto);

    void delete(long id);

    Item getItemById(long id);

    List<Item> getItems(User user);

    List<Item> getItemsByText(String text);

}
