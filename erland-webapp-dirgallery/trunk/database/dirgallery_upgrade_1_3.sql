ALTER TABLE galleries
  ADD copyrighttext varchar(100) NOT NULL default '',
  ADD copyrightposition int(11) NOT NULL default '1',
  ADD usecopyright tinyint(1) NOT NULL default '1',
  ADD copyrighttransparency double NOT NULL default '1',
  ADD uselargecache tinyint(1) NOT NULL default '0';
