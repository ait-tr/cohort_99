insert into users(email, nick_name, password, user_status)
values ('vasja.pupkin22@competa.test',
        'Vasja-Pupkin-22',
        '$2a$10$UEV685sg8aB9Msrnp1XPYOaKj6HHrN7.qKG6xFj4RI8gchGG4Dh.e',
        'NOT_CONFIRMED'),
       ('vasja.pupkin11@competa.test',
        'Vasiliy-Pupkins-11',
        'Qwerty007!',
        'NOT_CONFIRMED');

insert into confirmation_code(code, expire_time, is_used, time_of_use_code, user_id)
values ('12345', '2024-09-1 10:43:34.457155', false, null, '1'),
       ('6789', '2024-09-1 10:43:34.457155', true, null, '2');
