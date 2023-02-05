package ru.practicum.shareit.request.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.request.error.ItemRequestNotFoundException;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.request.repository.ItemRequestRepository;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.service.UserService;

import java.util.List;
import java.util.Optional;

@Service
public class ItemRequestServiceImpl implements ItemRequestService {

    private final ItemRequestRepository repository;
    private final UserService userService;

    @Autowired
    public ItemRequestServiceImpl(ItemRequestRepository repository, UserService userService) {
        this.repository = repository;
        this.userService = userService;
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
        return repository.findAllByRequestorId(userId);
    }

    @Override
    public List<ItemRequest> getAllItemRequests(Long userId, Pageable pageRequest) {
        return repository.findAllByRequestorIdNot(userId, pageRequest);
    }

    @Override
    public ItemRequest getItemRequestById(Long userId, Long id) {
        userService.getUserById(userId);
        Optional<ItemRequest> requestOptional = repository.findById(id);
        if (requestOptional.isEmpty())
            throw new ItemRequestNotFoundException();

        return requestOptional.get();
    }
}
