package ru.practicum.shareit.item.comment;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
class CommentDTOTest {

    @Autowired
    private JacksonTester<CommentDTO> json;

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

    private final Comment comment = new Comment(
            1L,
            "test",
            item,
            user,
            LocalDateTime.now()
    );

    private final CommentDTO commentDTO = new CommentDTO(comment);

    @Test
    @SneakyThrows
    void commentDtoToJsonTest() {
        JsonContent<CommentDTO> result = json.write(commentDTO);

        assertThat(result).hasJsonPath("$.id");
        assertThat(result).hasJsonPath("$.text");
        assertThat(result).hasJsonPath("$.item");
        assertThat(result).hasJsonPath("$.author");
        assertThat(result).hasJsonPath("$.created");
        assertThat(result).extractingJsonPathNumberValue("$.id").isEqualTo(commentDTO.getId().intValue());
        assertThat(result).extractingJsonPathStringValue("$.text").isEqualTo(commentDTO.getText());

    }

}