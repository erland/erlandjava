ALTER TABLE galleries
  ADD title_en varchar(100) NOT NULL default '' AFTER title,
  ADD description_en longtext NOT NULL default '' AFTER description,
  ADD thumbnailpicturetitle longtext default NULL,
  ADD picturetitle longtext default NULL,
  ADD thumbnailrow1 longtext default NULL,
  ADD thumbnailrow2 longtext default NULL,
  ADD thumbnailrow3 longtext default NULL,
  ADD copyright longtext default NULL,
  DROP showpicturedescription;

ALTER TABLE categories
  ADD name_en varchar(100) NOT NULL default '' AFTER name;

ALTER TABLE pictures
  ADD title_en varchar(255) NOT NULL default '' AFTER title,
  ADD description_en longtext NOT NULL default '' AFTER description,
  ADD file varchar(255) NOT NULL default '' AFTER description_en;

ALTER TABLE useraccounts
  ADD welcometext_en longtext NOT NULL AFTER welcometext,
  ADD description_en longtext NOT NULL AFTER description,
  MODIFY copyright longtext default NULL;

ALTER TABLE skins
  ADD stylesheet varchar(255) default NULL;