ALTER TABLE sections
  ADD customizedtransformerdata longtext;

ALTER TABLE useraccounts
  ADD showlogo tinyint(1) NOT NULL default '0' AFTER logo_en;

