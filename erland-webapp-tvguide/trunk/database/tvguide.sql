-- MySQL dump 10.9
--
-- Host: localhost    Database: tvguide
-- ------------------------------------------------------
-- Server version	4.1.8-nt

--
-- Table structure for table `channels`
--

DROP TABLE IF EXISTS `channels`;
CREATE TABLE `channels` (
  `id` int(11) NOT NULL auto_increment,
  `tag` varchar(100) NOT NULL default '',
  `name` varchar(100) NOT NULL default '',
  `description` longtext NOT NULL,
  `logo` varchar(255) NOT NULL default '',
  `link` varchar(255) NOT NULL default '',
  `service` int(11) NOT NULL default '0',
  `serviceparameters` longtext NOT NULL,
  `cachedate` datetime NOT NULL default '0000-00-00 00:00:00',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Table structure for table `favorites`
--

DROP TABLE IF EXISTS `favorites`;
CREATE TABLE `favorites` (
  `id` int(11) NOT NULL auto_increment,
  `username` varchar(100) NOT NULL default '',
  `channel` int(11) NOT NULL default '0',
  `orderno` int(11) NOT NULL default '0',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Table structure for table `programs`
--

DROP TABLE IF EXISTS `programs`;
CREATE TABLE `programs` (
  `id` int(11) NOT NULL auto_increment,
  `name` varchar(255) NOT NULL default '',
  `description` longtext NOT NULL,
  `start` datetime NOT NULL default '0000-00-00 00:00:00',
  `stop` datetime NOT NULL default '0000-00-00 00:00:00',
  `channel` int(11) NOT NULL default '0',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Table structure for table `services`
--

DROP TABLE IF EXISTS `services`;
CREATE TABLE `services` (
  `id` int(11) NOT NULL auto_increment,
  `name` varchar(100) NOT NULL default '',
  `serviceclass` varchar(255) NOT NULL default '',
  `servicedata` longtext,
  `customizedservicedata` longtext,
  `transformerclass` varchar(255) default NULL,
  `transformerdata` longtext,
  `customizedtransformerdata` longtext,
  PRIMARY KEY  (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Table structure for table `useraccounts`
--

DROP TABLE IF EXISTS `useraccounts`;
CREATE TABLE `useraccounts` (
  `username` varchar(100) NOT NULL default '',
  `welcometext` longtext NOT NULL,
  `description` longtext NOT NULL,
  `official` tinyint(1) NOT NULL default '0',
  `stylesheet` varchar(255) default NULL,
  `skin` varchar(100) default NULL,
  `logo` varchar(255) default NULL,
  PRIMARY KEY  (`username`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;


