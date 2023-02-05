package ru.practicum.shareit.gateway.item;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.gateway.item.client.ItemClient;
import ru.practicum.shareit.gateway.item.comment.CommentDTO;
import ru.practicum.shareit.gateway.item.dto.ItemDto;

@RestController
@RequestMapping("/items")
public class ItemController {

    private final ItemClient client;

    @Autowired
    public ItemController(ItemClient itemClient) {
        this.client = itemClient;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<Object> create(@RequestHeader("X-Sharer-User-Id") long userId,
                                         @RequestBody @Valid ItemDto itemDto) {
        ResponseEntity<Object> newItem = client.create(userId, itemDto);
        return newItem;
    }

    @PatchMapping("/{id}")
    @Transactional
    public ResponseEntity<Object> update(@RequestHeader("X-Sharer-User-Id") long userId,
                                         @PathVariable("id") long id,
                                         @RequestBody ItemDto itemDto) {
        ResponseEntity<Object> updatedItem = client.update(userId, id, itemDto);
        return updatedItem;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getItemById(@RequestHeader("X-Sharer-User-Id") long userId,
                                  @PathVariable("id") Long itemId) {
        return client.getItemById(userId, itemId);
    }

    @GetMapping
    public ResponseEntity<Object> getItems(@RequestHeader("X-Sharer-User-Id") long userId,
                                     @RequestParam(name = "from", required = false) Integer from,
                                     @RequestParam(name = "size", required = false) Integer size) {

        return client.getItems(userId, from, size);
    }

    @GetMapping("/search")
    public ResponseEntity<Object> getItemsByText(@RequestParam String text,
                                        @PositiveOrZero @RequestParam(name = "from", required = false) Integer from,
                                        @Positive @RequestParam(name = "size", required = false) Integer size) {

        ResponseEntity<Object> items = client.getItemsByText(text, from, size);
        return items;
    }

    @PostMapping("{itemId}/comment")
    @Transactional
    public ResponseEntity<Object> createComment(@RequestHeader("X-Sharer-User-Id") long userId,
                                       @PathVariable("itemId") Long itemId,
                                       @RequestBody @Valid CommentDTO commentDTO) {
        ResponseEntity<Object> newComment = client.createComment(userId, itemId, commentDTO);
        return newComment;
    }

    @DeleteMapping("/{id}")
    @Transactional
    public void delete(@RequestHeader("X-Sharer-User-Id") long userId, @PathVariable("id") long id) {
        client.delete(userId, id);
    }

}
