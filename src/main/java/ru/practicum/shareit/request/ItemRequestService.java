package ru.practicum.shareit.request;

import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ItemRequestService {

    ItemRequest add(ItemRequest request, Long userId);
    List<ItemRequest> getItemRequests(Long userId);

    List<ItemRequest> getAllItemRequests(Long userId, Pageable pageRequest);

    ItemRequest getItemRequestById(Long userId, Long id);

}
