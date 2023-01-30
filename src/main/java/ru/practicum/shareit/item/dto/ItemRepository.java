package ru.practicum.shareit.item.dto;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    @Query("select i from Item i where i.owner.id = ?1 order by i.id")
    List<Item> findAllByUser(Long userId);

    @Query("select i from Item i " +
            "where (upper(i.name) like upper(concat('%', ?1, '%'))  or upper(i.description) " +
            "like upper(concat('%', ?1, '%'))) and i.available = true")
    List<Item> search(String text);

    List<Item> findAllByRequestId(Long requestId, Sort sort);

}
