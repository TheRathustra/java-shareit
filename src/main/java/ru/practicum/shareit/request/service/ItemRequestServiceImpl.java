package ru.practicum.shareit.request.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.item.service.ItemService;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.request.repository.ItemRequestRepository;
import ru.practicum.shareit.request.error.ItemRequestNotFoundException;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.user.service.UserService;
import ru.practicum.shareit.user.model.User;

import java.util.List;
import java.util.Optional;

@Service
public class ItemRequestServiceImpl implements ItemRequestService {

    private final ItemRequestRepository repository;
    private final UserService userService;

    private final ItemService itemService;

    @Autowired
    public ItemRequestServiceImpl(ItemRequestRepository repository, UserService userService, ItemService itemService) {
        this.repository = repository;
        this.userService = userService;
        this.itemService = itemService;
    }

    @Override
    public ItemRequest add(ItemRequest request, Long userId) {
        User user = userService.getUserById(userId);
        request.setRequestor(user);
        repository.saveAndFlush(request);
        return request;
    }

    @Override
    public List<ItemRequest> getItemRequests(Long userId) {
        userService.getUserById(userId);
        List<ItemRequest> requests = repository.findAllByRequestorId(userId);
        for (ItemRequest request : requests) {
            List<Item> items = itemService.getItemsByRequestId(request.getId());
            request.setItems(items);
        }
        return requests;
    }

    @Override
    public List<ItemRequest> getAllItemRequests(Long userId, Pageable pageRequest) {
        List<ItemRequest> requests = repository.findAllByRequestorIdNot(userId, pageRequest);
        for (ItemRequest request : requests) {
            List<Item> items = itemService.getItemsByRequestId(request.getId());
            request.setItems(items);
        }
        return requests;
    }

    @Override
    public ItemRequest getItemRequestById(Long userId, Long id) {
        userService.getUserById(userId);
        Optional<ItemRequest> requestOptional = repository.findById(id);
        if (requestOptional.isEmpty())
            throw new ItemRequestNotFoundException();

        ItemRequest request = requestOptional.get();
        List<Item> items = itemService.getItemsByRequestId(request.getId());
        request.setItems(items);
        return request;
    }
}
