-- MySQL dump 9.08
--
-- Host: localhost    Database: common
---------------------------------------------------------
-- Server version	4.0.13-max-nt

--
-- Table structure for table 'descriptiontags'
--

CREATE TABLE descriptiontags (
  type varchar(100) NOT NULL default '',
  tag varchar(100) NOT NULL default '',
  description longtext NOT NULL default '',
  description_en longtext NOT NULL default '',
  PRIMARY KEY  (type,tag)
) TYPE=MyISAM;

--
-- Table structure for table 'resources'
--

CREATE TABLE resources (
  application varchar(100) NOT NULL default '',
  id varchar(100) NOT NULL default '',
  value longtext NOT NULL,
  PRIMARY KEY  (application,id)
) TYPE=MyISAM;

