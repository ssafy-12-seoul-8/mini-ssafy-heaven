-- CLEAR
SET foreign_key_checks = 0;

TRUNCATE members;
TRUNCATE rooms;
TRUNCATE games;
TRUNCATE room_games;
TRUNCATE room_chats;
TRUNCATE room_players;

SET foreign_key_checks = 1;

-- MEMBERS
INSERT INTO members (id, username, password, nickname, role, score, created_at, updated_at)
VALUES (1, 'injaesong', 1234, '인재', 'ADMIN', 100, '2024-11-13T16:13:48', '2024-11-13T16:13:48');

-- GAMES
INSERT INTO games (id, title, description, max_round, created_at, updated_at)
VALUES (1, '숫자야구', '더미입니다.', 5, '2024-11-13T16:13:48', '2024-11-13T16:13:48');