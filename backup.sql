

--
-- Table structure for table `Etudiant`
--
DROP TABLE IF EXISTS `Etudiant`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Etudiant` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nom` varchar(255) DEFAULT NULL,
  `prenom` varchar(255) DEFAULT NULL,
  `matricule` varchar(255) DEFAULT NULL,
  `moduleId` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_etudiant_module` (`moduleId`),
  CONSTRAINT `fk_etudiant_module` FOREIGN KEY (`moduleId`) REFERENCES `Modules` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Etudiant`
--

LOCK TABLES `Etudiant` WRITE;
/*!40000 ALTER TABLE `Etudiant` DISABLE KEYS */;
INSERT INTO `Etudiant` VALUES (19,'Dupont','Jean','E12345',NULL),(20,'Martin','Marie','E67890',NULL),(21,'Bernard','Luc','E54321',NULL),(22,'Petit','Sophie','E98765',NULL),(23,'Leroy','Thomas','E11223',NULL);
/*!40000 ALTER TABLE `Etudiant` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Evaluation`
--

DROP TABLE IF EXISTS `Evaluation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Evaluation` (
  `id` int NOT NULL AUTO_INCREMENT,
  `moduleId` int DEFAULT NULL,
  `date` datetime DEFAULT NULL,
  `coef` float DEFAULT NULL,
  `max` float DEFAULT NULL,
  `typeEvaluation` enum('TP','CC','SN','Rattrapage') DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `moduleId` (`moduleId`),
  CONSTRAINT `Evaluation_ibfk_1` FOREIGN KEY (`moduleId`) REFERENCES `Modules` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Evaluation`
--

LOCK TABLES `Evaluation` WRITE;
/*!40000 ALTER TABLE `Evaluation` DISABLE KEYS */;
INSERT INTO `Evaluation` VALUES (14,9,'2023-10-15 09:00:00',0.4,20,'CC'),(15,9,'2023-11-20 14:00:00',0.6,20,'SN'),(16,10,'2023-10-18 10:30:00',0.3,20,'TP'),(17,11,'2023-11-05 08:00:00',0.5,20,'CC'),(18,12,'2023-12-10 16:00:00',1,20,'SN');
/*!40000 ALTER TABLE `Evaluation` ENABLE KEYS */;
UNLOCK TABLES;



--
-- Table structure for table `Modules`
--

DROP TABLE IF EXISTS `Modules`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Modules` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nom` varchar(255) DEFAULT NULL,
  `description` text,
  `credit` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Modules`
--

LOCK TABLES `Modules` WRITE;
/*!40000 ALTER TABLE `Modules` DISABLE KEYS */;
INSERT INTO `Modules` VALUES (9,'Mathématiques','Cours de mathématiques avancées',5),(10,'Physique','Cours de physique fondamentale',4),(11,'Informatique','Programmation et algorithmique',6),(12,'Chimie','Chimie organique et inorganique',4),(13,'Anglais','Cours d\'anglais technique',3);
/*!40000 ALTER TABLE `Modules` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Note`
--

DROP TABLE IF EXISTS `Note`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Note` (
  `evaluationId` int NOT NULL,
  `etudiantId` int NOT NULL,
  `note` float DEFAULT NULL,
  PRIMARY KEY (`evaluationId`,`etudiantId`),
  KEY `etudiantId` (`etudiantId`),
  CONSTRAINT `Note_ibfk_1` FOREIGN KEY (`evaluationId`) REFERENCES `Evaluation` (`id`),
  CONSTRAINT `Note_ibfk_2` FOREIGN KEY (`etudiantId`) REFERENCES `Etudiant` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Note`
--

LOCK TABLES `Note` WRITE;
/*!40000 ALTER TABLE `Note` DISABLE KEYS */;
INSERT INTO `Note` VALUES (14,19,15.5),(14,20,12),(14,21,18),(15,19,14),(16,21,10),(16,22,11.5),(17,23,9),(18,19,17),(18,20,13.5);
/*!40000 ALTER TABLE `Note` ENABLE KEYS */;
UNLOCK TABLES;


ALTER TABLE Evaluation DROP FOREIGN KEY Evaluation_ibfk_1;

ALTER TABLE Etudiant DROP FOREIGN KEY fk_etudiant_module;


ALTER TABLE Evaluation
ADD CONSTRAINT Evaluation_ibfk_1
FOREIGN KEY (moduleId) REFERENCES Modules(id)
ON DELETE CASCADE;

ALTER TABLE Etudiant
ADD CONSTRAINT fk_etudiant_module
FOREIGN KEY (moduleId) REFERENCES Modules(id)
ON DELETE CASCADE;


-- Supprimer la contrainte de clé étrangère existante dans Note
ALTER TABLE Note DROP FOREIGN KEY Note_ibfk_1;

-- Ajouter la contrainte de clé étrangère avec ON DELETE CASCADE dans Note
ALTER TABLE Note
ADD CONSTRAINT Note_ibfk_1
FOREIGN KEY (evaluationId) REFERENCES Evaluation(id)
ON DELETE CASCADE;