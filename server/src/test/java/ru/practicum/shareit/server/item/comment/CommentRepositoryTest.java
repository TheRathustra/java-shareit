package ru.practicum.shareit.server.item.comment;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import ru.practicum.shareit.server.item.model.Item;
import ru.practicum.shareit.server.user.model.User;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@DataJpaTest
@Sql("/testData.sql")
class CommentRepositoryTest {

    @Autowired
    private CommentRepository repository;

    private final User user = new User(
            1L,
            "userName",
            "user@email.com"
    );

    private final Item item = new Item(
            1L,
            "test",
            "test",
            true,
            user,
            null
    );

    @Test
    void findAllByItem_Id() {
        List<Comment> commentList = repository.findAllByItem_Id(item.getId());
        assertThat(commentList.size(), equalTo(1));
    }

}