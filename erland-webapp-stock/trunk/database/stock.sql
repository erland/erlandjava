-- MySQL dump 10.9
--
-- Host: localhost    Database: stock
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
-- Table structure for table `accounts`
--

DROP TABLE IF EXISTS `accounts`;
CREATE TABLE `accounts` (
  `accountid` int(11) NOT NULL auto_increment,
  `username` varchar(100) NOT NULL default '',
  `name` varchar(100) NOT NULL default '',
  PRIMARY KEY  (`accountid`)
) TYPE=MyISAM;

--
-- Table structure for table `brokerstocks`
--

DROP TABLE IF EXISTS `brokerstocks`;
CREATE TABLE `brokerstocks` (
  `broker` varchar(100) NOT NULL default '',
  `code` varchar(100) NOT NULL default '',
  `name` varchar(255) NOT NULL default '',
  `cachetime` timestamp NULL default NULL,
  PRIMARY KEY  (`broker`,`code`)
) TYPE=MyISAM;

--
-- Table structure for table `rates`
--

DROP TABLE IF EXISTS `rates`;
CREATE TABLE `rates` (
  `broker` varchar(100) NOT NULL default '',
  `stock` varchar(100) NOT NULL default '',
  `date` date NOT NULL default '0000-00-00',
  `rate` double NOT NULL default '0',
  PRIMARY KEY  (`broker`,`stock`,`date`)
) TYPE=MyISAM;

--
-- Table structure for table `transactions`
--

DROP TABLE IF EXISTS `transactions`;
CREATE TABLE `transactions` (
  `accountid` int(11) NOT NULL default '0',
  `type` int(11) NOT NULL default '0',
  `brokerid` varchar(100) NOT NULL default '',
  `stockid` varchar(100) NOT NULL default '',
  `date` date NOT NULL default '0000-00-00',
  `price` double NOT NULL default '0',
  `value` double NOT NULL default '0',
  PRIMARY KEY  (`accountid`,`brokerid`,`stockid`,`date`,`type`)
) TYPE=MyISAM;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

