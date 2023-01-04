package ru.practicum.shareit.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.User;

import java.util.List;

@Component
public class ItemServiceImpl implements ItemService {

    private ItemStorage itemStorage;

    @Autowired
    public ItemServiceImpl(ItemStorage itemStorage) {
        this.itemStorage = itemStorage;
    }

    public Item add(User user, ItemDto itemDto) {
        return itemStorage.add(user, itemDto);
    }

    public Item update(User user, long id, ItemDto itemDto) {
        return itemStorage.update(user, id, itemDto);
    }

    public void delete(long id) {
        itemStorage.delete(id);
    }

    public Item getItemById(long id) {
        return itemStorage.getItemById(id);
    }

    public List<Item> getItems(User user) {
        return itemStorage.getItems(user);
    }

    @Override
    public List<Item> getItemsByText(String text) {
        return itemStorage.findItemsByText(text);
    }
}
