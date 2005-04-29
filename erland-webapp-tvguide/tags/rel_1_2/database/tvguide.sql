-- MySQL dump 10.9
--
-- Host: localhost    Database: tvguide
-- ------------------------------------------------------
-- Server version	4.1.8-nt

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE="NO_AUTO_VALUE_ON_ZERO" */;

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
  `reviewavailable` tinyint(4) NOT NULL default '0',
  `reviewcategories` varchar(255) NOT NULL default '',
  `noreviewcategories` varchar(255) NOT NULL default '',
  PRIMARY KEY  (`id`)
) TYPE=MyISAM;

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
) TYPE=MyISAM;

--
-- Table structure for table `movies`
--

DROP TABLE IF EXISTS `movies`;
CREATE TABLE `movies` (
  `title` varchar(255) NOT NULL default '',
  `review` int(11) default NULL,
  `link` varchar(100) default NULL,
  PRIMARY KEY  (`title`)
) TYPE=MyISAM;

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
  `review` int(11) NOT NULL default '0',
  `reviewLink` varchar(255) NOT NULL default '',
  PRIMARY KEY  (`id`)
) TYPE=MyISAM;

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
) TYPE=MyISAM;

--
-- Table structure for table `subscriptions`
--

DROP TABLE IF EXISTS `subscriptions`;
CREATE TABLE `subscriptions` (
  `id` int(11) NOT NULL auto_increment,
  `username` varchar(100) NOT NULL default '',
  `name` varchar(100) NOT NULL default '',
  `pattern` varchar(255) NOT NULL default '',
  PRIMARY KEY  (`id`)
) TYPE=MyISAM;

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
  `mailnotification` tinyint(4) NOT NULL default '0',
  `jabberid` varchar(100) default NULL,
  `jabbernotification` tinyint(4) NOT NULL default '0',
  PRIMARY KEY  (`username`)
) TYPE=MyISAM;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

