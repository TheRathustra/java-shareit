package ru.practicum.shareit.server.item;

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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.practicum.shareit.server.item.comment.Comment;
import ru.practicum.shareit.server.item.dto.ItemAnswer;
import ru.practicum.shareit.server.item.dto.ItemDto;
import ru.practicum.shareit.server.item.model.Item;
import ru.practicum.shareit.server.item.service.ItemService;
import ru.practicum.shareit.server.user.model.User;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@WebMvcTest(controllers = ItemController.class)
class ItemControllerTest {

    @MockBean
    private ItemService itemService;
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

    private final ItemDto itemDto = ItemDto.itemToDTO(item);

    private final ItemAnswer itemAnswer = ItemAnswer.itemToAnswerDTO(item);

    @Test
    @SneakyThrows
    void getItemById() {
        Long itemId = 1L;
        when(itemService.getItemById(Mockito.anyLong(), Mockito.anyLong()))
                .thenReturn(itemAnswer);

        MvcResult mvcResult = mvc.perform(get("/items/{id}", itemId)
                .header("x-sharer-user-id", user.getId())
                .characterEncoding(StandardCharsets.UTF_8)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andReturn();

        assertThat(mvcResult.getResponse().getContentAsString(), equalTo(mapper.writeValueAsString(itemAnswer)));
        verify(itemService).getItemById(itemId, user.getId());
    }

    @Test
    @SneakyThrows
    void getItems() {
        List<ItemAnswer> items = List.of(itemAnswer);
        when(itemService.getItems(Mockito.anyLong(), Mockito.any()))
                .thenReturn(items);

        MvcResult mvcResult = mvc.perform(get("/items")
                .header("x-sharer-user-id", user.getId())
                .characterEncoding(StandardCharsets.UTF_8)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andReturn();

        assertThat(mvcResult.getResponse().getContentAsString(), equalTo(mapper.writeValueAsString(items)));
        verify(itemService).getItems(Mockito.anyLong(), Mockito.any());
    }

    @Test
    @SneakyThrows
    void getItemsByText() {
        List<ItemDto> items = List.of(itemDto);
        when(itemService.getItemsByText(Mockito.anyString(), Mockito.any()))
                .thenReturn(List.of(item));

        MvcResult mvcResult = mvc.perform(get("/items/search")
                        .param("text", "test")).andReturn();

        assertThat(mvcResult.getResponse().getContentAsString(), equalTo(mapper.writeValueAsString(items)));
        verify(itemService).getItemsByText(Mockito.anyString(), Mockito.any());
    }

    @Test
    @SneakyThrows
    void create() {
        when(itemService.add(Mockito.anyLong(), Mockito.any()))
                .thenReturn(item);

        mvc.perform(post("/items")
                        .header("x-sharer-user-id", user.getId())
                        .content(mapper.writeValueAsString(itemDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(item.getId()), Long.class))
                .andExpect(jsonPath("$.name", is(item.getName())))
                .andExpect(jsonPath("$.description", is(item.getDescription())))
                .andExpect(jsonPath("$.available", is(item.getAvailable())));

        verify(itemService).add(Mockito.anyLong(), Mockito.any());
    }

    @Test
    @SneakyThrows
    void addComment() {
        Long itemId = item.getId();
        Comment comment = new Comment(1L, "test", item, user, LocalDateTime.now());
        when(itemService.addComment(Mockito.anyLong(), Mockito.anyLong(), Mockito.any()))
                .thenReturn(comment);

        mvc.perform(post("/items/{id}/comment", itemId)
                        .header("x-sharer-user-id", user.getId())
                        .content(mapper.writeValueAsString(comment))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(comment.getId()), Long.class))
                .andExpect(jsonPath("$.text", is(comment.getText())));

        verify(itemService).addComment(Mockito.anyLong(), Mockito.anyLong(), Mockito.any());
    }

    @Test
    @SneakyThrows
    void update() {
        when(itemService.update(Mockito.anyLong(), Mockito.anyLong(), Mockito.any()))
                .thenReturn(item);

        mvc.perform(patch("/items/{id}", item.getId())
                        .header("x-sharer-user-id", user.getId())
                        .content(mapper.writeValueAsString(item))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(item.getId()), Long.class))
                .andExpect(jsonPath("$.name", is(item.getName())))
                .andExpect(jsonPath("$.description", is(item.getDescription())))
                .andExpect(jsonPath("$.available", is(item.getAvailable())));
        verify(itemService).update(Mockito.anyLong(), Mockito.anyLong(), Mockito.any());
    }

    @Test
    @SneakyThrows
    void delete() {
        mvc.perform(MockMvcRequestBuilders.delete("/items/{id}", item.getId()))
                .andExpect(status().isOk());
    }
}