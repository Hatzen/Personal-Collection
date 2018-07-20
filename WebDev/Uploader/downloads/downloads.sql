
-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `downloads`
--

CREATE TABLE `downloads` (
  `Nr` int(5) NOT NULL,
  `Name` varchar(40) NOT NULL,
  `Pfad` varchar(40) NOT NULL,
  `Datum` int(15) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
