ALTER TABLE useraccounts
  ADD `includetips` tinyint(4) NOT NULL default '0',
  ADD `mintipsreview` int(11) NOT NULL default '8';
