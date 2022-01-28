SHOW WARNINGS;

CREATE TABLE users (
	is_valid BOOLEAN NOT NULL,
	name TEXT,
	pass TEXT
);

CREATE TABLE users_results (
	balance_num DECIMAL(10,4),
	balance_raw TEXT,
	datetime_inserted DATETIME NOT NULL DEFAULT NOW(),
	datetime_retrieved DATETIME,
	msisdn TEXT,
	prepaid_plan TEXT,
	username TEXT
);

INSERT INTO users VALUES
(true,"99368021","Voda1234");