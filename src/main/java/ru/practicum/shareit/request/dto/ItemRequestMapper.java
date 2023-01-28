package ru.practicum.shareit.request.dto;

import ru.practicum.shareit.request.ItemRequest;
import ru.practicum.shareit.user.dto.UserMapper;

public class ItemRequestMapper {
    public static ItemRequestDto itemRequestToDTO(ItemRequest itemRequest) {
        ItemRequestDto itemRequestDto = new ItemRequestDto();
        itemRequestDto.setId(itemRequest.getId());
        itemRequestDto.setDescription(itemRequest.getDescription());
        if (itemRequest.getRequestor() != null)
            itemRequestDto.setRequestor(UserMapper.userToDto(itemRequest.getRequestor()));
        return itemRequestDto;
    }

    public static ItemRequest dtoToItemRequest(ItemRequestDto itemRequestDto) {
        ItemRequest itemRequest = new ItemRequest();
        itemRequest.setId(itemRequestDto.getId());
        itemRequest.setDescription(itemRequestDto.getDescription());
        if (itemRequestDto.getRequestor() != null)
            itemRequest.setRequestor(UserMapper.dtoToUser(itemRequestDto.getRequestor()));
        return itemRequest;
    }
}
