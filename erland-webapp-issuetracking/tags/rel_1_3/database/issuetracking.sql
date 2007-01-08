-- MySQL dump 9.09
--
-- Host: localhost    Database: issuetracking
---------------------------------------------------------
-- Server version	4.0.15-max-debug

--
-- Table structure for table `applicationdispatchers`
--

CREATE TABLE applicationdispatchers (
  application varchar(100) NOT NULL default '',
  prevstate int(11) NOT NULL default '0',
  newstate int(11) NOT NULL default '0',
  username varchar(100) default NULL,
  PRIMARY KEY  (application,prevstate,newstate)
) TYPE=MyISAM;

--
-- Table structure for table `applications`
--

CREATE TABLE applications (
  name varchar(100) NOT NULL default '',
  username varchar(100) NOT NULL default '',
  titlenative varchar(100) NOT NULL default '',
  titleenglish varchar(100) NOT NULL default '',
  logo varchar(255) default NULL,
  official tinyint(1) NOT NULL default '0',
  PRIMARY KEY  (name)
) TYPE=MyISAM;

--
-- Table structure for table `issueevents`
--

CREATE TABLE issueevents (
  issueid int(11) NOT NULL default '0',
  eventid int(11) NOT NULL auto_increment,
  prevstate int(11) NOT NULL default '0',
  state int(11) NOT NULL default '0',
  date datetime NOT NULL default '0000-00-00 00:00:00',
  username varchar(100) NOT NULL default '',
  description longtext,
  PRIMARY KEY  (issueid,eventid)
) TYPE=MyISAM;

--
-- Table structure for table `issues`
--

CREATE TABLE issues (
  id int(11) NOT NULL auto_increment,
  application varchar(100) NOT NULL default '',
  version varchar(100) default NULL,
  username varchar(100) default NULL,
  realname varchar(100) default NULL,
  mail varchar(100) default NULL,
  type int(11) NOT NULL default '0',
  extref varchar(255) default NULL,
  title varchar(255) NOT NULL default '',
  description longtext,
  PRIMARY KEY  (id)
) TYPE=MyISAM;

--
-- Table structure for table `useraccounts`
--

CREATE TABLE useraccounts (
  username varchar(100) NOT NULL default '',
  description longtext NOT NULL,
  PRIMARY KEY  (username)
) TYPE=MyISAM;

