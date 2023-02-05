package ru.practicum.shareit.server.item.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.server.item.model.Item;
import ru.practicum.shareit.server.user.model.User;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemDto {

    private Long id;

    private String name;

    private String description;

    private Boolean available;

    private UserDtoItem owner;

    private Long requestId;

    public ItemDto(Item item) {
        this.id = item.getId();
        this.name = item.getName();
        this.description = item.getDescription();
        this.available = item.getAvailable();
        this.owner = UserDtoItem.userToDTO(item.getOwner());
        this.requestId = item.getRequestId();
    }

    public static Item dtoToItem(ItemDto dto) {
        if (dto == null)
            return null;

        Item item = new Item();
        item.setId(dto.getId());
        item.setName(dto.getName());
        item.setDescription(dto.getDescription());
        item.setAvailable(dto.getAvailable());
        item.setOwner(UserDtoItem.dtoToUser(dto.getOwner()));
        item.setRequestId(dto.getRequestId());
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

}
