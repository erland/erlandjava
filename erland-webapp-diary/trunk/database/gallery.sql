-- MySQL dump 9.08
--
-- Host: localhost    Database: gallery
---------------------------------------------------------
-- Server version	4.0.13-max-nt

--
-- Table structure for table 'categories'
--

CREATE TABLE categories (
  gallery int(11) NOT NULL default '0',
  id int(11) NOT NULL auto_increment,
  name varchar(100) NOT NULL default '',
  official tinyint(1) NOT NULL default '0',
  officialvisible tinyint(1) NOT NULL default '0',
  officialalways tinyint(1) NOT NULL default '0',
  parent int(11) NOT NULL default '0',
  PRIMARY KEY  (gallery,id)
) TYPE=MyISAM;

--
-- Table structure for table 'categorymembers'
--

CREATE TABLE categorymembers (
  gallery int(11) NOT NULL default '0',
  category int(11) NOT NULL default '0',
  member int(11) NOT NULL default '0',
  PRIMARY KEY  (gallery,category,member)
) TYPE=MyISAM;

--
-- Table structure for table 'descriptiontags'
--

CREATE TABLE descriptiontags (
  type varchar(100) NOT NULL default '',
  tag varchar(100) NOT NULL default '',
  description varchar(100) NOT NULL default '',
  PRIMARY KEY  (type,tag)
) TYPE=MyISAM;

--
-- Table structure for table 'galleries'
--

CREATE TABLE galleries (
  id int(11) NOT NULL auto_increment,
  username varchar(100) NOT NULL default '',
  title varchar(100) NOT NULL default '',
  description longtext NOT NULL,
  topcategory int(11) NOT NULL default '0',
  referencedgallery int(11) NOT NULL default '0',
  official tinyint(1) NOT NULL default '0',
  PRIMARY KEY  (id)
) TYPE=MyISAM;

--
-- Table structure for table 'gallerycategories'
--

CREATE TABLE gallerycategories (
  gallery int(11) NOT NULL default '0',
  category int(11) NOT NULL default '0',
  PRIMARY KEY  (gallery,category)
) TYPE=MyISAM;

--
-- Table structure for table 'picturecategories'
--

CREATE TABLE picturecategories (
  gallery int(11) NOT NULL default '0',
  category int(11) NOT NULL default '0',
  picture int(11) NOT NULL default '0',
  PRIMARY KEY  (gallery,category,picture)
) TYPE=MyISAM;

--
-- Table structure for table 'pictures'
--

CREATE TABLE pictures (
  id int(11) NOT NULL auto_increment,
  gallery int(11) NOT NULL default '0',
  title varchar(255) NOT NULL default '',
  date date NOT NULL default '0000-00-00',
  description longtext NOT NULL,
  image varchar(255) NOT NULL default '',
  official tinyint(1) NOT NULL default '0',
  link varchar(255) NOT NULL default '',
  PRIMARY KEY  (id,gallery)
) TYPE=MyISAM;

--
-- Table structure for table 'picturestorages'
--

CREATE TABLE picturestorages (
  id int(11) NOT NULL auto_increment,
  username varchar(100) NOT NULL default '',
  name varchar(255) NOT NULL default '',
  path varchar(255) NOT NULL default '',
  PRIMARY KEY  (id)
) TYPE=MyISAM;

--
-- Table structure for table 'resources'
--

CREATE TABLE resources (
  id varchar(100) NOT NULL default '',
  value varchar(255) NOT NULL default '',
  PRIMARY KEY  (id)
) TYPE=MyISAM;

--
-- Table structure for table 'useraccounts'
--

CREATE TABLE useraccounts (
  username varchar(100) NOT NULL default '',
  welcometext longtext NOT NULL,
  description longtext NOT NULL,
  logo varchar(100) NOT NULL default '',
  defaultgallery int(11) NOT NULL default '0',
  copyright varchar(100) NOT NULL default '',
  official tinyint(1) NOT NULL default '0',
  PRIMARY KEY  (username)
) TYPE=MyISAM;

--
-- Table structure for table 'userguests'
--

CREATE TABLE userguests (
  username varchar(100) NOT NULL default '',
  guestuser varchar(100) NOT NULL default '',
  PRIMARY KEY  (username,guestuser)
) TYPE=MyISAM;

