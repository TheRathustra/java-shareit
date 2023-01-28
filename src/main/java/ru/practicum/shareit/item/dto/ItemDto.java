package ru.practicum.shareit.item.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.user.dto.UserDto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class ItemDto {

    private Long id;

    @NotEmpty
    private String name;

    @NotNull
    private String description;

    @NotNull
    private Boolean available;

    private UserDto owner;

    private ItemRequestDto request;

    public ItemDto(String name, String description, Boolean available, UserDto owner, ItemRequestDto request) {
        this.name = name;
        this.description = description;
        this.available = available;
        this.owner = owner;
        this.request = request;
    }
}
