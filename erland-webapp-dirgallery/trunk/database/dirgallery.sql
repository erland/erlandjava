-- MySQL dump 9.08
--
-- Host: localhost    Database: dirgallery
---------------------------------------------------------
-- Server version	4.0.13-max-nt

--
-- Table structure for table 'galleries'
--

CREATE TABLE galleries (
  id int(11) NOT NULL auto_increment,
  username varchar(100) NOT NULL default '',
  title varchar(100) NOT NULL default '',
  menuname varchar(100) NOT NULL default '',
  description longtext NOT NULL,
  directory varchar(255) NOT NULL default '',
  includesubdirs tinyint(1) NOT NULL default '0',
  originaldownloadable tinyint(1) NOT NULL default '0',
  noofthumbnail int(11) NOT NULL default '0',
  thumbnailwidth int(11) NOT NULL default '0',
  showlogo tinyint(1) NOT NULL default '0',
  logo varchar(255) NOT NULL default '',
  logolink varchar(255) NOT NULL default '',
  showdownloadlinks tinyint(1) NOT NULL default '0',
  showpicturenames tinyint(1) NOT NULL default '0',
  maxnoofrows int(11) NOT NULL default '0',
  uselogoseparator tinyint(1) NOT NULL default '0',
  logoseparatorcolor varchar(20) NOT NULL default '',
  logoseparatorheight int(11) NOT NULL default '0',
  useshortpicturenames tinyint(1) NOT NULL default '0',
  ordernumber int(11) NOT NULL default '0',
  usethumbnailcache tinyint(1) NOT NULL default '0',
  thumbnailcompression double NOT NULL default '0',
  typeoffiles int(11) NOT NULL default '0',
  noofmoviecolumns int(11) NOT NULL default '0',
  noofmovierows int(11) NOT NULL default '0',
  maxpicturenamelength int(11) NOT NULL default '0',
  showpicturenameintooltip tinyint(1) NOT NULL default '0',
  usetooltip tinyint(1) NOT NULL default '0',
  showcommentbelowpicture tinyint(1) NOT NULL default '0',
  showfilesizebelowpicture tinyint(1) NOT NULL default '0',
  official tinyint(1) NOT NULL default '0',
  PRIMARY KEY  (id)
) TYPE=MyISAM;

--
-- Table structure for table 'galleryfriends'
--

CREATE TABLE galleryfriends (
  gallery int(11) NOT NULL default '0',
  friendgallery int(11) NOT NULL default '0'
) TYPE=MyISAM;

--
-- Table structure for table 'picturecomments'
--

CREATE TABLE picturecomments (
  id varchar(255) NOT NULL default '',
  comment longtext NOT NULL,
  PRIMARY KEY  (id)
) TYPE=MyISAM;

--
-- Table structure for table 'useraccounts'
--

CREATE TABLE useraccounts (
  username varchar(100) NOT NULL default '',
  welcometext longtext NOT NULL,
  description longtext NOT NULL,
  logo varchar(255) NOT NULL default '',
  defaultgallery int(11) NOT NULL default '0',
  copyright varchar(100) NOT NULL default '',
  official tinyint(1) NOT NULL default '0',
  PRIMARY KEY  (username)
) TYPE=MyISAM;

