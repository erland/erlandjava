ALTER TABLE useraccounts
  ADD title varchar(255) NOT NULL default '' AFTER username,
  ADD title_en varchar(255) NOT NULL default '' AFTER title;

ALTER TABLE galleries
  ADD nationalnamepattern varchar(255) NOT NULL default '',
  ADD englishnamepattern varchar(255) NOT NULL default '';
