CREATE TABLE sourceappendixentries (
  id int(11) NOT NULL auto_increment,
  name varchar(100) NOT NULL default '',
  description varchar(100) NOT NULL default '',
  PRIMARY KEY  (id)
) TYPE=MyISAM;

ALTER TABLE containers
  ADD linksource varchar(255) default NULL AFTER link;

ALTER TABLE inventoryentries
  ADD linksource varchar(255) default NULL AFTER link;

