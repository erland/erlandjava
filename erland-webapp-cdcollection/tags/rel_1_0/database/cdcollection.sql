-- MySQL dump 9.09
--
-- Host: localhost    Database: cdcollection
---------------------------------------------------------
-- Server version	4.0.15-max-debug

--
-- Table structure for table `collectionmedias`
--

CREATE TABLE collectionmedias (
  collectionid int(11) NOT NULL default '0',
  mediaid int(11) NOT NULL default '0',
  PRIMARY KEY  (collectionid,mediaid)
) TYPE=MyISAM;

--
-- Table structure for table `collections`
--

CREATE TABLE collections (
  id int(11) NOT NULL auto_increment,
  username varchar(100) NOT NULL default '',
  title varchar(100) default NULL,
  title_en varchar(100) default NULL,
  description longtext,
  description_en longtext,
  official tinyint(1) default NULL,
  sortorder varchar(100) default NULL,
  PRIMARY KEY  (id)
) TYPE=MyISAM;

--
-- Table structure for table `discs`
--

CREATE TABLE discs (
  mediaid int(11) NOT NULL default '0',
  id int(11) NOT NULL auto_increment,
  uniquediscid varchar(100) default NULL,
  title varchar(255) default NULL,
  artist varchar(255) default NULL,
  year int(11) default NULL,
  tracktitlepattern varchar(100) default NULL,
  trackartistpattern varchar(100) default NULL,
  PRIMARY KEY  (id)
) TYPE=MyISAM;

--
-- Table structure for table `disctracks`
--

CREATE TABLE disctracks (
  discid int(11) NOT NULL default '0',
  id int(11) NOT NULL auto_increment,
  trackno int(11) default NULL,
  title varchar(255) default NULL,
  artist varchar(255) default NULL,
  length int(11) default NULL,
  PRIMARY KEY  (id)
) TYPE=MyISAM;

--
-- Table structure for table `medias`
--

CREATE TABLE medias (
  id int(11) NOT NULL auto_increment,
  title varchar(255) default NULL,
  artist varchar(255) default NULL,
  uniquemediaid varchar(100) default NULL,
  year int(11) default NULL,
  coverurl varchar(255) default NULL,
  PRIMARY KEY  (id)
) TYPE=MyISAM;

--
-- Table structure for table `useraccounts`
--

CREATE TABLE useraccounts (
  username varchar(100) NOT NULL default '',
  welcometext longtext NOT NULL,
  welcometext_en longtext NOT NULL,
  description longtext NOT NULL,
  description_en longtext NOT NULL,
  defaultcollection int(11) default '0',
  official tinyint(1) NOT NULL default '0',
  logo varchar(255) default NULL,
  stylesheet varchar(255) default NULL,
  PRIMARY KEY  (username)
) TYPE=MyISAM;

