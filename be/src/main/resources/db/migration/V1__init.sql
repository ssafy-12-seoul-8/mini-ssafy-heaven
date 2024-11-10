-- MEMBER
CREATE TABLE IF NOT EXISTS members 
(
    id         INT 			AUTO_INCREMENT,
    username   VARCHAR(30) 	NOT NULL,
    password   VARCHAR(100) NOT NULL,
    nickname   VARCHAR(30)	NOT NULL,
    role	   VARCHAR(10)	NOT NULL,
    score	   INT			NOT NULL DEFAULT 0,
    created_at TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    
    CONSTRAINT pk_member_id PRIMARY KEY (id),
    CONSTRAINT uk_member_username UNIQUE (username),
    CONSTRAINT uk_member_nickname UNIQUE (nickname)
);

-- ROOM
CREATE TABLE IF NOT EXISTS rooms
(
	id         INT 			AUTO_INCREMENT,
	title	   VARCHAR(30)  NOT NULL,
	status	   VARCHAR(30)  NOT NULL DEFAULT 'WAITING',
	created_at TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    
    CONSTRAINT pk_room_id PRIMARY KEY (id)
);

-- GAME
CREATE TABLE IF NOT EXISTS games
(
	id          INT 		  AUTO_INCREMENT,
    title	    VARCHAR(10)	  NOT NULL,
    description VARCHAR(1024) NOT NULL,
    max_round   INT           NOT NULL,
    created_at  TIMESTAMP     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMP     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    
    CONSTRAINT pk_game_id PRIMARY KEY (id)
);

-- ROOM GAME
CREATE TABLE IF NOT EXISTS room_games
(
	id         INT 			AUTO_INCREMENT,
	room_id	   INT			NOT NULL,
	game_id    INT			NOT NULL,
	round	   INT			NOT NULL,
	created_at TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    
    CONSTRAINT pk_room_game_id PRIMARY KEY (id),
    CONSTRAINT fk_room_game_room_id FOREIGN KEY (room_id) REFERENCES rooms (id),
    CONSTRAINT fk_room_game_game_id FOREIGN KEY (game_id) REFERENCES games (id)
);

-- ROOM PLAYER
CREATE TABLE IF NOT EXISTS room_players
(
	id         INT 			AUTO_INCREMENT,
	member_id  INT			NOT NULL,
	room_id	   INT			NOT NULL,
	score	   INT			NOT NULL DEFAULT 0,
    role	   VARCHAR(10)	NOT NULL DEFAULT 'PLAYER',
    created_at TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    
    CONSTRAINT pk_room_player_id PRIMARY KEY (id),
    CONSTRAINT fk_room_player_member_id FOREIGN KEY (member_id) REFERENCES members (id),
    CONSTRAINT fk_room_player_room_id FOREIGN KEY (room_id) REFERENCES rooms (id)
);

-- ROOM CHAT
CREATE TABLE IF NOT EXISTS room_chats
(
	id         	   INT 			AUTO_INCREMENT,
	room_player_id INT			NOT NULL,
	message	   	   VARCHAR(100)	NOT NULL,
	created_at 	   TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at 	   TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    
    CONSTRAINT pk_room_chat_id PRIMARY KEY (id),
    CONSTRAINT fk_room_chat_room_player_id FOREIGN KEY (room_player_id) REFERENCES room_players (id)
);