-- MySQL dump 9.09
--
-- Host: localhost    Database: help
---------------------------------------------------------
-- Server version	4.0.15-max-debug

--
-- Table structure for table `applications`
--

CREATE TABLE applications (
  name varchar(100) NOT NULL default '',
  username varchar(100) NOT NULL default '',
  titlenative varchar(100) default NULL,
  titleenglish varchar(100) default NULL,
  logo varchar(255) default NULL,
  official tinyint(1) default NULL,
  descriptionnative longtext,
  descriptionenglish longtext,
  PRIMARY KEY  (name)
) TYPE=MyISAM;

--
-- Table structure for table `applicationversions`
--

CREATE TABLE applicationversions (
  application varchar(100) NOT NULL default '',
  version varchar(20) NOT NULL default '',
  orderno int(11) NOT NULL default '0',
  PRIMARY KEY  (application,version)
) TYPE=MyISAM;

--
-- Table structure for table `attributes`
--

CREATE TABLE attributes (
  id int(11) NOT NULL auto_increment,
  application varchar(100) NOT NULL default '',
  version varchar(20) NOT NULL default '',
  chapter varchar(100) NOT NULL default '',
  orderno int(11) NOT NULL default '0',
  namenative varchar(100) default NULL,
  nameenglish varchar(100) default NULL,
  descriptionnative longtext,
  descriptionenglish longtext,
  PRIMARY KEY  (id)
) TYPE=MyISAM;

--
-- Table structure for table `chapters`
--

CREATE TABLE chapters (
  id int(11) NOT NULL auto_increment,
  application varchar(100) NOT NULL default '',
  version varchar(20) NOT NULL default '',
  chapter varchar(100) NOT NULL default '',
  orderno int(11) NOT NULL default '0',
  titlenative varchar(100) default NULL,
  titleenglish varchar(100) default NULL,
  headernative longtext,
  headerenglish longtext,
  footernative longtext,
  footerenglish longtext,
  PRIMARY KEY  (id)
) TYPE=MyISAM;

--
-- Table structure for table `useraccounts`
--

CREATE TABLE useraccounts (
  username varchar(100) NOT NULL default '',
  PRIMARY KEY  (username)
) TYPE=MyISAM;

