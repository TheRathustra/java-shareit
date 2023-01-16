package ru.practicum.shareit.item.dto;

import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.request.ItemRequest;

public class ItemMapper {

    public static ItemDto itemToDTO(Item item) {
        ItemDto itemDto = new ItemDto(
                item.getName(),
                item.getDescription(),
                item.getAvailable(),
                item.getOwner(),
                item.getRequest() != null ? item.getRequest().getId() : null
        );
        itemDto.setId(item.getId());
        return itemDto;
    }

    public static Item dtoToItem(ItemDto itemDto) {
        Item item = new Item();
        item.setId(itemDto.getId());
        item.setDescription(itemDto.getDescription());
        item.setName(itemDto.getName());
        if (itemDto.getAvailable() != null)
            item.setAvailable(itemDto.getAvailable());

        ItemRequest itemRequest = new ItemRequest();
        itemRequest.setId(itemDto.getId());
        item.setRequest(itemRequest);

        return item;
    }
}
