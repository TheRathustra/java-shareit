package ru.practicum.shareit.gateway.item.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemDto {

    private Long id;

    @NotEmpty
    private String name;

    @NotNull
    private String description;

    @NotNull
    private Boolean available;

    private UserDtoItem owner;

    private Long requestId;

    @Data
    public static class UserDtoItem {
        private Long id;
        private String name;
        private String email;


    }

}
