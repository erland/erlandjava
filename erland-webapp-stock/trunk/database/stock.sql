-- MySQL dump 9.08
--
-- Host: localhost    Database: stock
---------------------------------------------------------
-- Server version	4.0.13-max-nt

--
-- Table structure for table 'accounts'
--

CREATE TABLE accounts (
  accountid int(11) NOT NULL auto_increment,
  username varchar(100) NOT NULL default '',
  PRIMARY KEY  (accountid)
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
-- Table structure for table 'transactions'
--

CREATE TABLE transactions (
  accountid int(11) NOT NULL default '0',
  type int(11) NOT NULL default '0',
  brokerid varchar(100) NOT NULL default '',
  stockid varchar(100) NOT NULL default '',
  date date NOT NULL default '0000-00-00',
  price double NOT NULL default '0',
  value double NOT NULL default '0',
  PRIMARY KEY  (accountid,brokerid,stockid,date,type)
) TYPE=MyISAM;

--
-- Table structure for table 'users'
--

CREATE TABLE users (
  username varchar(100) NOT NULL default '',
  firstname varchar(100) NOT NULL default '',
  lastname varchar(100) NOT NULL default '',
  password varchar(100) NOT NULL default '',
  PRIMARY KEY  (username)
) TYPE=MyISAM;

