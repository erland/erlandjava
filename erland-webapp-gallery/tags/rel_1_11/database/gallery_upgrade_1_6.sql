ALTER TABLE galleries
  ADD stylesheet varchar(255) default NULL,
  ADD thumbnailwidth int(11) default NULL,
  ADD sortorder varchar(20) NOT NULL default 'bydatedesc',
  ADD noofcols int(11) NOT NULL default '3',
  ADD noofrows int(11) NOT NULL default '3',
  ADD allowsearch tinyint(1) NOT NULL default '1',
  ADD cutlongpicturetitles tinyint(1) NOT NULL default '1',
  ADD useshortpicturenames tinyint(1) NOT NULL default '0',
  ADD showpicturetitle tinyint(1) NOT NULL default '1',
  ADD showresolutionlinks tinyint(1) NOT NULL default '1',
  ADD showpicturedescription tinyint(1) NOT NULL default '0',
  ADD thumbnailheight int(11) default NULL;

ALTER TABLE pictures
  ADD orderno int(11) NOT NULL default '0';

ALTER TABLE useraccounts
  ADD stylesheet varchar(255) default NULL,
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
