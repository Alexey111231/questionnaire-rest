delete
from question;
delete
from poll;

insert into poll(id, name, begin_date, end_date, active)
values (1, 'Question', '1971-07-13', '1991-07-13', true),
       (2, 'What', '1971-07-13', '1991-07-13', true),
       (3, 'Test', '1971-07-13', '1991-07-13', true);

insert into question(id, question_order, text, poll_id)
values (1, 2, 'testText', 1),
       (2, 1, 'testText', 1),
       (3, 3, 'testText', 1),
       (4, 1, 'testText', 2),
       (5, 2, 'testText', 2),
       (6, 1, 'testText', 3);