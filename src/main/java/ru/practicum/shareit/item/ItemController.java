package ru.practicum.shareit.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.global.Utils;
import ru.practicum.shareit.item.comment.Comment;
import ru.practicum.shareit.item.comment.CommentAnswerDto;
import ru.practicum.shareit.item.comment.CommentDTO;
import ru.practicum.shareit.item.dto.ItemAnswer;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Locale;
import java.util.Map;
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
    public ItemAnswer getItemById(@RequestHeader Map<String, String> headers, @PathVariable("id") Long itemId) {
        Long userId = Utils.getUserFromHeaders(headers);
        return itemService.getItemById(itemId, userId);
    }

    @GetMapping
    public List<ItemAnswer> getItems(@RequestHeader Map<String, String> headers) {
        Long userId = Utils.getUserFromHeaders(headers);
        return itemService.getItems(userId);
    }

    @GetMapping("/search")
    public List<ItemDto> getItemsByText(HttpServletRequest request) {
        String text = request.getParameter("text").toUpperCase(Locale.ROOT).trim();
        List<Item> items = itemService.getItemsByText(text);
        return items.stream().map(ItemDto::itemToDTO).collect(Collectors.toList());
    }

    @PostMapping
    @Transactional
    public ItemDto create(@RequestHeader Map<String, String> headers, @RequestBody @Valid ItemDto itemDto) {
        Long userId = Utils.getUserFromHeaders(headers);
        Item item = ItemDto.dtoToItem(itemDto);
        Item newItem = itemService.add(userId, item);
        return ItemDto.itemToDTO(newItem);
    }

    @PostMapping("{itemId}/comment")
    @Transactional
    public CommentAnswerDto addComment(@RequestHeader Map<String, String> headers, @PathVariable("itemId") Long itemId,  @RequestBody @Valid CommentDTO commentDTO) {
        Long userId = Utils.getUserFromHeaders(headers);
        Comment comment = CommentDTO.dtoToComment(commentDTO);
        Comment newComment = itemService.addComment(userId, itemId, comment);
        return CommentAnswerDto.commentToDto(newComment);
    }

    @PatchMapping("/{id}")
    @Transactional
    public ItemDto update(@RequestHeader Map<String, String> headers, @PathVariable("id") long id, @RequestBody ItemDto itemDto) {
        Long userId = Utils.getUserFromHeaders(headers);
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
