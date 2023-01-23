package ru.practicum.shareit.item;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ItemStorageImp implements ItemStorage {

    private Map<Long, Item> items = new HashMap();
    private long id = 1;

    @Override
    public Item add(User user, Item item) {

        item.setOwner(user);
        item.setId(id);
        items.put(id, item);

        id++;
        return item;

    }

    @Override
    public Item update(User user, long id, Item item) {
        Item itemDB = items.get(id);

        if (itemDB == null)
            throw new IllegalArgumentException();

        if (!user.equals(itemDB.getOwner()))
            throw new IllegalArgumentException();

        if (item.getName() != null)
            itemDB.setName(item.getName());

        if (item.getDescription() != null)
            itemDB.setDescription(item.getDescription());

        if (item.getAvailable() != null)
            itemDB.setAvailable(item.getAvailable());

        return itemDB;
    }

    @Override
    public void delete(long id) {
        Item item = items.get(id);

        if (item == null)
            throw new IllegalArgumentException();

        items.remove(id);
    }

    @Override
    public Item getItemById(long id) {
        Item item = items.get(id);
        if (item == null)
            throw new IllegalArgumentException();

        return item;
    }

    @Override
    public List<Item> getItems(Long idOwner) {
        List<Item> userItems = new ArrayList<>();
        for (Item item : items.values()) {
          if (item.getOwner().getId() == idOwner) {
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

        for (Item item : items.values()) {
            if (item.getAvailable() && (item.getName().toLowerCase().contains(text)
                    || item.getDescription().toLowerCase().contains(text))) {
                availableItems.add(item);
            }
        }
        return availableItems;
    }
}
