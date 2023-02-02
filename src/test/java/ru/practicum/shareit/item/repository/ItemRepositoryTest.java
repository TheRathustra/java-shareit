package ru.practicum.shareit.item.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.jdbc.Sql;
import ru.practicum.shareit.global.util.Utils;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@DataJpaTest
@Sql("/testData.sql")
class ItemRepositoryTest {

    @Autowired
    private ItemRepository repository;

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

    private final ItemRequest request = new ItemRequest(
            1L,
            "test",
            user,
            LocalDateTime.now(),
            List.of(item)
    );

    @Test
    void findAllByUser() {
        List<Item> items = repository.findAllByUser(user.getId());
        assertThat(items.size(), equalTo(1));
    }

    @Test
    void findAllByUserByPage() {
        Pageable pageable = Utils.getPageRequest("0", "1");
        Page<Item> items = repository.findAllByUserByPage(user.getId(), pageable);
        assertThat(items.getTotalElements(), equalTo(1L));
    }

    @Test
    void search() {
        List<Item> items = repository.search("test");
        assertThat(items.size(), equalTo(1));
    }

    @Test
    void searchByPage() {
        Pageable pageable = Utils.getPageRequest("0", "1");
        Page<Item> items = repository.searchByPage("test", pageable);
        assertThat(items.getTotalElements(), equalTo(1L));
    }

    @Test
    void findAllByRequestId() {
        List<Item> items = repository.findAllByRequestId(request.getId(),
                                    Sort.by(Sort.Direction.DESC,"id"));
        assertThat(items.size(), equalTo(1));
    }

}