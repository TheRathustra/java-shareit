package ru.practicum.shareit.request;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.global.util.Utils;
import ru.practicum.shareit.global.util.Validator;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.request.service.ItemRequestService;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.Map;
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
    public ItemRequestDto create(@RequestHeader Map<String, String> headers,
                                 @RequestBody @Valid ItemRequestDto requestDto) {
        Long userId = Utils.getUserFromHeaders(headers);
        ItemRequest request = ItemRequestDto.dtoToInstance(requestDto);
        ItemRequest newItemRequest = itemRequestService.add(request, userId);
        return ItemRequestDto.instanceToDto(newItemRequest);
    }

    @GetMapping
    public List<ItemRequestDto> getItemRequests(@RequestHeader Map<String, String> headers) {
        Long userId = Utils.getUserFromHeaders(headers);
        List<ItemRequest> requests = itemRequestService.getItemRequests(userId);
        return requests.stream().map(ItemRequestDto::instanceToDto).collect(Collectors.toList());
    }

    @GetMapping("/all")
    public List<ItemRequestDto> getAllItemRequests(@RequestHeader Map<String, String> headers,
                                                   @RequestParam(name = "from", required = false) String fromStr,
                                                   @RequestParam(name = "size", required = false) String sizeStr) {

        if (Validator.isEmptyPageSize(fromStr, sizeStr))
            return Collections.emptyList();

        Long userId = Utils.getUserFromHeaders(headers);
        Pageable pageRequest = Utils.getPageRequest(fromStr, sizeStr, Sort.by(Sort.Direction.DESC,"created"));
        List<ItemRequest> requests = itemRequestService.getAllItemRequests(userId, pageRequest);

        return requests.stream().map(ItemRequestDto::instanceToDto).collect(Collectors.toList());

    }

    @GetMapping("/{requestId}")
    public ItemRequestDto getItemRequestById(@RequestHeader Map<String, String> headers,
                                             @PathVariable("requestId") Long id) {
        Long userId = Utils.getUserFromHeaders(headers);
        ItemRequest request = itemRequestService.getItemRequestById(userId, id);
        return ItemRequestDto.instanceToDto(request);
    }

}
