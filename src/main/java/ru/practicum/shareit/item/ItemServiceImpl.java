package ru.practicum.shareit.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserService;

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
        User user = userService.getUserById(userId);
        return itemStorage.add(user, itemDto);
    }

    @Override
    public Item update(Long userId, long id, ItemDto itemDto) {
        User user = userService.getUserById(userId);
        return itemStorage.update(user, id, itemDto);
    }

    @Override
    public void delete(long id) {
        itemStorage.delete(id);
    }

    @Override
    public Item getItemById(long id) {
        return itemStorage.getItemById(id);
    }

    @Override
    public List<Item> getItems(Long userId) {
        User user = userService.getUserById(userId);
        return itemStorage.getItems(user);
    }

    @Override
    public List<Item> getItemsByText(String text) {
        return itemStorage.findItemsByText(text);
    }
}
