package ru.practicum.shareit.request;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.global.Utils;
import ru.practicum.shareit.request.dto.ItemRequestDto;

import javax.validation.Valid;
import java.util.ArrayList;
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
    public ItemRequestDto create(@RequestHeader Map<String, String> headers, @RequestBody @Valid ItemRequestDto requestDto) {
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
    public List<ItemRequestDto> getAllItemRequests(@RequestHeader Map<String, String> headers, @RequestParam(name = "from", required = false, defaultValue = "0") String fromStr, @RequestParam(name = "size", required = false) String sizeStr) {

        if (fromStr == null && sizeStr == null)
            return new ArrayList<>();

        Long userId = Utils.getUserFromHeaders(headers);
        int from = Integer.parseInt(fromStr);
        int size = Integer.parseInt(sizeStr);
        List<ItemRequest> requests = itemRequestService.getAllItemRequests(userId, from, size);

        return requests.stream().map(ItemRequestDto::instanceToDto).collect(Collectors.toList());

    }

    @GetMapping("/{requestId}")
    public ItemRequestDto getItemRequestById(@RequestHeader Map<String, String> headers, @PathVariable("requestId") Long id) {
        Long userId = Utils.getUserFromHeaders(headers);
        ItemRequest request = itemRequestService.getItemRequestById(userId, id);
        return ItemRequestDto.instanceToDto(request);
    }

}
