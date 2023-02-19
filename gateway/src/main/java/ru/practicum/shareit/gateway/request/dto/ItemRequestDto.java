package ru.practicum.shareit.gateway.request.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemRequestDto {
    private Long id;
    @NotEmpty
    private String description;
    private UserDtoItemRequest requestor;

    private List<ItemDtoRequest> items;

    private LocalDateTime created;

    @Data
    @NoArgsConstructor
    public static class UserDtoItemRequest {
        private Long id;
        private String name;
        private String email;

    }

    @Data
    @NoArgsConstructor
    public static class ItemDtoRequest {
        private Long id;
        private String name;
        private String description;
        private Boolean available;
        private UserDtoItemRequest owner;

        private Long requestId;

    }

}
