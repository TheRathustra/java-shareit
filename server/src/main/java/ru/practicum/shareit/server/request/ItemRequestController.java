package ru.practicum.shareit.server.request;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.server.global.util.Utils;
import ru.practicum.shareit.server.global.util.Validator;
import ru.practicum.shareit.server.request.dto.ItemRequestDto;
import ru.practicum.shareit.server.request.model.ItemRequest;
import ru.practicum.shareit.server.request.service.ItemRequestService;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/requests")
public class ItemRequestController {

    private final ItemRequestService itemRequestService;

    @Autowired
    public ItemRequestController(ItemRequestService itemRequestService) {
        this.itemRequestService = itemRequestService;
    }

    @PostMapping
    @Transactional
    public ItemRequestDto create(@RequestHeader("X-Sharer-User-Id") long userId,
                                 @RequestBody ItemRequestDto requestDto) {
        ItemRequest request = ItemRequestDto.dtoToInstance(requestDto);
        ItemRequest newItemRequest = itemRequestService.add(request, userId);
        return ItemRequestDto.instanceToDto(newItemRequest);
    }

    @GetMapping
    public List<ItemRequestDto> getItemRequests(@RequestHeader("X-Sharer-User-Id") long userId) {
        List<ItemRequest> requests = itemRequestService.getItemRequests(userId);
        return requests.stream().map(ItemRequestDto::instanceToDto).collect(Collectors.toList());
    }

    @GetMapping("/all")
    public List<ItemRequestDto> getAllItemRequests(@RequestHeader("X-Sharer-User-Id") long userId,
                                                   @RequestParam(name = "from", required = false) Integer from,
                                                   @RequestParam(name = "size", required = false) Integer size) {

        if (Validator.isEmptyPageSize(from, size))
            return Collections.emptyList();

        Pageable pageRequest = Utils.getPageRequest(from, size, Sort.by(Sort.Direction.DESC,"created"));
        List<ItemRequest> requests = itemRequestService.getAllItemRequests(userId, pageRequest);

        return requests.stream().map(ItemRequestDto::instanceToDto).collect(Collectors.toList());

    }

    @GetMapping("/{requestId}")
    public ItemRequestDto getItemRequestById(@RequestHeader("X-Sharer-User-Id") long userId,
                                             @PathVariable("requestId") Long id) {
        ItemRequest request = itemRequestService.getItemRequestById(userId, id);
        return ItemRequestDto.instanceToDto(request);
    }

}
