package ru.practicum.shareit.gateway.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.gateway.request.client.RequestClient;
import ru.practicum.shareit.gateway.request.dto.ItemRequestDto;

@RestController
@RequestMapping(path = "/requests")
public class ItemRequestController {

    private final RequestClient client;

    @Autowired
    public ItemRequestController(RequestClient requestClient) {
        this.client = requestClient;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<Object> create(@RequestHeader("X-Sharer-User-Id") long userId,
                                 @RequestBody @Valid ItemRequestDto requestDto) {
        ResponseEntity<Object> newItemRequest = client.create(userId, requestDto);
        return newItemRequest;
    }

    @GetMapping
    public ResponseEntity<Object> getItemRequests(@RequestHeader("X-Sharer-User-Id") long userId) {
        ResponseEntity<Object> requests = client.getItemRequests(userId);
        return requests;
    }

    @GetMapping("/all")
    public ResponseEntity<Object> getAllItemRequests(@RequestHeader("X-Sharer-User-Id") long userId,
                                                     @PositiveOrZero @RequestParam(name = "from", required = false) Integer from,
                                                     @Positive @RequestParam(name = "size", required = false) Integer size) {

        ResponseEntity<Object> requests = client.getAllItemRequests(userId, from, size);
        return requests;

    }

    @GetMapping("/{requestId}")
    public ResponseEntity<Object> getItemRequestById(@RequestHeader("X-Sharer-User-Id") long userId,
                                             @PathVariable("requestId") Long id) {
        ResponseEntity<Object> request = client.getItemRequestById(userId, id);
        return request;
    }

}
