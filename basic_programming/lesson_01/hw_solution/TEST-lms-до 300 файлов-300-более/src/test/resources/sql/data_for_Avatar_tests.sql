insert into users(id, email, nick_name, password, user_status)
values (1,
        'otto.shtirliz@competa.test',
        'Otto Shtirliz',
        '$2a$10$ocVr3Gmtui9n9JXKvvEoJ.RU8zCgO5cJTOBe6zSPmKwWa/6kubmKy',
        'CONFIRMED'); /*   Password:  oldPassword007!   */

INSERT INTO image_info (image_name, url)
values ('oldAvatar.jpg', './uploads/anyHash.jpg');

INSERT INTO user_profile (id, user_id, is_ready_to_move, is_public, avatar_id)
values (1, 1, false, false, 1);

update users u
set u.user_profile_id = 1
where u.id = 1;
