ALTER TABLE descriptiontags
  ADD description_en longtext NOT NULL default '';

INSERT INTO resources VALUES ('gallery','resources.nativelanguage','sv');
INSERT INTO resources VALUES ('gallery','resources.welcometext_sv','Logga in f�r att uppdatera ditt bildarkiv eller klicka [boldlinksamewindow=h�r,http://erland.homeip.net/gallery/do/guest/home?user=erland] f�r att titta i mitt bildarkiv. Du kan ocks� titta p� n�gon annans bildarkiv genom att klicka p� l�nkarna nedan.\r\nDu kan ocks� registrera ditt eget bildarkiv [boldlinksamewindow=h�r,/gallery/do/guest/registeruser]\r\n\r\n');
