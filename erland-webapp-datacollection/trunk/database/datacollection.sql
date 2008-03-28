-- MySQL dump 9.09
--
-- Host: localhost    Database: datacollection
---------------------------------------------------------
-- Server version	4.0.15-max-debug

--
-- Table structure for table `collections`
--

CREATE TABLE collections (
  id int(11) NOT NULL auto_increment,
  username varchar(100) NOT NULL,
  application varchar(100) default NULL,
  title varchar(100) default NULL,
  description longtext,
  official tinyint(1) default NULL,
  sortorder varchar(100) default NULL,
  PRIMARY KEY  (id)
) TYPE=MyISAM;

--
-- Table structure for table `entries`
--

CREATE TABLE entries (
  collection int(11) NOT NULL,
  id int(11) NOT NULL auto_increment,
  uniqueentryid varchar(100) default NULL,
  title varchar(255) default NULL,
  description longtext default NULL,
  lastchanged datetime NOT NULL,
  PRIMARY KEY  (id)
) TYPE=MyISAM;

--
-- Table structure for table `entries_history`
--

CREATE TABLE entries_history (
  historyid int(11) NOT NULL auto_increment,
  collection int(11) NOT NULL,
  id int(11) NOT NULL,
  uniqueentryid varchar(100) default NULL,
  title varchar(255) default NULL,
  description longtext default NULL,
  lastchanged datetime NOT NULL,
  PRIMARY KEY  (historyid)
) TYPE=MyISAM;

--
-- Table structure for table `datas`
--

CREATE TABLE datas (
  entryid int(11) NOT NULL,
  id int(11) NOT NULL auto_increment,
  type varchar(255) default NULL,
  content longtext NOT NULL,
  url varchar(255) default NULL,
  version int(11) NOT NULL,
  PRIMARY KEY  (id)
) TYPE=MyISAM;

--
-- Table structure for table `datas_history`
--

CREATE TABLE datas_history (
  historyid int(11) NOT NULL auto_increment,
  entryhistoryid int(11) NOT NULL,
  entryid int(11) NOT NULL,
  id int(11) NOT NULL,
  type varchar(255) default NULL,
  content longtext NOT NULL,
  url varchar(255) default NULL,
  version int(11) NOT NULL,
  PRIMARY KEY  (historyid)
) TYPE=MyISAM;

--
-- Table structure for table `useraccounts`
--

CREATE TABLE useraccounts (
  username varchar(100) NOT NULL default '',
  welcometext longtext NOT NULL,
  description longtext NOT NULL,
  defaultcollection int(11) default '0',
  official tinyint(1) NOT NULL default '0',
  logo varchar(255) default NULL,
  stylesheet varchar(255) default NULL,
  anonymous tinyint(1) NOT NULL default '0',
  PRIMARY KEY  (username)
) TYPE=MyISAM;

