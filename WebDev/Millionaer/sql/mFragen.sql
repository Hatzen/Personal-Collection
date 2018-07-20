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
-- Tabellenstruktur für Tabelle `mFragen`
--

CREATE TABLE `mFragen` (
  `id` int(4) NOT NULL,
  `frage` varchar(255) NOT NULL,
  `schwere` int(1) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Daten für Tabelle `mFragen`
--

INSERT INTO `mFragen` (`id`, `frage`, `schwere`) VALUES
(1, 'Wie dumm ist Moritz?', 1),
(20, 'Wie heißt das Märchen Hänsel und ...?', 1),
(21, 'Woraus besteht ein Baum?', 1),
(22, 'Was ist keine Farbe', 2),
(23, 'Was macht ein Drucker?', 1),
(24, 'Was ist keine Pflanze?', 2),
(25, 'Warum ist die Banane krum?', 3),
(26, 'Was für eine Haarfarbe hat Bene', 1),
(27, 'Wie schmeckt pizza?', 3),
(28, 'Wie heißt der rote Teletubi', 3),
(29, 'Was macht ein Toaster', 2),
(30, 'Welches der folgenden natürlichen Nahrungsmittel enthält die meisten Eiweiße?', 2),
(31, 'Creatin ist ein...', 3),
(32, 'Wie heiÃŸt moritz?', 1),
(33, 'Wie heiÃŸt moritz?', 1),
(34, 'Wie heiÃŸt moritz?', 1),
(35, 'Wie heiÃŸt moritz?', 1),
(36, 'Wie heiÃŸt moritz?', 1),
(37, 'Woraus besteht ein Zettel?', 1),
(38, 'Was ist Dreist?', 3),
(39, 'Wie viele Kinder hat herr Suthe', 3);

--
-- Indizes der exportierten Tabellen
--

--
-- Indizes für die Tabelle `mFragen`
--
ALTER TABLE `mFragen`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
