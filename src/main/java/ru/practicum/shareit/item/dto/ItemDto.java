package ru.practicum.shareit.item.dto;

import lombok.Data;
import ru.practicum.shareit.user.User;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class ItemDto {
    private long id;
    @NotEmpty
    private String name;
    @NotEmpty
    private String description;

    @NotNull
    private Boolean available;
    private User owner;
    private long request;

    public ItemDto(String name, String description, Boolean available, User owner, long request) {
        this.name = name;
        this.description = description;
        this.available = available;
        this.owner = owner;
        this.request = request;
    }

}
