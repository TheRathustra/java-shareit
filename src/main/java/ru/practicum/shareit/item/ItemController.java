package ru.practicum.shareit.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.error.EmptyHeaderException;
import ru.practicum.shareit.item.error.UserNotFoundException;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * TODO Sprint add-controllers.
 */
@RestController
@RequestMapping("/items")
public class ItemController {

    private ItemService itemService;
    private UserService userService;

    @Autowired
    public ItemController(ItemService itemService, UserService userService) {
        this.itemService = itemService;
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public Item getItemById(@PathVariable("id") long id) {
        return itemService.getItemById(id);
    }

    @GetMapping
    public List<Item> getItems(@RequestHeader Map<String, String> headers) {
        User user = getUserFromHeaders(headers);
        return itemService.getItems(user);
    }

    @GetMapping("/search")
    public List<Item> getItemsByText(HttpServletRequest request) {
        String text = request.getParameter("text").toLowerCase();
        return itemService.getItemsByText(text);
    }

    @PostMapping
    public Item create(@RequestHeader Map<String, String> headers, @RequestBody @Valid ItemDto itemDto) {
        User user = getUserFromHeaders(headers);
        return itemService.add(user, itemDto);
    }

    @PatchMapping("/{id}")
    public Item update(@RequestHeader Map<String, String> headers, @PathVariable("id") long id, @RequestBody ItemDto itemDto) {
        User user = getUserFromHeaders(headers);
        return itemService.update(user, id, itemDto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") long id) {
        itemService.delete(id);
    }

    private User getUserFromHeaders(Map<String, String> headers) {
        User user = null;
        String userId = headers.get("x-sharer-user-id");
        if (userId == null) {
            throw new EmptyHeaderException();
        }
        long id = Long.parseLong(userId);
        try {
            user = userService.getUserById(id);
        } catch (IllegalArgumentException e) {
            user = null;
        }

        if (user == null) {
            throw new UserNotFoundException();
        }
        return user;
    }

}
