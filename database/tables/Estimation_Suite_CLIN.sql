CREATE DATABASE  IF NOT EXISTS `Estimation_Suite` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `Estimation_Suite`;
-- MySQL dump 10.13  Distrib 8.0.18, for Win64 (x86_64)
--
-- Host: localhost    Database: Estimation_Suite
-- ------------------------------------------------------
-- Server version	5.7.28-0ubuntu0.18.04.4

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `CLIN`
--

DROP TABLE IF EXISTS `CLIN`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `CLIN` (
  `idCLIN` int(11) NOT NULL AUTO_INCREMENT,
  `idProjectVersion` int(11) NOT NULL,
  `CLIN_Index` varchar(45) NOT NULL,
  `Based_On` int(11) DEFAULT NULL,
  PRIMARY KEY (`idCLIN`),
  KEY `FK_idProjectVersion_idx` (`idProjectVersion`),
  CONSTRAINT `FK_idProjectVersion` FOREIGN KEY (`idProjectVersion`) REFERENCES `ProjectVersion` (`idProjectVersion`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=198 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CLIN`
--

LOCK TABLES `CLIN` WRITE;
/*!40000 ALTER TABLE `CLIN` DISABLE KEYS */;
INSERT INTO `CLIN` VALUES (166,233,'10',NULL),(168,235,'10',166),(170,235,'12',NULL),(171,236,'10',NULL),(174,239,'10',171),(175,240,'1',NULL),(176,240,'2',NULL),(177,240,'3',NULL),(178,241,'1',175),(179,241,'2',176),(180,241,'3',177),(181,242,'1',178),(182,242,'2',179),(183,242,'3',180),(184,243,'1',181),(185,243,'2',182),(186,243,'3',183),(187,244,'1',184),(188,244,'2',185),(189,244,'3',186),(190,245,'1',NULL),(191,246,'10',168),(192,246,'12',170),(193,247,'1',NULL),(194,248,'10',174),(195,248,'11',NULL),(196,256,'1',NULL),(197,256,'2',NULL);
/*!40000 ALTER TABLE `CLIN` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-12-10 17:10:27
