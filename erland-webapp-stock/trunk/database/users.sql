-- MySQL dump 9.08
--
-- Host: localhost    Database: users
---------------------------------------------------------
-- Server version	4.0.13-max-nt

--
-- Table structure for table 'userapplicationroles'
--

CREATE TABLE userapplicationroles (
  username varchar(100) NOT NULL default '',
  role varchar(100) NOT NULL default '',
  application varchar(100) NOT NULL default '',
  PRIMARY KEY  (username,application,role)
) TYPE=MyISAM;

--
-- Table structure for table 'users'
--

CREATE TABLE users (
  username varchar(100) NOT NULL default '',
  firstname varchar(100) NOT NULL default '',
  lastname varchar(100) NOT NULL default '',
  password varchar(100) NOT NULL default '',
  mail varchar(100) default NULL
) TYPE=MyISAM;

