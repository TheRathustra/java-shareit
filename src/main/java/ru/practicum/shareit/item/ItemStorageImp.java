package ru.practicum.shareit.item;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.User;

import java.util.ArrayList;
import java.util.List;

@Component
public class ItemStorageImp implements ItemStorage {

    private List<Item> items = new ArrayList<>();
    private long id = 1;

    @Override
    public Item add(User user, ItemDto itemDto) {

        Item item = new Item();
        item.setId(id);
        item.setDescription(itemDto.getDescription());
        item.setName(itemDto.getName());
        item.setAvailable(itemDto.getAvailable());
        item.setOwner(user);

        items.add(item);
        id++;
        return item;

    }

    @Override
    public Item update(User user, long id, ItemDto itemDto) {
        Item item = null;
        for (Item itemDB : items) {
            if (itemDB.getId() == id) {
                item = itemDB;
                break;
            }
        }
        if (item == null) {
            throw new IllegalArgumentException();
        }

        if (!user.equals(item.getOwner())) {
            throw new IllegalArgumentException();
        }

        if (itemDto.getName() != null) {
            item.setName(itemDto.getName());
        }
        if (itemDto.getDescription() != null) {
            item.setDescription(itemDto.getDescription());
        }
        if (itemDto.getAvailable() != null) {
            item.setAvailable(itemDto.getAvailable());
        }
        return item;
    }

    @Override
    public void delete(long id) {
        Item item = null;
        for (Item itemDB : items) {
            if (itemDB.getId() == id) {
                item = itemDB;
                break;
            }
        }
        if (item == null) {
            throw new IllegalArgumentException();
        }
        items.remove(item);
    }

    @Override
    public Item getItemById(long id) {
        Item item = null;
        for (Item itemDB : items) {
            if (itemDB.getId() == id) {
                item = itemDB;
                break;
            }
        }
        if (item == null) {
            throw new IllegalArgumentException();
        }
        return item;
    }

    @Override
    public List<Item> getItems(User owner) {
        List<Item> userItems = new ArrayList<>();
        for (Item item : items) {
          if (item.getOwner() == owner) {
              userItems.add(item);
          }
        }

        return userItems;
    }

    @Override
    public List<Item> findItemsByText(String text) {
        List<Item> availableItems = new ArrayList<>();
        if (text.isEmpty()) {
            return availableItems;
        }

        for (Item item : items) {
            if (item.isAvailable() && (item.getName().toLowerCase().contains(text)
                    || item.getDescription().toLowerCase().contains(text))) {
                availableItems.add(item);
            }
        }
        return availableItems;
    }
}
