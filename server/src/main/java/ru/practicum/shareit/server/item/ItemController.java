package ru.practicum.shareit.server.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.server.global.util.Utils;
import ru.practicum.shareit.server.item.comment.Comment;
import ru.practicum.shareit.server.item.comment.CommentAnswerDto;
import ru.practicum.shareit.server.item.comment.CommentDTO;
import ru.practicum.shareit.server.item.dto.ItemAnswer;
import ru.practicum.shareit.server.item.dto.ItemDto;
import ru.practicum.shareit.server.item.model.Item;
import ru.practicum.shareit.server.item.service.ItemService;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/items")
public class ItemController {

    private final ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/{id}")
    public ItemAnswer getItemById(@RequestHeader("X-Sharer-User-Id") long userId,
                                  @PathVariable("id") Long itemId) {
        return itemService.getItemById(itemId, userId);
    }

    @GetMapping
    public List<ItemAnswer> getItems(@RequestHeader("X-Sharer-User-Id") long userId,
                                     @RequestParam(name = "from", required = false) Integer from,
                                     @RequestParam(name = "size", required = false) Integer size) {

        Pageable pageRequest = Utils.getPageRequest(from, size);
        return itemService.getItems(userId, pageRequest);
    }

    @GetMapping("/search")
    public List<ItemDto> getItemsByText(@RequestParam(name = "text", required = false) String text,
                                        @RequestParam(name = "from", required = false) Integer from,
                                        @RequestParam(name = "size", required = false) Integer size) {

        if (text == null || text.isBlank()) {
            return Collections.emptyList();
        }

        Pageable pageRequest = Utils.getPageRequest(from, size);
        text = text.toUpperCase(Locale.ROOT).trim();
        List<Item> items = itemService.getItemsByText(text, pageRequest);
        return items.stream().map(ItemDto::itemToDTO).collect(Collectors.toList());
    }

    @PostMapping
    @Transactional
    public ItemDto create(@RequestHeader("X-Sharer-User-Id") long userId,
                          @RequestBody ItemDto itemDto) {
        Item item = ItemDto.dtoToItem(itemDto);
        Item newItem = itemService.add(userId, item);
        return ItemDto.itemToDTO(newItem);
    }

    @PostMapping("{itemId}/comment")
    @Transactional
    public CommentAnswerDto addComment(@RequestHeader("X-Sharer-User-Id") long userId,
                                       @PathVariable("itemId") Long itemId,
                                       @RequestBody CommentDTO commentDTO) {
        Comment comment = CommentDTO.dtoToComment(commentDTO);
        Comment newComment = itemService.addComment(userId, itemId, comment);
        return CommentAnswerDto.commentToDto(newComment);
    }

    @PatchMapping("/{id}")
    @Transactional
    public ItemDto update(@RequestHeader("X-Sharer-User-Id") long userId,
                          @PathVariable("id") long id,
                          @RequestBody ItemDto itemDto) {
        Item item = ItemDto.dtoToItem(itemDto);
        Item updatedItem = itemService.update(userId, id, item);
        return ItemDto.itemToDTO(updatedItem);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public void delete(@PathVariable("id") long id) {
        itemService.delete(id);
    }

}
