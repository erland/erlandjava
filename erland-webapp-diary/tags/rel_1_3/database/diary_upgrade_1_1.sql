CREATE TABLE containers (
  id int(11) NOT NULL auto_increment,
  username varchar(100) NOT NULL default '',
  name varchar(100) NOT NULL default '',
  model varchar(100) NOT NULL default '',
  volume int(11) NOT NULL default '0',
  width int(11) NOT NULL default '0',
  height int(11) NOT NULL default '0',
  length int(11) NOT NULL default '0',
  image varchar(255) default NULL,
  largeimage varchar(255) default NULL,
  link varchar(255) default NULL,
  gallery int(11) default NULL,
  description longtext,
  PRIMARY KEY  (id)
) TYPE=MyISAM;

CREATE TABLE inventoryentrysexes (
  id int(11) NOT NULL default '0',
  description char(100) NOT NULL default '',
  PRIMARY KEY  (id)
) TYPE=MyISAM;

ALTER TABLE inventoryentries
  ADD sex int(11) NOT NULL default '0' AFTER type;

ALTER TABLE inventoryentryhistory
  ADD container int(11) NOT NULL default '0';

INSERT INTO inventoryentryeventtypes VALUES (9,'Flyttad');

INSERT INTO inventoryentrysexes VALUES (1,'Hane');
INSERT INTO inventoryentrysexes VALUES (2,'Hona');
INSERT INTO inventoryentrysexes VALUES (0,'');
