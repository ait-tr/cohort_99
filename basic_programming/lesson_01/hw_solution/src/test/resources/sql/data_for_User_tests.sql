insert into roles(id, name)
values (1, 'ROLE_ADMIN'),
       (2, 'ROLE_USER');

insert into users(id, email, nick_name, password, user_status)
values (1,
        'vasja.pupkin@competa.test',
        'Vasja-Pupkin',
        '$2a$10$UEV685sg8aB9Msrnp1XPYOaKj6HHrN7.qKG6xFj4RI8gchGG4Dh.e',
        'CONFIRMED'),
       (2,
        'mark.shulz@competa.test',
        'Mark-Schulz',
        '$2a$10$9/k3TCXRn/dZaaPiPKqDiejcKYa/74hHJ0DnEAGhzcg9FJXlR2gyG',
        'CONFIRMED'),
       (3,
        'otto.shtirliz@competa.test',
        'Otto-Stierlitz',
        '$2a$10$ocVr3Gmtui9n9JXKvvEoJ.RU8zCgO5cJTOBe6zSPmKwWa/6kubmKy',
        'CONFIRMED'); /*   Password:  oldPassword007!   */

INSERT INTO user_profile (user_id, is_ready_to_move, is_public, first_name, last_name)

values (1, false, false, 'Vasja', 'Pupkin'),

       (2, false, false, 'Mark', 'Schulz'),

       (3, false, false, 'Otto', 'Stierlitz');

update users u
set u.user_profile_id = 1
where u.id = 1;
update users u
set u.user_profile_id = 2
where u.id = 2;
update users u
set u.user_profile_id = 3
where u.id = 3;

insert into users_roles(users_id, roles_id)
values (1, 1),
       (2, 2),
       (3, 2);
