CREATE TABLE `moviecredits` (
  `movie` varchar(100) NOT NULL default '',
  `name` varchar(100) NOT NULL default '',
  `category` varchar(20) NOT NULL default '',
  `role` varchar(255) default null,
  `priority` int(11) default NULL,
  `link` varchar(100) default NULL,
  PRIMARY KEY  (`movie`,`name`,`category`)
) TYPE=MyISAM;

ALTER TABLE `subscriptions`
    ADD `type` int(11) NOT NULL default 0;

ALTER TABLE `exclusions`
    ADD `type` int(11) NOT NULL default 0;

ALTER TABLE `movies`
    ADD `category` varchar(255) default NULL;

ALTER TABLE `programs`
    ADD `category` varchar(255) default NULL;
