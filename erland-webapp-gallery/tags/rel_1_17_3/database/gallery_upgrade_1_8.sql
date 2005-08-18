ALTER TABLE galleries
  ADD showpicturedescription tinyint(1) NOT NULL default '0',
  ADD thumbnailheight int(11) default NULL;

ALTER TABLE useraccounts
  ADD skin varchar(100) default NULL;
