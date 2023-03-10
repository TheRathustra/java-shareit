package ru.practicum.shareit.server.item.dto;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import ru.practicum.shareit.server.item.comment.Comment;
import ru.practicum.shareit.server.item.model.Item;
import ru.practicum.shareit.server.user.model.User;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
class ItemAnswerTest {

    @Autowired
    private JacksonTester<ItemAnswer> json;

    private final User user = new User(
            1L,
            "userName",
            "user@email.com"
    );

    private final Item item = new Item(
            1L,
            "test",
            "test",
            true, user,
            1L
    );

    private final ItemAnswer itemAnswer = new ItemAnswer(item);

    private final Comment comment = new Comment(
            1L,
            "test",
            item,
            user,
            LocalDateTime.now()
    );

    @Test
    @SneakyThrows
    void itemAnswerTestToJson() {
        itemAnswer.setComments(List.of(comment));

        JsonContent<ItemAnswer> result = json.write(itemAnswer);

        assertThat(result).hasJsonPath("$.id");
        assertThat(result).hasJsonPath("$.name");
        assertThat(result).hasJsonPath("$.description");
        assertThat(result).hasJsonPath("$.available");
        assertThat(result).hasJsonPath("$.owner");
        assertThat(result).hasJsonPath("$.requestId");
        assertThat(result).extractingJsonPathNumberValue("$.id").isEqualTo(itemAnswer.getId().intValue());
        assertThat(result).extractingJsonPathStringValue("$.name").isEqualTo(itemAnswer.getName());
        assertThat(result).extractingJsonPathStringValue("$.description").isEqualTo(itemAnswer.getDescription());
        assertThat(result).extractingJsonPathBooleanValue("$.available").isEqualTo(itemAnswer.getAvailable());
        assertThat(result).extractingJsonPathNumberValue("$.requestId").isEqualTo(itemAnswer.getRequestId().intValue());
    }

}