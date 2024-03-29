-- Insert 10 Teachers
INSERT INTO profile(password, email, picture, description, active, average_rating, deleted)
VALUES ('$2a$10$VlOOwBkaXcGcK2mkvOsD9O5WXBmABmioPInhi73QUpz.3UMUWwwaO', 'john01@example.com', '1234', 'I love studying!', true, null, false),
       ('$2a$10$VlOOwBkaXcGcK2mkvOsD9O5WXBmABmioPInhi73QUpz.3UMUWwwaO', 'jane02@example.com', null, 'Experienced teacher!', true, 4.7, false),
       ('$2a$10$VlOOwBkaXcGcK2mkvOsD9O5WXBmABmioPInhi73QUpz.3UMUWwwaO', 'mark03@example.com', null, 'I love teaching!', true, 4.6, false),
       ('$2a$10$VlOOwBkaXcGcK2mkvOsD9O5WXBmABmioPInhi73QUpz.3UMUWwwaO', 'susan04@example.com', null, 'Committed to excellence in teaching!', true, 4.8, false),
       ('$2a$10$VlOOwBkaXcGcK2mkvOsD9O5WXBmABmioPInhi73QUpz.3UMUWwwaO', 'peter05@example.com', null, 'Teaching is my passion!', true, 4.7, false),
       ('$2a$10$VlOOwBkaXcGcK2mkvOsD9O5WXBmABmioPInhi73QUpz.3UMUWwwaO', 'lucy06@example.com', null, 'Inspiring young minds!', true, 4.6, false),
       ('$2a$10$VlOOwBkaXcGcK2mkvOsD9O5WXBmABmioPInhi73QUpz.3UMUWwwaO', 'jack07@example.com', null, 'Making complex concepts simple!', true, 4.9, false),
       ('$2a$10$VlOOwBkaXcGcK2mkvOsD9O5WXBmABmioPInhi73QUpz.3UMUWwwaO', 'alice08@example.com', null, 'Believe in lifelong learning!', true, 4.8, false),
       ('$2a$10$VlOOwBkaXcGcK2mkvOsD9O5WXBmABmioPInhi73QUpz.3UMUWwwaO', 'josh09@example.com', null, 'Creating an inclusive learning environment!', true, 4.7, false),
       ('$2a$10$VlOOwBkaXcGcK2mkvOsD9O5WXBmABmioPInhi73QUpz.3UMUWwwaO', 'emma10@example.com', null, 'Adaptive teaching style!', true, 4.6, false);

INSERT INTO application_user(user_type, first_name, last_name, birthdate, fk_profile_id, role)
VALUES ('teacher', 'John', 'Doe', '2000-01-01', 1, 'ROLE_TEACHER'),
       ('teacher', 'Jane', 'Smith', '1980-01-01', 2, 'ROLE_TEACHER'),
       ('teacher', 'Mark', 'Johnson', '1981-02-01', 3, 'ROLE_TEACHER'),
       ('teacher', 'Susan', 'Williams', '1982-03-01', 4, 'ROLE_TEACHER'),
       ('teacher', 'Peter', 'Brown', '1983-04-01', 5, 'ROLE_TEACHER'),
       ('teacher', 'Lucy', 'Jones', '1984-05-01', 6, 'ROLE_TEACHER'),
       ('teacher', 'Jack', 'Miller', '1985-06-01', 7, 'ROLE_TEACHER'),
       ('teacher', 'Alice', 'Davis', '1986-07-01', 8, 'ROLE_TEACHER'),
       ('teacher', 'Josh', 'Garcia', '1987-08-01', 9, 'ROLE_TEACHER'),
       ('teacher', 'Emma', 'Rodriguez', '1988-09-01', 10, 'ROLE_TEACHER');

-- Insert Districts to teachers
INSERT INTO districts(teacher_id, districts) VALUES
(1, 'DISTRICT_1010');


