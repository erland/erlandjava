ALTER TABLE useraccounts
  ADD `jabberid` varchar(100) default NULL,
  ADD `jabbernotification` tinyint(4) NOT NULL default '0';
