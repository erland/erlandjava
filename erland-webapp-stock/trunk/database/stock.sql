-- MySQL dump 9.09
--
-- Host: localhost    Database: stock
---------------------------------------------------------
-- Server version	4.0.15-max-debug

--
-- Table structure for table `accounts`
--

CREATE TABLE accounts (
  accountid int(11) NOT NULL auto_increment,
  username varchar(100) NOT NULL default '',
  PRIMARY KEY  (accountid)
) TYPE=MyISAM;

--
-- Table structure for table `brokerstocks`
--

CREATE TABLE brokerstocks (
  broker varchar(100) NOT NULL default '',
  code varchar(100) NOT NULL default '',
  name varchar(255) NOT NULL default '',
  PRIMARY KEY  (broker,code)
) TYPE=MyISAM;

--
-- Table structure for table `transactions`
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

