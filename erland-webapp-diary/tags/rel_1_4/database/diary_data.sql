-- MySQL dump 9.09
--
-- Host: localhost    Database: diary
---------------------------------------------------------
-- Server version	4.0.15-max-debug


--
-- Dumping data for table `inventoryentryeventtypes`
--

INSERT INTO inventoryentryeventtypes VALUES (1,'Ink%C3%B6pt');
INSERT INTO inventoryentryeventtypes VALUES (2,'D%C3%B6dad');
INSERT INTO inventoryentryeventtypes VALUES (3,'D%C3%B6d');
INSERT INTO inventoryentryeventtypes VALUES (4,'S%C3%A5ld');
INSERT INTO inventoryentryeventtypes VALUES (5,'Lekt');
INSERT INTO inventoryentryeventtypes VALUES (6,'Yngel');
INSERT INTO inventoryentryeventtypes VALUES (7,'Uppm%C3%A4tt');
INSERT INTO inventoryentryeventtypes VALUES (8,'F%C3%B6dd');
INSERT INTO inventoryentryeventtypes VALUES (9,'Flyttad');

--
-- Dumping data for table `inventoryentrytypes`
--

INSERT INTO inventoryentrytypes VALUES (1,'Fisk');

--
-- Dumping data for table `inventoryentrysexes`
--

INSERT INTO inventoryentrysexes VALUES (1,'Hane');
INSERT INTO inventoryentrysexes VALUES (2,'Hona');
INSERT INTO inventoryentrysexes VALUES (0,'');

--
-- Dumping data for table `purchaseentrycategories`
--

INSERT INTO purchaseentrycategories VALUES (0,'Okänd');
INSERT INTO purchaseentrycategories VALUES (1,'Fisk');
INSERT INTO purchaseentrycategories VALUES (2,'V%C3%A4xt');
INSERT INTO purchaseentrycategories VALUES (3,'Mat');
INSERT INTO purchaseentrycategories VALUES (4,'Akvarium');
INSERT INTO purchaseentrycategories VALUES (5,'Tillbeh%C3%B6r');
INSERT INTO purchaseentrycategories VALUES (6,'Inredning');
INSERT INTO purchaseentrycategories VALUES (7,'Preparat');
INSERT INTO purchaseentrycategories VALUES (8,'Medicin');
INSERT INTO purchaseentrycategories VALUES (9,'V%C3%A4xtn%C3%A4ring');

--
-- Dumping data for table `useraccounts`
--

INSERT INTO useraccounts VALUES ('test','Du+kan+%C3%A4ndra+denna+text+och+mycket+annat+genom+att+v%C3%A4lja+Inst%C3%A4llningar+i+menyn+till+v%C3%A4nster.%0D%0AN%C3%A4r+du+har+registrerat+all+n%C3%B6dv%C3%A4ndig+information+i+ditt+bildarkiv+can+du+g%C3%B6ra+det+synligt+p%C3%A5+f%C3%B6rstasidan+genom+att+s%C3%A4tta+en+markering+i+Inst%C3%A4llningar','En+kort+beskrivning+av+detta+bild+arkiv','',0,0);

INSERT INTO speciestypes VALUES (1,'Fisk');