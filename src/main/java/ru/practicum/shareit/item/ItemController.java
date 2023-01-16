package ru.practicum.shareit.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.error.EmptyHeaderException;
import ru.practicum.shareit.item.model.Item;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/items")
public class ItemController {

    private ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/{id}")
    public ItemDto getItemById(@PathVariable("id") long id) {
        return itemService.getItemById(id);
    }

    @GetMapping
    public List<ItemDto> getItems(@RequestHeader Map<String, String> headers) {
        Long userId = getUserFromHeaders(headers);
        return itemService.getItems(userId);
    }

    @GetMapping("/search")
    public List<ItemDto> getItemsByText(HttpServletRequest request) {
        String text = request.getParameter("text").toLowerCase();
        return itemService.getItemsByText(text);
    }

    @PostMapping
    public ItemDto create(@RequestHeader Map<String, String> headers, @RequestBody @Valid ItemDto itemDto) {
        Long userId = getUserFromHeaders(headers);
        return itemService.add(userId, itemDto);
    }

    @PatchMapping("/{id}")
    public ItemDto update(@RequestHeader Map<String, String> headers, @PathVariable("id") long id, @RequestBody ItemDto itemDto) {
        Long userId = getUserFromHeaders(headers);
        return itemService.update(userId, id, itemDto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") long id) {
        itemService.delete(id);
    }

    private Long getUserFromHeaders(Map<String, String> headers) {
        String userId = headers.get("x-sharer-user-id");
        if (userId == null) {
            throw new EmptyHeaderException();
        }
        long id = Long.parseLong(userId);
        return id;
    }

}
