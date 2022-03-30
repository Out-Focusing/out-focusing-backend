INSERT INTO user_profile (user_id, name, contact, readme, profile_image) values ('test1234a', 'Test', '', '', '');
INSERT INTO auth (user_id, password) values ('test1234a', '{bcrypt}$2a$10$pnRzgBi2Mjcd9m9en/dvBOFaNPhhkfbReMnxhTdOYG5eYT8.IPbL.');

INSERT INTO user_profile (user_id, name, contact, readme, profile_image) values ('test1234b', 'Test', '', '', '');
INSERT INTO auth (user_id, password) values ('test1234b', '{bcrypt}$2a$10$pnRzgBi2Mjcd9m9en/dvBOFaNPhhkfbReMnxhTdOYG5eYT8.IPbL.');

INSERT INTO user_profile (user_id, name, contact, readme, profile_image) values ('test1234c', 'Test', '', '', '');
INSERT INTO auth (user_id, password) values ('test1234c', '{bcrypt}$2a$10$pnRzgBi2Mjcd9m9en/dvBOFaNPhhkfbReMnxhTdOYG5eYT8.IPbL.');

INSERT INTO user_profile (user_id, name, contact, readme, profile_image) values ('test1234d', 'Test', '', '', '');
INSERT INTO auth (user_id, password) values ('test1234d', '{bcrypt}$2a$10$pnRzgBi2Mjcd9m9en/dvBOFaNPhhkfbReMnxhTdOYG5eYT8.IPbL.');

INSERT INTO user_profile (user_id, name, contact, readme, profile_image) values ('test1234e', 'Test', '', '', '');
INSERT INTO auth (user_id, password) values ('test1234e', '{bcrypt}$2a$10$pnRzgBi2Mjcd9m9en/dvBOFaNPhhkfbReMnxhTdOYG5eYT8.IPbL.');


INSERT INTO album (album_id, writer_user_id, title, content, created_at, secret, deleted, thumbnail) values (1, 'test1234a', 'Title #1', 'content1', localtimestamp, 0, false, '');
INSERT INTO album (album_id, writer_user_id, title, content, created_at, secret, deleted, thumbnail) values (2, 'test1234a', 'Title #2', 'content2', localtimestamp, 0, false, '');
INSERT INTO album (album_id, writer_user_id, title, content, created_at, secret, deleted, thumbnail) values (3, 'test1234a', 'Title #3', 'content3', localtimestamp, 0, false, '');
INSERT INTO album (album_id, writer_user_id, title, content, created_at, secret, deleted, thumbnail) values (4, 'test1234a', 'Title #4', 'content4', localtimestamp, 0, false, '');
INSERT INTO album (album_id, writer_user_id, title, content, created_at, secret, deleted, thumbnail) values (5, 'test1234b', 'Title #5', 'content5', localtimestamp, 0, false, '');
INSERT INTO album (album_id, writer_user_id, title, content, created_at, secret, deleted, thumbnail) values (6, 'test1234b', 'Title #6', 'content6', localtimestamp, 0, false, '');
INSERT INTO album (album_id, writer_user_id, title, content, created_at, secret, deleted, thumbnail) values (7, 'test1234c', 'Title #7', 'content7', localtimestamp, 0, false, '');
INSERT INTO album (album_id, writer_user_id, title, content, created_at, secret, deleted, thumbnail) values (8, 'test1234a', 'Title #8', 'content8', localtimestamp, 0, false, '');
INSERT INTO album (album_id, writer_user_id, title, content, created_at, secret, deleted, thumbnail) values (9, 'test1234b', 'Title #9', 'content9', localtimestamp, 0, false, '');
INSERT INTO album (album_id, writer_user_id, title, content, created_at, secret, deleted, thumbnail) values (10, 'test1234d', 'Title #10', 'content10', localtimestamp, 0, false, '');
INSERT INTO album (album_id, writer_user_id, title, content, created_at, secret, deleted, thumbnail) values (11, 'test1234b', 'Title #11', 'content11', localtimestamp, 0, false, '');
INSERT INTO album (album_id, writer_user_id, title, content, created_at, secret, deleted, thumbnail) values (12, 'test1234a', 'Title #12', 'content12', localtimestamp, 0, false, '');

INSERT INTO album_bookmark (album_id, user_id) values (1, 'test1234a');
INSERT INTO album_bookmark (album_id, user_id) values (2, 'test1234a');
INSERT INTO album_bookmark (album_id, user_id) values (3, 'test1234a');
INSERT INTO album_bookmark (album_id, user_id) values (4, 'test1234a');
INSERT INTO album_bookmark (album_id, user_id) values (8, 'test1234a');
INSERT INTO album_bookmark (album_id, user_id) values (9, 'test1234a');
INSERT INTO album_bookmark (album_id, user_id) values (11, 'test1234a');
INSERT INTO album_bookmark (album_id, user_id) values (1, 'test1234b');
INSERT INTO album_bookmark (album_id, user_id) values (2, 'test1234b');
INSERT INTO album_bookmark (album_id, user_id) values (3, 'test1234b');
INSERT INTO album_bookmark (album_id, user_id) values (4, 'test1234b');
INSERT INTO album_bookmark (album_id, user_id) values (5, 'test1234b');
INSERT INTO album_bookmark (album_id, user_id) values (1, 'test1234c');
INSERT INTO album_bookmark (album_id, user_id) values (3, 'test1234c');
INSERT INTO album_bookmark (album_id, user_id) values (5, 'test1234c');
INSERT INTO album_bookmark (album_id, user_id) values (6, 'test1234c');
INSERT INTO album_bookmark (album_id, user_id) values (7, 'test1234c');
INSERT INTO album_bookmark (album_id, user_id) values (9, 'test1234c');
INSERT INTO album_bookmark (album_id, user_id) values (10, 'test1234c');
INSERT INTO album_bookmark (album_id, user_id) values (11, 'test1234c');
INSERT INTO album_bookmark (album_id, user_id) values (12, 'test1234c');
INSERT INTO album_bookmark (album_id, user_id) values (3, 'test1234d');
INSERT INTO album_bookmark (album_id, user_id) values (4, 'test1234d');
INSERT INTO album_bookmark (album_id, user_id) values (5, 'test1234d');
INSERT INTO album_bookmark (album_id, user_id) values (7, 'test1234d');
INSERT INTO album_bookmark (album_id, user_id) values (8, 'test1234d');
INSERT INTO album_bookmark (album_id, user_id) values (9, 'test1234d');
INSERT INTO album_bookmark (album_id, user_id) values (10, 'test1234d');
INSERT INTO album_bookmark (album_id, user_id) values (11, 'test1234d');
INSERT INTO album_bookmark (album_id, user_id) values (4, 'test1234e');
INSERT INTO album_bookmark (album_id, user_id) values (7, 'test1234e');
INSERT INTO album_bookmark (album_id, user_id) values (8, 'test1234e');
INSERT INTO album_bookmark (album_id, user_id) values (9, 'test1234e');
INSERT INTO album_bookmark (album_id, user_id) values (11, 'test1234e');
INSERT INTO album_bookmark (album_id, user_id) values (12, 'test1234e');
