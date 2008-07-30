-- MySQL dump 9.09
--
-- Host: localhost    Database: common
---------------------------------------------------------
-- Server version	4.0.15-max-debug

--
-- Table structure for table `mail`
--

CREATE TABLE mail (
  application varchar(100) NOT NULL,
  email varchar(100) NOT NULL,
  mailinglist varchar(100) default NULL,
  date datetime NOT NULL,
  lastdate datetime NOT NULL,
  PRIMARY KEY  (application,email)
) TYPE=MyISAM;
