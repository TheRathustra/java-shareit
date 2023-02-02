package ru.practicum.shareit.request.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.user.model.User;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@DataJpaTest
@Sql("/testData.sql")
class ItemRequestRepositoryTest {

    @Autowired
    private ItemRequestRepository itemRequestRepository;

    private final User user = new User(
            1L,
            "userName",
            "user@email.com"
    );

    @Test
    void findAllByRequestorId() {
        List<ItemRequest> itemRequests = itemRequestRepository.findAllByRequestorId(user.getId());
        assertThat(itemRequests.size(), equalTo(1));
    }

    @Test
    void findAllByRequestorIdNot() {
        List<ItemRequest> itemRequests = itemRequestRepository.findAllByRequestorIdNot(2L, null);
        assertThat(itemRequests.size(), equalTo(1));
    }

}