ALTER TABLE purchaseentries
  ADD category int(11) NOT NULL default '0' AFTER store;

CREATE TABLE purchaseentrycategories (
  id int(11) NOT NULL default '0',
  description char(100) NOT NULL default '',
  PRIMARY KEY  (id)
) TYPE=MyISAM;

INSERT INTO purchaseentrycategories VALUES (0,'Okänd');
INSERT INTO purchaseentrycategories VALUES (1,'Fisk');
INSERT INTO purchaseentrycategories VALUES (2,'V%C3%A4xt');
INSERT INTO purchaseentrycategories VALUES (3,'Mat');
INSERT INTO purchaseentrycategories VALUES (4,'Akvarium');
INSERT INTO purchaseentrycategories VALUES (5,'Tillbeh%C3%B6r');
INSERT INTO purchaseentrycategories VALUES (6,'Inredning');

INSERT INTO inventoryentryeventtypes VALUES (8,'F%C3%B6dd');
