package ru.practicum.shareit.request.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.user.model.User;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    public ItemRequestDto(ItemRequest request) {
        this.id = request.getId();
        this.description = request.getDescription();
        this.requestor = UserDtoItemRequest.userToDto(request.getRequestor());
        this.created = request.getCreated();
        if (request.getItems() != null) {
            this.items = request.getItems().stream().map(ItemDtoRequest::inctanceToDto).collect(Collectors.toList());
        } else {
            this.items = new ArrayList<>();
        }
    }

    public static ItemRequest dtoToInstance(ItemRequestDto dto) {
        if (dto == null)
            return null;

        ItemRequest instance = new ItemRequest();
        instance.setId(dto.getId());
        instance.setDescription(dto.getDescription());
        instance.setRequestor(UserDtoItemRequest.dtoToUser(dto.getRequestor()));
        instance.setCreated(dto.getCreated());

        return instance;
    }

    public static ItemRequestDto instanceToDto(ItemRequest instance) {
        if (instance == null)
            return null;

        return new ItemRequestDto(instance);
    }

    @Data
    @NoArgsConstructor
    public static class UserDtoItemRequest {
        private Long id;
        private String name;
        private String email;

        public UserDtoItemRequest(User user) {
            this.id = user.getId();
            this.name = user.getName();
            this.email = user.getEmail();
        }

        public static UserDtoItemRequest userToDto(User user) {
            if (user == null)
                return null;

            return new UserDtoItemRequest(user);
        }

        public static User dtoToUser(UserDtoItemRequest dto) {
            if (dto == null)
                return null;

            User user = new User();
            user.setId(dto.getId());
            user.setName(dto.getName());
            user.setEmail(dto.getEmail());
            return user;
        }
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

        public static ItemDtoRequest inctanceToDto(Item inctance) {
            if (inctance == null)
                return null;

            ItemDtoRequest dto = new ItemDtoRequest();
            dto.setId(inctance.getId());
            dto.setName(inctance.getName());
            dto.setDescription(inctance.getDescription());
            dto.setAvailable(inctance.getAvailable());
            dto.setOwner(UserDtoItemRequest.userToDto(inctance.getOwner()));
            dto.setRequestId(inctance.getRequestId());
            return dto;
        }
    }

}
