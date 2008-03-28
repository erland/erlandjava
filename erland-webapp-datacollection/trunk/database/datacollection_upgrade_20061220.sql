ALTER TABLE datas
  ADD version int(11) NOT NULL default '1';

ALTER TABLE datas_history
  ADD version int(11) NOT NULL default '1';
