package ru.practicum.shareit.item.dto;

import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.request.ItemRequest;
import ru.practicum.shareit.request.dto.ItemRequestMapper;
import ru.practicum.shareit.user.dto.UserMapper;

import java.util.ArrayList;

public class ItemMapper {

    public static ItemDto itemToDTO(Item item) {
        ItemDto itemDto = new ItemDto(
                item.getName(),
                item.getDescription(),
                item.getAvailable(),
                UserMapper.userToDto(item.getOwner()),
                item.getRequest() != null ? ItemRequestMapper.itemRequestToDTO(item.getRequest()) : null
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

        if (itemDto.getRequest() != null) {
            ItemRequest itemRequest = new ItemRequest();
            itemRequest.setId(itemDto.getRequest().getId());
            item.setRequest(itemRequest);
        }

        if (itemDto.getOwner() != null) {
            item.setOwner(UserMapper.dtoToUser(itemDto.getOwner()));
        }

        return item;
    }

    public static ItemAnswer itemToAnswerDTO(Item item) {
        ItemAnswer a = new ItemAnswer();
        a.setId(item.getId());
        a.setName(item.getName());
        a.setDescription(item.getDescription());
        a.setAvailable(item.getAvailable());
        if (item.getOwner() != null)
            a.setOwner(UserMapper.userToDto(item.getOwner()));
        if (item.getRequest() != null)
            a.setRequest(ItemRequestMapper.itemRequestToDTO(item.getRequest()));
        a.setComments(new ArrayList<>());

        return a;
    }
}
