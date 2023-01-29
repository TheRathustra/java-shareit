package ru.practicum.shareit.item.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.request.ItemRequest;
import ru.practicum.shareit.user.model.User;

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

    private UserDtoItem owner;

    private ItemRequestDtoItem request;

    public ItemDto(Item item) {
        this.id = item.getId();
        this.name = item.getName();
        this.description = item.getDescription();
        this.available = item.getAvailable();
        this.owner = UserDtoItem.userToDTO(item.getOwner());
        this.request = ItemRequestDtoItem.itemRequestToDTO(item.getRequest());
    }

    public static Item dtoToItem(ItemDto dto) {
        if (dto == null)
            return null;

        Item item = new Item();
        item.setId(dto.getId());
        item.setName(dto.getName());
        item.setDescription(dto.description);
        item.setAvailable(dto.getAvailable());
        item.setOwner(UserDtoItem.dtoToUser(dto.getOwner()));
        item.setRequest(ItemRequestDtoItem.dtoToItemRequest(dto.getRequest()));
        return item;
    }

    public static ItemDto itemToDTO(Item item) {
        if (item == null)
            return null;

        return new ItemDto(item);
    }

    @Data
    public static class UserDtoItem {
        private Long id;
        private String name;
        private String email;

        private static UserDtoItem userToDTO(User user) {
            if (user == null)
                return null;

            UserDtoItem dto = new UserDtoItem();
            dto.setId(user.getId());
            dto.setName(user.getName());
            dto.setEmail(user.getEmail());
            return dto;
        }

        private static User dtoToUser(UserDtoItem dto) {
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
    public static class ItemRequestDtoItem {
        private Long id;
        private String description;
        private UserDtoItem requestor;

        private static ItemRequestDtoItem itemRequestToDTO(ItemRequest itemRequest) {
            if (itemRequest == null)
                return null;

            ItemRequestDtoItem dto = new ItemRequestDtoItem();
            dto.setId(itemRequest.getId());
            dto.setDescription(itemRequest.getDescription());
            dto.setRequestor(UserDtoItem.userToDTO(itemRequest.getRequestor()));
            return dto;
        }

        private static ItemRequest dtoToItemRequest(ItemRequestDtoItem dto) {
            if (dto == null)
                return null;

            ItemRequest request = new ItemRequest();
            request.setId(dto.getId());
            request.setDescription(dto.getDescription());
            request.setRequestor(UserDtoItem.dtoToUser(dto.getRequestor()));
            return request;
        }

    }

}