-- Insert 10 Student Profiles
INSERT INTO profile(password, email, picture, description, active, average_rating, deleted)
VALUES ('$2a$10$VlOOwBkaXcGcK2mkvOsD9O5WXBmABmioPInhi73QUpz.3UMUWwwaO', 'student01@example.com', null, 'Eager to learn!', true, null, false),
       ('$2a$10$VlOOwBkaXcGcK2mkvOsD9O5WXBmABmioPInhi73QUpz.3UMUWwwaO', 'student02@example.com', null, 'Learning is fun!', true, null, false),
       ('$2a$10$VlOOwBkaXcGcK2mkvOsD9O5WXBmABmioPInhi73QUpz.3UMUWwwaO', 'student03@example.com', null, 'I love studying!', true, null, false),
       ('$2a$10$VlOOwBkaXcGcK2mkvOsD9O5WXBmABmioPInhi73QUpz.3UMUWwwaO', 'student04@example.com', null, 'Strive to improve!', true, null, false),
       ('$2a$10$VlOOwBkaXcGcK2mkvOsD9O5WXBmABmioPInhi73QUpz.3UMUWwwaO', 'student05@example.com', null, 'Hardworking student!', true, null, false),
       ('$2a$10$VlOOwBkaXcGcK2mkvOsD9O5WXBmABmioPInhi73QUpz.3UMUWwwaO', 'student06@example.com', null, 'Learning never stops!', true, null, false),
       ('$2a$10$VlOOwBkaXcGcK2mkvOsD9O5WXBmABmioPInhi73QUpz.3UMUWwwaO', 'student07@example.com', null, 'Ready to learn!', true, null, false),
       ('$2a$10$VlOOwBkaXcGcK2mkvOsD9O5WXBmABmioPInhi73QUpz.3UMUWwwaO', 'student08@example.com', null, 'Believe in self-improvement!', true, null, false),
       ('$2a$10$VlOOwBkaXcGcK2mkvOsD9O5WXBmABmioPInhi73QUpz.3UMUWwwaO', 'student09@example.com', null, 'Passionate about learning!', true, null, false),
       ('$2a$10$VlOOwBkaXcGcK2mkvOsD9O5WXBmABmioPInhi73QUpz.3UMUWwwaO', 'student10@example.com', null, 'Always ready for a challenge!', true, null, false);

-- Insert 10 Students
INSERT INTO application_user(user_type, first_name, last_name, birthdate, fk_profile_id, role)
VALUES ('student', 'Alex', 'Turner', '2005-01-01', 11, 'ROLE_STUDENT'),
       ('student', 'Ben', 'Harper', '2004-02-01', 12, 'ROLE_STUDENT'),
       ('student', 'Chris', 'Martin', '2003-03-01', 13, 'ROLE_STUDENT'),
       ('student', 'Dave', 'Grohl', '2002-04-01', 14, 'ROLE_STUDENT'),
       ('student', 'Eddie', 'Vedder', '2001-05-01', 15, 'ROLE_STUDENT'),
       ('student', 'Freddie', 'Mercury', '2000-06-01', 16, 'ROLE_STUDENT'),
       ('student', 'George', 'Harrison', '1999-07-01', 17, 'ROLE_STUDENT'),
       ('student', 'Iggy', 'Pop', '1998-08-01', 18, 'ROLE_STUDENT'),
       ('student', 'Jack', 'White', '1997-09-01', 19, 'ROLE_STUDENT'),
       ('student', 'Kurt', 'Cobain', '1996-10-01', 20, 'ROLE_STUDENT');


INSERT into profile(password, email, picture, description, active, average_rating, deleted)
    values ('$2a$10$VlOOwBkaXcGcK2mkvOsD9O5WXBmABmioPInhi73QUpz.3UMUWwwaO', 'admin01@example.com', null, 'Control everything!', true, null, false);

INSERT INTO application_user(user_type, first_name, last_name, birthdate, fk_profile_id, role)
    values ('admin', 'Administrator', 'Administrator', null, '21', 'ROLE_ADMIN');

-- Insert Districts to teachers
INSERT INTO districts(teacher_id, districts) VALUES
 (1, 'DISTRICT_1010'),
 (1, 'DISTRICT_1020'),
 (1, 'DISTRICT_1030'),
 (2, 'DISTRICT_1040'),
 (2, 'DISTRICT_1050'),
 (2, 'DISTRICT_1060'),
 (3, 'DISTRICT_1070'),
 (3, 'DISTRICT_1080'),
 (3, 'DISTRICT_1090'),
 (4, 'DISTRICT_1100'),
 (4, 'DISTRICT_1110'),
 (4, 'DISTRICT_1120'),
 (5, 'DISTRICT_1130'),
 (5, 'DISTRICT_1140'),
 (5, 'DISTRICT_1150'),
 (6, 'DISTRICT_1160'),
 (6, 'DISTRICT_1170'),
 (6, 'DISTRICT_1180'),
 (7, 'DISTRICT_1190'),
 (7, 'DISTRICT_1200'),
 (7, 'DISTRICT_1210'),
 (8, 'DISTRICT_1220'),
 (8, 'DISTRICT_1230'),
 (9, 'DISTRICT_1010'),
 (9, 'DISTRICT_1020'),
 (10, 'DISTRICT_1030'),
 (10, 'DISTRICT_1040');

-- CONVERSATIONS --

-- Insert 3 Conversations
INSERT INTO conversation DEFAULT VALUES;
INSERT INTO conversation DEFAULT VALUES;
INSERT INTO conversation DEFAULT VALUES;

