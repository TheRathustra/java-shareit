package ru.practicum.shareit.request.service;

import org.springframework.data.domain.Pageable;
import ru.practicum.shareit.request.model.ItemRequest;

import java.util.List;

public interface ItemRequestService {

    ItemRequest add(ItemRequest request, Long userId);

    List<ItemRequest> getItemRequests(Long userId);

    List<ItemRequest> getAllItemRequests(Long userId, Pageable pageRequest);

    ItemRequest getItemRequestById(Long userId, Long id);

}
