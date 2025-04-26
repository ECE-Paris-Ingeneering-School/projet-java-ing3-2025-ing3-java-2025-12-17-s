-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1:3306
-- Généré le : sam. 26 avr. 2025 à 20:31
-- Version du serveur : 8.2.0
-- Version de PHP : 8.2.13

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `shopping`
--

-- --------------------------------------------------------

--
-- Structure de la table `article`
--

DROP TABLE IF EXISTS `article`;
CREATE TABLE IF NOT EXISTS `article` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nom` varchar(100) DEFAULT NULL,
  `description` text,
  `stock` int DEFAULT NULL,
  `marque` varchar(100) DEFAULT NULL,
  `prixUnitaire` double DEFAULT NULL,
  `prixVrac` double DEFAULT NULL,
  `quantiteVrac` double DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `article`
--

INSERT INTO `article` (`id`, `nom`, `description`, `stock`, `marque`, `prixUnitaire`, `prixVrac`, `quantiteVrac`) VALUES
(1, 'Stylo', 'Stylo bleu classique', 400, 'bic', 2, 1.5, 50),
(2, 'Cahier', 'Cahier 96 pages', 475, 'Aucune', 2, 1.5, 50),
(3, 'Clé USB', 'Clé USB 32 Go', 250, 'Aucune', 10, 8.5, 100),
(5, 'feuilles', NULL, 450, 'oxford', 1.25, 1, 50);

-- --------------------------------------------------------

--
-- Structure de la table `avis`
--

DROP TABLE IF EXISTS `avis`;
CREATE TABLE IF NOT EXISTS `avis` (
  `id` int NOT NULL AUTO_INCREMENT,
  `id_utilisateur` int DEFAULT NULL,
  `id_article` int DEFAULT NULL,
  `note` int DEFAULT NULL,
  `commentaire` text,
  `date_avis` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `id_utilisateur` (`id_utilisateur`),
  KEY `id_article` (`id_article`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `avis`
--

INSERT INTO `avis` (`id`, `id_utilisateur`, `id_article`, `note`, `commentaire`, `date_avis`) VALUES
(1, 3, 2, 5, 'super top', '2025-04-26 21:32:48'),
(2, 3, 1, 4, 'super', '2025-04-26 21:32:57'),
(3, 2, 1, 5, 'incroyable !', '2025-04-26 21:40:59');

-- --------------------------------------------------------

--
-- Structure de la table `commande`
--

DROP TABLE IF EXISTS `commande`;
CREATE TABLE IF NOT EXISTS `commande` (
  `id` int NOT NULL AUTO_INCREMENT,
  `id_utilisateur` int DEFAULT NULL,
  `montant` double DEFAULT NULL,
  `date_commande` datetime DEFAULT NULL,
  `statut` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `id_utilisateur` (`id_utilisateur`)
) ENGINE=MyISAM AUTO_INCREMENT=33 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `commande`
--

INSERT INTO `commande` (`id`, `id_utilisateur`, `montant`, `date_commande`, `statut`) VALUES
(1, 2, NULL, '2025-04-19 12:06:27', 'en cours'),
(2, 2, NULL, '2025-04-19 12:17:33', 'en cours'),
(3, 2, NULL, '2025-04-19 12:30:31', 'en cours'),
(4, 2, NULL, '2025-04-19 12:42:40', 'en cours'),
(5, 2, NULL, '2025-04-19 12:55:04', 'en cours'),
(6, 2, NULL, '2025-04-19 13:05:10', 'en cours'),
(7, 2, NULL, '2025-04-19 13:44:36', 'en cours'),
(8, 2, NULL, '2025-04-19 13:58:50', 'en cours'),
(9, 2, NULL, '2025-04-19 14:05:30', 'en cours'),
(10, 2, NULL, '2025-04-19 14:12:33', 'en cours'),
(11, 2, NULL, '2025-04-19 14:12:35', 'en cours'),
(12, 2, NULL, '2025-04-19 15:47:38', 'en cours'),
(13, 2, NULL, '2025-04-20 11:08:23', 'en cours'),
(14, 2, NULL, '2025-04-20 11:21:22', 'en cours'),
(15, 2, NULL, '2025-04-20 11:40:21', 'en cours'),
(16, 2, NULL, '2025-04-20 12:05:54', 'en cours'),
(17, 2, 92.625, '2025-04-20 12:31:32', 'en cours'),
(18, 2, 154.42903125, '2025-04-20 12:36:57', 'en cours'),
(19, 2, 93.75, '2025-04-20 12:50:13', 'en cours'),
(20, 2, 8.5, '2025-04-20 12:50:45', 'en cours'),
(21, 2, 23.75, '2025-04-20 13:00:45', 'en cours'),
(22, 2, 2.5, '2025-04-20 13:06:03', 'en cours'),
(23, 2, 11.25, '2025-04-20 13:10:55', 'en cours'),
(24, 2, 1.125, '2025-04-20 13:14:51', 'en cours'),
(25, 2, 5.625, '2025-04-20 21:11:14', 'en cours'),
(26, 2, 2.85, '2025-04-25 18:20:59', 'en cours'),
(27, 3, 2.85, '2025-04-26 20:01:32', 'en cours'),
(28, 3, 8985.6, '2025-04-26 20:06:16', 'en cours'),
(29, 3, 2306.25, '2025-04-26 20:12:22', 'en cours'),
(30, 3, 142.5, '2025-04-26 20:33:18', 'en cours'),
(31, 3, 9450, '2025-04-26 20:33:51', 'en cours'),
(32, 3, 47.5, '2025-04-26 21:31:03', 'en cours');

-- --------------------------------------------------------

--
-- Structure de la table `ligne_commande`
--

DROP TABLE IF EXISTS `ligne_commande`;
CREATE TABLE IF NOT EXISTS `ligne_commande` (
  `id_commande` int NOT NULL,
  `id_article` int NOT NULL,
  `quantite` int DEFAULT NULL,
  PRIMARY KEY (`id_commande`,`id_article`),
  KEY `id_article` (`id_article`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `ligne_commande`
--

INSERT INTO `ligne_commande` (`id_commande`, `id_article`, `quantite`) VALUES
(1, 3, 5),
(2, 3, 10),
(3, 1, 25),
(3, 2, 25),
(4, 2, 5),
(5, 1, 5),
(6, 1, 10),
(7, 1, 5),
(8, 1, 5),
(16, 1, 12),
(16, 3, 3),
(17, 1, 25),
(17, 2, 5),
(17, 3, 5),
(18, 1, 25),
(18, 2, 5),
(18, 3, 7),
(18, 5, 75),
(19, 5, 75),
(20, 2, 3),
(20, 5, 2),
(21, 3, 2),
(21, 5, 3),
(22, 5, 2),
(23, 1, 5),
(23, 5, 3),
(24, 5, 1),
(25, 5, 5),
(26, 1, 2),
(27, 1, 2),
(28, 1, 2),
(28, 6, 20),
(29, 3, 250),
(29, 5, 50),
(30, 1, 100),
(31, 6, 25),
(32, 2, 25);

-- --------------------------------------------------------

--
-- Structure de la table `panier`
--

DROP TABLE IF EXISTS `panier`;
CREATE TABLE IF NOT EXISTS `panier` (
  `id_client` int NOT NULL,
  `id_article` int NOT NULL,
  `quantite` int NOT NULL DEFAULT '1',
  PRIMARY KEY (`id_client`,`id_article`),
  KEY `id_article` (`id_article`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `panier`
--

INSERT INTO `panier` (`id_client`, `id_article`, `quantite`) VALUES
(2, 1, 2);

-- --------------------------------------------------------

--
-- Structure de la table `remise`
--

DROP TABLE IF EXISTS `remise`;
CREATE TABLE IF NOT EXISTS `remise` (
  `id` int NOT NULL AUTO_INCREMENT,
  `id_article` int DEFAULT NULL,
  `pourcentage` double DEFAULT NULL,
  `code` varchar(255) DEFAULT NULL,
  `date_debut` date DEFAULT NULL,
  `date_fin` date DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `id_article` (`id_article`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `remise`
--

INSERT INTO `remise` (`id`, `id_article`, `pourcentage`, `code`, `date_debut`, `date_fin`) VALUES
(1, 1, 5, 'test', '2025-04-20', '2025-07-20'),
(2, 4, 10, 'papier', '2025-04-20', '2025-07-20'),
(3, 6, 10, 'ps', '2025-04-20', '2025-07-25');

-- --------------------------------------------------------

--
-- Structure de la table `utilisateur`
--

DROP TABLE IF EXISTS `utilisateur`;
CREATE TABLE IF NOT EXISTS `utilisateur` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nom` varchar(100) DEFAULT NULL,
  `prenom` varchar(100) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `motDePasse` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `role` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `utilisateur`
--

INSERT INTO `utilisateur` (`id`, `nom`, `prenom`, `email`, `motDePasse`, `role`) VALUES
(1, 'Admin', 'Admin', 'admin@admin.com', 'admin', 'admin'),
(2, 'Client', 'Test', 'client@test.com', 'client', 'client'),
(3, 'Rihana', 'Nicolas', 'fosdjf@gmail.com', 'Nico', 'client');
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
