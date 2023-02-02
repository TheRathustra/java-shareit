insert into users (id, email, name) values (1, 'test', 'test');
insert into requests (id, description, requestor_id, created) values (1, 'test', 1, now());
insert into items (id, name, description, is_available, booker_id, request_id) values (1, 'test', 'test', true, 1, 1);
insert into comments(id, text, item_id, author_id, created) values (1, 'test', 1, 1, now());