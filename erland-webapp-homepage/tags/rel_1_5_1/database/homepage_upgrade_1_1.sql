ALTER TABLE useraccounts
    ADD title longtext NOT NULL AFTER username,
    ADD title_en longtext NOT NULL AFTER title;

