ALTER TABLE room_games CHANGE round current_round INT;
ALTER TABLE room_games ADD round_limit INT;
ALTER TABLE rooms ADD capacity INT DEFAULT 5;