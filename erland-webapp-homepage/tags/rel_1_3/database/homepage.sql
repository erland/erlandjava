-- MySQL dump 9.09
--
-- Host: localhost    Database: homepage
---------------------------------------------------------
-- Server version	4.0.15-max-debug

--
-- Table structure for table `sections`
--

CREATE TABLE sections (
  username varchar(100) NOT NULL default '',
  id int(11) NOT NULL auto_increment,
  parent int(11) default NULL,
  orderno int(11) default NULL,
  name varchar(100) NOT NULL default '',
  name_en varchar(100) default NULL,
  title varchar(100) default NULL,
  title_en varchar(100) default NULL,
  description longtext,
  description_en longtext,
  service int(11) default NULL,
  serviceparameters longtext,
  official tinyint(4) default NULL,
  PRIMARY KEY  (username,id)
) TYPE=MyISAM;

--
-- Table structure for table `services`
--

CREATE TABLE services (
  id int(11) NOT NULL auto_increment,
  name varchar(100) NOT NULL default '',
  name_en varchar(100) default NULL,
  serviceclass varchar(255) NOT NULL default '',
  servicedata longtext,
  customizedservicedata longtext,
  transformerclass varchar(255) default NULL,
  transformerdata longtext,
  customizedtransformerdata longtext
  PRIMARY KEY  (id)
) TYPE=MyISAM;

--
-- Table structure for table `useraccounts`
--

CREATE TABLE useraccounts (
  username varchar(100) NOT NULL default '',
  title longtext NOT NULL,
  title_en longtext NOT NULL,
  headertext longtext,
  headertext_en longtext,
  welcometext longtext NOT NULL,
  welcometext_en longtext NOT NULL,
  description longtext NOT NULL,
  description_en longtext NOT NULL,
  logo varchar(255) NOT NULL default '',
  logo_en varchar(255) default NULL,
  showlogo tinyint(1) NOT NULL default '1',
  logolink varchar(255) default NULL,
  defaultsection int(11) default '0',
  copyright longtext NOT NULL,
  official tinyint(1) NOT NULL default '0',
  stylesheet varchar(255) default NULL,
  skin varchar(100) default NULL,
  PRIMARY KEY  (username)
) TYPE=MyISAM;

