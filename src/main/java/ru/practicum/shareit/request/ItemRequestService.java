package ru.practicum.shareit.request;

import java.util.List;

public interface ItemRequestService {

    ItemRequest add(ItemRequest request, Long userId);
    List<ItemRequest> getItemRequests(Long userId);

    List<ItemRequest> getAllItemRequests(Long userId, int from, int size);

    ItemRequest getItemRequestById(Long userId, Long id);

}
