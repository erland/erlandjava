CREATE TABLE species (
  id int(11) NOT NULL auto_increment,
  username varchar(100) NOT NULL default '',
  name varchar(100) NOT NULL default '',
  latinname varchar(100) NOT NULL default '',
  type int(11) NOT NULL default '0',
  description longtext NOT NULL,
  image varchar(255) NOT NULL default '',
  largeimage varchar(255) NOT NULL default '',
  link varchar(255) NOT NULL default '',
  gallery int(11) NOT NULL default '0',
  PRIMARY KEY  (id)
) TYPE=MyISAM;

CREATE TABLE speciestypes (
  id int(11) NOT NULL default '0',
  description varchar(100) NOT NULL default '',
  PRIMARY KEY  (id)
) TYPE=MyISAM;

ALTER TABLE inventoryentries
  ADD species int(11) NOT NULL default '0';

INSERT INTO speciestypes VALUES (1,'Fisk');
