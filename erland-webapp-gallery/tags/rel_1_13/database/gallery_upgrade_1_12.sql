ALTER TABLE galleries
  ADD scaleExifThumbnails tinyint(1) NOT NULL default '0',
  ADD useExifThumbnails tinyint(1) NOT NULL default '0';