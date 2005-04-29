ALTER TABLE channels
  ADD `reviewavailable` tinyint(4) NOT NULL default '0',
  ADD `reviewcategories` varchar(255) NOT NULL default '',
  ADD `noreviewcategories` varchar(255) NOT NULL default '';

ALTER TABLE programs
  ADD `review` int(11) NOT NULL default '0',
  ADD `reviewLink` varchar(255) NOT NULL default '';


CREATE TABLE `movies` (
    `title` varchar(255) NOT NULL default '',
    `review` int(11) default NULL,
    `link` varchar(100) default NULL,
    PRIMARY KEY  (`title`)
) TYPE=MyISAM;


