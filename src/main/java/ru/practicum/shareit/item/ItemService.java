package ru.practicum.shareit.item;

import ru.practicum.shareit.item.dto.ItemDto;

import java.util.List;

public interface ItemService {

    ItemDto add(Long userId, ItemDto itemDto);

    ItemDto update(Long userId, long id, ItemDto itemDto);

    void delete(long id);

    ItemDto getItemById(long id);

    List<ItemDto> getItems(Long userId);

    List<ItemDto> getItemsByText(String text);

}
