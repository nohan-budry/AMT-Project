CREATE DATABASE  IF NOT EXISTS `amt_project` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `amt_project`;
-- MySQL dump 10.13  Distrib 8.0.13, for macos10.14 (x86_64)
--
-- Host: localhost    Database: amt_project
-- ------------------------------------------------------
-- Server version	8.0.13

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `exploitationRights`
--

DROP TABLE IF EXISTS `exploitationRights`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `exploitationRights` (
  `issueDate` datetime DEFAULT NULL,
  `monthlyFee` int(11) DEFAULT NULL,
  `duration` int(11) DEFAULT NULL,
  `idField` int(11) NOT NULL,
  `idFarmer` int(11) NOT NULL,
  PRIMARY KEY (`idField`,`idFarmer`),
  KEY `fk_farmer_idx` (`idFarmer`),
  CONSTRAINT `fk_farmer` FOREIGN KEY (`idFarmer`) REFERENCES `farmers` (`idfarmer`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_fields` FOREIGN KEY (`idField`) REFERENCES `fields` (`idfield`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 MAX_ROWS=1000000000;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `exploitationRights`
--

LOCK TABLES `exploitationRights` WRITE;
/*!40000 ALTER TABLE `exploitationRights` DISABLE KEYS */;
/*!40000 ALTER TABLE `exploitationRights` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `farmers`
--

DROP TABLE IF EXISTS `farmers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `farmers` (
  `idFarmer` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(45) NOT NULL,
  `firstName` varchar(45) NOT NULL,
  `lastName` varchar(45) NOT NULL,
  `address` varchar(45) DEFAULT NULL,
  `email` varchar(45) NOT NULL,
  `password` varchar(1000) NOT NULL,
  `admin` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`idFarmer`),
  UNIQUE KEY `id_UNIQUE` (`idFarmer`),
  UNIQUE KEY `email_UNIQUE` (`email`),
  UNIQUE KEY `farmerscol_UNIQUE` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8 MAX_ROWS=1000000000;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `farmers`
--

LOCK TABLES `farmers` WRITE;
/*!40000 ALTER TABLE `farmers` DISABLE KEYS */;
INSERT INTO `farmers` VALUES (1,'andy','andy','moreno','aaaa','aaaa','aaaaaaa',NULL),(2,'jack','jack','Eri','potatoland','toto@gmailss.com','123',1);
/*!40000 ALTER TABLE `farmers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fields`
--

DROP TABLE IF EXISTS `fields`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `fields` (
  `idField` int(11) NOT NULL AUTO_INCREMENT,
  `size` int(11) NOT NULL,
  PRIMARY KEY (`idField`),
  UNIQUE KEY `id_UNIQUE` (`idField`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 MAX_ROWS=1000000000;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fields`
--

LOCK TABLES `fields` WRITE;
/*!40000 ALTER TABLE `fields` DISABLE KEYS */;
INSERT INTO `fields` VALUES (1,30),(2,10000),(3,22),(4,22);
/*!40000 ALTER TABLE `fields` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-11-10 15:51:21
