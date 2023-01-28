package ru.practicum.shareit.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.comment.Comment;
import ru.practicum.shareit.item.comment.CommentAnswerDto;
import ru.practicum.shareit.item.comment.CommentDTO;
import ru.practicum.shareit.item.dto.ItemAnswer;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemMapper;
import ru.practicum.shareit.item.error.EmptyHeaderException;
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

    private ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/{id}")
    public ItemAnswer getItemById(@PathVariable("id") long id) {
        ItemAnswer item = itemService.getItemById(id);
        return item;
    }

    @GetMapping
    public List<ItemDto> getItems(@RequestHeader Map<String, String> headers) {
        Long userId = getUserFromHeaders(headers);
        List<Item> items = itemService.getItems(userId);
        List<ItemDto> itemsDto = items.stream().map(ItemMapper::itemToDTO).collect(Collectors.toList());
        return itemsDto;
    }

    @GetMapping("/search")
    public List<ItemDto> getItemsByText(HttpServletRequest request) {
        String text = request.getParameter("text").toUpperCase(Locale.ROOT).trim();
        List<Item> items = itemService.getItemsByText(text);
        List<ItemDto> itemsDto = items.stream().map(ItemMapper::itemToDTO).collect(Collectors.toList());
        return itemsDto;
    }

    @PostMapping
    public ItemDto create(@RequestHeader Map<String, String> headers, @RequestBody @Valid ItemDto itemDto) {
        Long userId = getUserFromHeaders(headers);
        Item item = ItemMapper.dtoToItem(itemDto);
        Item newItem = itemService.add(userId, item);
        return ItemMapper.itemToDTO(newItem);
    }

    @PostMapping("{itemId}/comment")
    public CommentAnswerDto addComment(@RequestHeader Map<String, String> headers, @PathVariable("itemId") Long itemId,  @RequestBody @Valid CommentDTO commentDTO) {
        Long userId = getUserFromHeaders(headers);
        Comment comment = CommentDTO.dtoToComment(commentDTO);
        Comment newComment = itemService.addComment(userId, itemId, comment);
        return CommentAnswerDto.commentToDto(newComment);
    }

    @PatchMapping("/{id}")
    public ItemDto update(@RequestHeader Map<String, String> headers, @PathVariable("id") long id, @RequestBody ItemDto itemDto) {
        Long userId = getUserFromHeaders(headers);
        Item item = ItemMapper.dtoToItem(itemDto);
        Item updatedItem = itemService.update(userId, id, item);
        return ItemMapper.itemToDTO(updatedItem);
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
