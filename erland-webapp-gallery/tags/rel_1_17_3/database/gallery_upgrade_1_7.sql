ALTER TABLE galleries
  ADD showpicturedescription tinyint(1) NOT NULL default '0',
  ADD thumbnailheight int(11) default NULL;

ALTER TABLE useraccounts
  ADD skin varchar(100) default NULL;

CREATE TABLE skins (
  id varchar(100) NOT NULL default '',
  layout varchar(255) default NULL,
  layoutsingle varchar(255) default NULL,
  header varchar(255) default NULL,
  menu varchar(255) default NULL,
  search varchar(255) default NULL,
  viewpicture varchar(255) default NULL,
  viewpictures varchar(255) default NULL,
  PRIMARY KEY  (id)
) TYPE=MyISAM;