-- Link Users to Conversations
INSERT INTO user_conversations(user_id, conversation_id)
VALUES (1, 1),
       (2, 1), -- Assuming users 1 and 2 are part of conversation 1
       (2, 2),
       (3, 2), -- Assuming users 2 and 3 are part of conversation 2
       (1, 3),
       (3, 3); -- Assuming users 1 and 3 are part of conversation 3


-- Insert Messages for Conversation 1
INSERT INTO message(content, sender_id, conversation_id, message_type)
VALUES ('Hello, how are you?', 1, 1, 'message'), -- Assuming user 1 is sending these messages
       ('Do you need help with your assignment?', 1, 1, 'message');

INSERT INTO conversation_messages (conversation_id, messages_id)
VALUES (1,1),
       (1,2);

-- Insert Messages for Conversation 2
INSERT INTO message(content, sender_id, conversation_id, message_type)
VALUES ('Hey, I got a question about the task.', 2, 2, 'message'), -- Assuming user 2 is sending these messages
       ('Could you explain the task more clearly?', 2, 2, 'message');

INSERT INTO conversation_messages (conversation_id, messages_id)
VALUES (2,3),
       (2,4);

-- Insert Messages for Conversation 3
INSERT INTO message(content, sender_id, conversation_id, message_type)
VALUES ('Hello, I am ready for the class.', 3, 3, 'message'), -- Assuming user 3 is sending these messages
       ('Can we start?', 3, 3, 'message');

INSERT INTO conversation_messages (conversation_id, messages_id)
VALUES (3,5),
       (3,6);

-- Insert Coaching
INSERT into coaching(subject, level, rate, active, fk_user_id)
values ('Mathematik', 'Unterstufe', 20.00, true, 1);

INSERT into coaching(subject, level, rate, active, fk_user_id)
values ('Französisch', 'Universität', 25.00, true, 1);

INSERT into coaching(subject, level, rate, active, fk_user_id)
values ('Rechnungswesen', 'Universität', 30.00, true, 1);

INSERT into coaching(subject, level, rate, active, fk_user_id)
values ('Rechnungswesen', 'Universität', 10.00, true, 2);

INSERT into coaching(subject, level, rate, active, fk_user_id)
values ('Rechnungswesen', 'Oberstufe', 15.00, true, 2);

INSERT into coaching(subject, level, rate, active, fk_user_id)
values ('Rechnungswesen', 'Unterstufe', 30.00, true, 3);



-- Insert some feedbacks ---
INSERT INTO feedback(fk_teacher_id, fk_student_id, content, title, rating, date)
values (1, 11, 'Super duper teacher', 'Nice', 4.3, '2023-07-07');

INSERT INTO feedback(fk_teacher_id, fk_student_id, content, title, rating, date)
values (1, 12, 'Best teacher', 'Yeah', 4.3, '2023-07-06');

INSERT INTO feedback(fk_teacher_id, fk_student_id, content, title, rating, date)
values (1, 13, 'Shit teacher', 'Shit', 1.0, '2023-07-07');


INSERT INTO message(start_coaching, end_coaching, status, fk_coaching_id, content, sender_id, fk_student_id, conversation_id, message_type)
VALUES ('2023-07-16T12:00','2023-07-16T13:00', 'CONFIRMED', 1, 'First Appointment', 11, 11, 1, 'appointment'); -- Assuming user 1 is sending these messages

INSERT INTO conversation_messages (conversation_id, messages_id)
VALUES (1,7);

INSERT INTO message(start_coaching, end_coaching, status, fk_coaching_id, content, sender_id, fk_student_id, conversation_id, message_type)
VALUES ('2023-07-16T14:00','2023-07-16T15:00', 'CONFIRMED', 1, 'Second Appointment', 12, 12, 1, 'appointment'); -- Assuming user 1 is sending these messages

INSERT INTO conversation_messages (conversation_id, messages_id)
VALUES (1,8);

INSERT INTO message(start_coaching, end_coaching, status, fk_coaching_id, content, sender_id, fk_student_id, conversation_id, message_type)
VALUES ('2023-07-16T11:00','2023-07-16T12:13', 'CONFIRMED', 1, 'Third Appointment', 13, 13, 1, 'appointment'); -- Assuming user 1 is sending these messages

INSERT INTO conversation_messages (conversation_id, messages_id)
VALUES (1,9);

INSERT INTO message(start_coaching, end_coaching, status, fk_coaching_id, content, sender_id, fk_student_id, conversation_id, message_type)
VALUES ('2023-07-17T11:00','2023-07-17T12:13', 'CONFIRMED', 1, 'Fourth Appointment', 13, 13, 1, 'appointment'); -- Assuming user 1 is sending these messages

INSERT INTO conversation_messages (conversation_id, messages_id)
VALUES (1,10);
