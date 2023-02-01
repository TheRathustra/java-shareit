package ru.practicum.shareit.request;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.request.service.ItemRequestService;
import ru.practicum.shareit.user.model.User;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(controllers = ItemRequestController.class)
class ItemRequestControllerTest {

    @MockBean
    private ItemRequestService itemRequestService;
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper mapper;

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

    private final ItemRequest itemRequest = new ItemRequest(
            1L,
            "test",
            user,
            LocalDateTime.now(),
            List.of(item)
    );

    private final ItemRequestDto itemRequestDto = new ItemRequestDto(itemRequest);

    @Test
    @SneakyThrows
    void create() {
        when(itemRequestService.add(Mockito.any(), Mockito.anyLong()))
                .thenReturn(itemRequest);

        MvcResult mvcResult = mvc.perform(post("/requests")
                        .header("x-sharer-user-id", user.getId())
                        .content(mapper.writeValueAsString(itemRequestDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)).andReturn();

        assertThat(mvcResult.getResponse().getContentAsString(), equalTo(mapper.writeValueAsString(itemRequestDto)));
        verify(itemRequestService).add(Mockito.any(), Mockito.anyLong());
    }

    @Test
    @SneakyThrows
    void getItemRequests() {

        when(itemRequestService.getItemRequests(user.getId()))
                .thenReturn(List.of(itemRequest));

        MvcResult mvcResult = mvc.perform(get("/requests")
                .header("x-sharer-user-id", user.getId())
                .characterEncoding(StandardCharsets.UTF_8)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andReturn();

        assertThat(mvcResult.getResponse().getContentAsString(), equalTo(mapper.writeValueAsString(List.of(itemRequestDto))));
        verify(itemRequestService).getItemRequests(user.getId());

    }

    @Test
    @SneakyThrows
    void getAllItemRequests() {
        when(itemRequestService.getAllItemRequests(Mockito.anyLong(), Mockito.any()))
                .thenReturn(List.of(itemRequest));


        MvcResult mvcResult = mvc.perform(get("/requests/all")
                .header("x-sharer-user-id", user.getId())
                        .param("from", "0")
                        .param("size", "10")
                .characterEncoding(StandardCharsets.UTF_8)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andReturn();

        assertThat(mvcResult.getResponse().getContentAsString(), equalTo(mapper.writeValueAsString(List.of(itemRequestDto))));
        verify(itemRequestService).getAllItemRequests(Mockito.anyLong(), Mockito.any());
    }

    @Test
    @SneakyThrows
    void getItemRequestById() {
        when(itemRequestService.getItemRequestById(user.getId(), itemRequest.getId()))
                .thenReturn(itemRequest);


        MvcResult mvcResult = mvc.perform(get("/requests/{requestId}", itemRequest.getId())
                .header("x-sharer-user-id", user.getId())
                .characterEncoding(StandardCharsets.UTF_8)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andReturn();

        assertThat(mvcResult.getResponse().getContentAsString(), equalTo(mapper.writeValueAsString(itemRequestDto)));
        verify(itemRequestService).getItemRequestById(user.getId(), itemRequest.getId());
    }
}