package ru.practicum.shareit.request.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.service.ItemService;
import ru.practicum.shareit.request.error.ItemRequestNotFoundException;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.request.repository.ItemRequestRepository;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.service.UserService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ItemRequestServiceImplTest {

    @InjectMocks
    private ItemRequestServiceImpl service;

    @Mock
    private ItemRequestRepository repository;

    @Mock
    private UserService userService;

    @Mock
    private ItemService itemService;

    private final User user = new User(
            1L,
            "userName",
            "user@email.ru"
    );

    private final Item item = new Item(
            1L,
            "test",
            "test",
            true, user,
            1L
    );

    private final ItemRequest request = new ItemRequest(
            1L,
            "test",
            user,
            LocalDateTime.now(),
            List.of(item)
    );

    @Test
    void add() {

        when(userService.getUserById(Mockito.anyLong())).thenReturn(user);
        when(repository.saveAndFlush(Mockito.any())).thenReturn(request);

        ItemRequest actualRequest = service.add(request, user.getId());
        assertThat(actualRequest, equalTo(request));

    }

    @Test
    void getItemRequests() {
        when(userService.getUserById(Mockito.anyLong())).thenReturn(user);
        when(repository.findAllByRequestorId(Mockito.anyLong())).thenReturn(List.of(request));
        when(itemService.getItemsByRequestId(Mockito.anyLong())).thenReturn(List.of(item));

        List<ItemRequest> actualRequests = service.getItemRequests(user.getId());
        assertThat(actualRequests, equalTo(List.of(request)));
    }

    @Test
    void getItemRequestById_whenRequestIsFound_thenReturnItemRequest() {
        when(userService.getUserById(Mockito.anyLong())).thenReturn(user);
        when(repository.findById(Mockito.anyLong())).thenReturn(Optional.of(request));
        when(itemService.getItemsByRequestId(Mockito.anyLong())).thenReturn(List.of(item));
        ItemRequest actualRequest = service.getItemRequestById(user.getId(), request.getId());
        assertThat(actualRequest, equalTo(request));
    }

    @Test
    void getItemRequestById_whenRequestNotFound_thenThrowItemRequestNotFoundException() {
        when(userService.getUserById(Mockito.anyLong())).thenReturn(user);
        when(repository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
        Assertions.assertThrows(
                ItemRequestNotFoundException.class,
                () -> service.getItemRequestById(user.getId(), request.getId()));
        verify(itemService, never()).getItemsByRequestId(Mockito.anyLong());
    }

    @Test
    void getAllItemRequests() {
        when(itemService.getItemsByRequestId(Mockito.anyLong())).thenReturn(List.of(item));
        when(repository.findAllByRequestorIdNot(Mockito.anyLong(), Mockito.any()))
                .thenReturn(List.of(request));
        List<ItemRequest> actualRequests = service.getAllItemRequests(user.getId(), null);
        assertThat(actualRequests, equalTo(List.of(request)));
    }
}