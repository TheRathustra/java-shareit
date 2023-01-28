package ru.practicum.shareit.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.item.comment.Comment;
import ru.practicum.shareit.item.comment.CommentRepository;
import ru.practicum.shareit.item.dto.ItemRepository;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class ItemStorageImp implements ItemStorage {

    final private ItemRepository repository;
    final private CommentRepository commentRepository;

    @Autowired
    public ItemStorageImp(ItemRepository repository, CommentRepository commentRepository) {
        this.repository = repository;
        this.commentRepository = commentRepository;
    }

    @Override
    public Item add(User user, Item item) {

        item.setOwner(user);
        repository.saveAndFlush(item);

        return item;

    }

    @Override
    public Item update(User user, long id, Item item) {

        Item itemDB = repository.getById(id);
        if (!itemDB.getOwner().getId().equals(user.getId())) {
            throw new IllegalArgumentException();
        }

        if (itemDB == null)
            throw new IllegalArgumentException();

        if (item.getName() != null)
            itemDB.setName(item.getName());

        if (item.getDescription() != null)
            itemDB.setDescription(item.getDescription());

        if (item.getAvailable() != null)
            itemDB.setAvailable(item.getAvailable());

        repository.saveAndFlush(itemDB);
        return itemDB;
    }

    @Override
    public void delete(long id) {
        Optional<Item> item = repository.findById(id);

        if (item.isEmpty())
            throw new IllegalArgumentException();

        repository.delete(item.get());
    }

    @Override
    public Item getItemById(long id) {
        Optional<Item> item = repository.findById(id);

        if (item.isEmpty())
            throw new IllegalArgumentException();

        return item.get();
    }

    @Override
    public List<Item> getItems(Long idOwner) {
        return repository.findAllByUser(idOwner);
    }

    @Override
    public List<Item> findItemsByText(String text) {
        if (text.isEmpty()) {
            return new ArrayList<>();
        }
        return repository.search(text);
    }

    @Override
    public Comment addComment(Comment comment) {
        commentRepository.saveAndFlush(comment);
        return comment;
    }

    @Override
    public List<Comment> getCommentsByItemId(Long itemId) {
        return commentRepository.findAllByItem_Id(itemId);
    }
}
