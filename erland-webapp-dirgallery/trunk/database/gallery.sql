-- MySQL dump 9.08
--
-- Host: localhost    Database: gallery
---------------------------------------------------------
-- Server version	4.0.13-max-nt

--
-- Table structure for table 'descriptiontags'
--

CREATE TABLE descriptiontags (
  type varchar(100) NOT NULL default '',
  tag varchar(100) NOT NULL default '',
  description varchar(100) NOT NULL default '',
  PRIMARY KEY  (type,tag)
) TYPE=MyISAM;

