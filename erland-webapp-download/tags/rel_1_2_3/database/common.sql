-- MySQL dump 9.09
--
-- Host: localhost    Database: common
---------------------------------------------------------
-- Server version	4.0.15-max-debug

--
-- Table structure for table `descriptiontags`
--

CREATE TABLE descriptiontags (
  type varchar(100) NOT NULL default '',
  tag varchar(100) NOT NULL default '',
  description longtext NOT NULL,
  PRIMARY KEY  (type,tag)
) TYPE=MyISAM;

--
-- Table structure for table `resources`
--

CREATE TABLE resources (
  application varchar(100) NOT NULL default '',
  id varchar(100) NOT NULL default '',
  value varchar(255) NOT NULL default '',
  PRIMARY KEY  (application,id)
) TYPE=MyISAM;

