DROP TABLE IF EXISTS `Etudiant`;
CREATE TABLE `Etudiant` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nom` varchar(255) DEFAULT NULL,
  `prenom` varchar(255) DEFAULT NULL,
  `matricule` varchar(255) DEFAULT NULL,
  `moduleId` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_etudiant_module` (`moduleId`),
  CONSTRAINT `fk_etudiant_module` FOREIGN KEY (`moduleId`) REFERENCES `Modules` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS `Evaluation`;

CREATE TABLE `Evaluation` (
  `id` int NOT NULL AUTO_INCREMENT,
  `moduleId` int DEFAULT NULL,
  `date` datetime DEFAULT NULL,
  `coef` float DEFAULT NULL,
  `max` float DEFAULT NULL,
  `typeEvaluation` enum('TP','CC','SN','Rattrapage') DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `moduleId` (`moduleId`),
  CONSTRAINT `Evaluation_ibfk_1` FOREIGN KEY (`moduleId`) REFERENCES `Modules` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


DROP TABLE IF EXISTS `Modules`;
CREATE TABLE `Modules` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nom` varchar(255) DEFAULT NULL,
  `description` text,
  `credit` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


DROP TABLE IF EXISTS `Note`;
CREATE TABLE `Note` (
  `evaluationId` int NOT NULL,
  `etudiantId` int NOT NULL,
  `note` float DEFAULT NULL,
  PRIMARY KEY (`evaluationId`,`etudiantId`),
  KEY `etudiantId` (`etudiantId`),
  CONSTRAINT `Note_ibfk_1` FOREIGN KEY (`evaluationId`) REFERENCES `Evaluation` (`id`) ON DELETE CASCADE,
  CONSTRAINT `Note_ibfk_2` FOREIGN KEY (`etudiantId`) REFERENCES `Etudiant` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

