ALTER TABLE descriptiontags
  ADD description_en longtext NOT NULL default '';

INSERT INTO resources VALUES ('gallery','resources.nativelanguage','sv');
INSERT INTO resources VALUES ('gallery','resources.welcometext_sv','Logga in för att uppdatera ditt bildarkiv eller klicka [boldlinksamewindow=här,http://erland.homeip.net/gallery/do/guest/home?user=erland] för att titta i mitt bildarkiv. Du kan också titta på någon annans bildarkiv genom att klicka på länkarna nedan.\r\nDu kan också registrera ditt eget bildarkiv [boldlinksamewindow=här,/gallery/do/guest/registeruser]\r\n\r\n');
