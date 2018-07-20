-- phpMyAdmin SQL Dump
-- version 4.7.9
-- https://www.phpmyadmin.net/
--
-- Host: 10.123.0.70:3307
-- Erstellungszeit: 20. Jul 2018 um 19:12
-- Server-Version: 5.7.21
-- PHP-Version: 7.0.27-0+deb9u1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Datenbank: `kaihar0_db`
--

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `mAntworten`
--

CREATE TABLE `mAntworten` (
  `id` int(4) NOT NULL,
  `antwort` varchar(255) NOT NULL,
  `richtig` tinyint(1) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Daten für Tabelle `mAntworten`
--

INSERT INTO `mAntworten` (`id`, `antwort`, `richtig`) VALUES
(1, 'Dumm', 1),
(1, 'Schlau', 0),
(1, 'sehr Schlau', 0),
(1, 'nur Dumm', 0),
(20, 'Gretel', 1),
(20, 'Peter', 0),
(20, 'Bimbo', 0),
(20, 'Bambo', 0),
(21, 'Holz', 1),
(21, 'Stein', 0),
(21, 'Rostfreiem Edelstahl', 0),
(21, 'Bambus', 0),
(22, 'schwarz', 1),
(22, 'pink', 0),
(22, 'gelb', 0),
(22, 'kaminrot', 0),
(22, 'magenta', 0),
(23, 'Drucken', 1),
(23, 'Malen', 0),
(23, 'Zeichnen', 0),
(23, 'Schreiben', 0),
(24, 'Magentabletten', 1),
(24, 'Blumen', 0),
(24, 'Baum', 0),
(24, 'Efeu', 0),
(25, 'Darum', 1),
(25, 'Deshalb', 0),
(25, 'Ist halt so', 0),
(25, 'Kein Grund', 0),
(26, 'Braun', 1),
(26, 'Schwarz', 0),
(26, 'Pink', 0),
(26, 'Gelb', 0),
(27, 'sehr gut', 1),
(27, 'Ekelig', 0),
(27, 'nicht gut', 0),
(27, 'Scheiße', 0),
(28, 'Po', 1),
(28, 'lala', 0),
(28, 'Stinkiwinki', 0),
(28, 'pipi', 0),
(29, 'Toasten', 1),
(29, 'Brennen', 0),
(29, 'rauchen', 0),
(29, 'stinken', 0),
(31, 'Muskelaufbaustoff', 1),
(31, 'Waschmittel', 0),
(31, 'Duschgel', 0),
(31, 'Modelabel', 0),
(30, 'Magaquark', 1),
(30, 'Proteinshake', 0),
(30, 'Cola', 0),
(30, 'Pommes', 0),
(34, 'Moritz', 1),
(34, 'Baum', 0),
(34, 'Schwantel', 0),
(34, 'Vader', 0),
(35, 'Moritz', 1),
(35, 'Baum', 0),
(35, 'Schwantel', 0),
(35, 'Vader', 0),
(36, 'Moritz', 1),
(36, 'Baum', 0),
(36, 'Schwantel', 0),
(36, 'Vader', 0),
(37, 'Holz', 1),
(37, 'Plastik', 0),
(37, 'Natrium', 0),
(37, 'C02', 0),
(38, 'Mama zu Papa sagen', 1),
(38, 'Burder zur Schwester sagen', 0),
(38, 'Hamster zum Hund sagen', 0),
(38, 'Fliegen in der Mikrowelle einsperren', 0),
(39, '3', 1),
(39, '1', 0),
(39, '2', 0),
(39, '1231', 0),
(39, '100', 0),
(39, '5', 0),
(39, '4', 0),
(39, '6', 0),
(39, '9', 0),
(39, '8', 0),
(39, '5.5', 0);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
