ALTER TABLE issues
    ADD extref varchar(255) NULL AFTER type,
    ADD realname varchar(100) NULL AFTER username;
