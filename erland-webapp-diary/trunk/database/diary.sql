-- MySQL dump 9.09
--
-- Host: localhost    Database: diary
---------------------------------------------------------
-- Server version	4.0.15-max-debug

--
-- Table structure for table `appendixentries`
--

CREATE TABLE appendixentries (
  id int(11) NOT NULL auto_increment,
  name varchar(100) NOT NULL default '',
  description varchar(100) NOT NULL default '',
  PRIMARY KEY  (id)
) TYPE=MyISAM;

--
-- Table structure for table `diaries`
--

CREATE TABLE diaries (
  id int(11) NOT NULL auto_increment,
  username varchar(100) NOT NULL default '',
  title varchar(100) NOT NULL default '',
  description longtext NOT NULL,
  official tinyint(1) NOT NULL default '0',
  PRIMARY KEY  (id)
) TYPE=MyISAM;

--
-- Table structure for table `diaryentries`
--

CREATE TABLE diaryentries (
  diary int(11) NOT NULL default '0',
  date date NOT NULL default '0000-00-00',
  title varchar(100) NOT NULL default '',
  description longtext NOT NULL,
  PRIMARY KEY  (date,diary)
) TYPE=MyISAM;

--
-- Table structure for table `galleries`
--

CREATE TABLE galleries (
  id int(11) NOT NULL auto_increment,
  username varchar(100) NOT NULL default '',
  title varchar(100) NOT NULL default '',
  description longtext NOT NULL,
  gallery int(11) NOT NULL default '0',
  official tinyint(1) NOT NULL default '0',
  PRIMARY KEY  (id)
) TYPE=MyISAM;

--
-- Table structure for table `galleryentries`
--

CREATE TABLE galleryentries (
  id int(11) NOT NULL auto_increment,
  gallery int(11) NOT NULL default '0',
  title varchar(100) NOT NULL default '',
  description longtext NOT NULL,
  image varchar(100) NOT NULL default '',
  link varchar(100) NOT NULL default '',
  PRIMARY KEY  (gallery,id)
) TYPE=MyISAM;

--
-- Table structure for table `inventoryentries`
--

CREATE TABLE inventoryentries (
  id int(11) NOT NULL auto_increment,
  username varchar(100) NOT NULL default '',
  type int(11) NOT NULL default '0',
  description longtext NOT NULL,
  image varchar(255) NOT NULL default '',
  largeimage varchar(255) NOT NULL default '',
  link varchar(255) NOT NULL default '',
  gallery int(11) NOT NULL default '0',
  name varchar(100) NOT NULL default '',
  PRIMARY KEY  (id)
) TYPE=MyISAM;

--
-- Table structure for table `inventoryentryeventtypes`
--

CREATE TABLE inventoryentryeventtypes (
  id int(11) NOT NULL default '0',
  description varchar(100) NOT NULL default '',
  PRIMARY KEY  (id)
) TYPE=MyISAM;

--
-- Table structure for table `inventoryentryhistory`
--

CREATE TABLE inventoryentryhistory (
  id int(11) NOT NULL default '0',
  eventid int(11) NOT NULL auto_increment,
  date date NOT NULL default '0000-00-00',
  size float(11,1) NOT NULL default '0.0',
  description int(11) NOT NULL default '0',
  PRIMARY KEY  (id,eventid)
) TYPE=MyISAM;

--
-- Table structure for table `inventoryentrytypes`
--

CREATE TABLE inventoryentrytypes (
  id int(11) NOT NULL default '0',
  description varchar(100) NOT NULL default '',
  PRIMARY KEY  (id)
) TYPE=MyISAM;

--
-- Table structure for table `purchaseentries`
--

CREATE TABLE purchaseentries (
  id int(11) NOT NULL auto_increment,
  username varchar(100) NOT NULL default '',
  date date NOT NULL default '0000-00-00',
  store varchar(100) NOT NULL default '',
  category int(11) NOT NULL default '0',
  description varchar(100) NOT NULL default '',
  price double NOT NULL default '0',
  PRIMARY KEY  (id)
) TYPE=MyISAM;

--
-- Table structure for table `purchaseentrycategories`
--

CREATE TABLE purchaseentrycategories (
  id int(11) NOT NULL default '0',
  description char(100) NOT NULL default '',
  PRIMARY KEY  (id)
) TYPE=MyISAM;

--
-- Table structure for table `useraccounts`
--

CREATE TABLE useraccounts (
  username varchar(100) NOT NULL default '',
  welcometext longtext NOT NULL,
  description longtext NOT NULL,
  logo varchar(100) NOT NULL default '',
  defaultdiary int(11) NOT NULL default '0',
  official tinyint(1) NOT NULL default '0',
  PRIMARY KEY  (username)
) TYPE=MyISAM;

