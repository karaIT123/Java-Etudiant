-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Hôte : localhost
-- Généré le : ven. 20 nov. 2020 à 19:48
-- Version du serveur :  10.4.11-MariaDB
-- Version de PHP : 7.4.5

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `etudiant`
--

-- --------------------------------------------------------

--
-- Structure de la table `activite`
--

CREATE TABLE `activite` (
  `idactivite` int(11) NOT NULL,
  `titre` varchar(50) NOT NULL,
  `description` varchar(50) NOT NULL,
  `datedebut` date NOT NULL,
  `datefin` date NOT NULL,
  `prix` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `activite`
--

INSERT INTO `activite` (`idactivite`, `titre`, `description`, `datedebut`, `datefin`, `prix`) VALUES
(1, 'Paint Ball', 'Jeu de Tir a l\'arme', '2020-11-01', '2020-11-30', '500'),
(2, 'Voyage ', 'Aller au Chalais ', '2020-11-02', '2020-12-01', '800'),
(3, 'Karting', 'Jeu de course', '2020-01-01', '2020-05-05', '500'),
(4, 'Parc d\'attraction', 'Aller a la ronde', '2020-03-03', '2020-05-05', '250'),
(5, 'Peche', 'Aller au lac', '2020-01-01', '2020-03-03', '500');

-- --------------------------------------------------------

--
-- Structure de la table `etudiant`
--

CREATE TABLE `etudiant` (
  `idetudiant` int(11) NOT NULL,
  `nom` varchar(50) NOT NULL,
  `prenom` varchar(50) NOT NULL,
  `email` varchar(50) NOT NULL,
  `telephone` varchar(10) NOT NULL,
  `user` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `etudiant`
--

INSERT INTO `etudiant` (`idetudiant`, `nom`, `prenom`, `email`, `telephone`, `user`, `password`) VALUES
(1, 'Coulibaly', 'Karamoko', 'karacoulibaly50@gmail.com', '4384090940', 'kara', 'kara'),
(2, 'Coulibaly', 'Bakary', 'coulibalybaraky864@gmail.com', '4387220916', 'booba', 'booba'),
(3, 'Dembele', 'Moh', 'modem.soccer-dream@hotmail.com', '4383408167', 'moh', 'moh');

-- --------------------------------------------------------

--
-- Structure de la table `etudiant_activite`
--

CREATE TABLE `etudiant_activite` (
  `idactivite` int(11) DEFAULT NULL,
  `idetudiant` int(11) DEFAULT NULL,
  `note` decimal(10,0) DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `etudiant_activite`
--

INSERT INTO `etudiant_activite` (`idactivite`, `idetudiant`, `note`) VALUES
(1, 2, '0'),
(2, 2, '0'),
(4, 2, '0'),
(3, 3, '86'),
(2, 3, '95'),
(4, 3, '88'),
(1, 1, '75'),
(1, 3, '98'),
(4, 1, '90'),
(3, 1, '85');

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `activite`
--
ALTER TABLE `activite`
  ADD PRIMARY KEY (`idactivite`);

--
-- Index pour la table `etudiant`
--
ALTER TABLE `etudiant`
  ADD PRIMARY KEY (`idetudiant`);

--
-- Index pour la table `etudiant_activite`
--
ALTER TABLE `etudiant_activite`
  ADD KEY `fk_activite` (`idactivite`),
  ADD KEY `fk_etudiant` (`idetudiant`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `activite`
--
ALTER TABLE `activite`
  MODIFY `idactivite` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT pour la table `etudiant`
--
ALTER TABLE `etudiant`
  MODIFY `idetudiant` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=23;

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `etudiant_activite`
--
ALTER TABLE `etudiant_activite`
  ADD CONSTRAINT `fk_activite` FOREIGN KEY (`idactivite`) REFERENCES `activite` (`idactivite`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_etudiant` FOREIGN KEY (`idetudiant`) REFERENCES `etudiant` (`idetudiant`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
