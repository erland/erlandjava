-- MySQL dump 9.09
--
-- Host: localhost    Database: companylist
---------------------------------------------------------
-- Server version	4.0.15-max-debug

--
-- Table structure for table `companies`
--

CREATE TABLE companies (
  id int(11) NOT NULL auto_increment,
  username varchar(50) default NULL,
  name varchar(50) default NULL,
  owner varchar(100) default NULL,
  PRIMARY KEY  (id)
) TYPE=MyISAM;

