package ru.practicum.shareit.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserService;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.dto.UserMapper;

import java.util.ArrayList;
import java.util.List;

@Component
public class ItemServiceImpl implements ItemService {

    private ItemStorage itemStorage;
    private UserService userService;

    @Autowired
    public ItemServiceImpl(ItemStorage itemStorage, UserService userService) {
        this.itemStorage = itemStorage;
        this.userService = userService;
    }

    @Override
    public ItemDto add(Long userId, ItemDto itemDto) {
        UserDto userDto = userService.getUserById(userId);
        User user = UserMapper.dtoToUser(userDto);
        Item item = ItemMapper.dtoToItem(itemDto);

        Item itemAdded = itemStorage.add(user, item);

        return ItemMapper.itemToDTO(itemAdded);
    }

    @Override
    public ItemDto update(Long userId, long id, ItemDto itemDto) {
        UserDto userDto = userService.getUserById(userId);
        User user = UserMapper.dtoToUser(userDto);
        Item item = ItemMapper.dtoToItem(itemDto);

        Item itemUpdated = itemStorage.update(user, id, item);

        return ItemMapper.itemToDTO(itemUpdated);
    }

    @Override
    public void delete(long id) {
        itemStorage.delete(id);
    }

    @Override
    public ItemDto getItemById(long id) {
        Item item = itemStorage.getItemById(id);
        return ItemMapper.itemToDTO(item);
    }

    @Override
    public List<ItemDto> getItems(Long userId) {
        List<Item> items = itemStorage.getItems(userId);
        List<ItemDto> itemsDTO = new ArrayList<>();
        for (Item item : items)
            itemsDTO.add(ItemMapper.itemToDTO(item));
        return itemsDTO;
    }

    @Override
    public List<ItemDto> getItemsByText(String text) {
        List<Item> items = itemStorage.findItemsByText(text);
        List<ItemDto> itemsDTO = new ArrayList<>();
        for (Item item : items) {
            itemsDTO.add(ItemMapper.itemToDTO(item));
        }
        return itemsDTO;
    }
}
