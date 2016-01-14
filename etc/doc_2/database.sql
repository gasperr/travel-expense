-- --------------------------------------------------------
-- Strežnik:                     127.0.0.1
-- Server version:               10.0.21-MariaDB - mariadb.org binary distribution
-- Server OS:                    Win64
-- HeidiSQL Različica:           9.1.0.4867
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- Dumping database structure for travel-expense
CREATE DATABASE IF NOT EXISTS `travel-expense` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `travel-expense`;


-- Dumping structure for tabela travel-expense.log
CREATE TABLE IF NOT EXISTS `log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `log` longtext NOT NULL,
  `date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `type` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Data exporting was unselected.


-- Dumping structure for tabela travel-expense.message
CREATE TABLE IF NOT EXISTS `message` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `to_user` int(11) NOT NULL,
  `from_user` int(11) NOT NULL,
  `content` varchar(2048) NOT NULL,
  `nalog_related` int(11) DEFAULT NULL,
  `zahtevek_related` int(11) DEFAULT NULL,
  `archived` tinyint(4) NOT NULL DEFAULT '0',
  `date` date NOT NULL,
  `subject` varchar(50) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `message_id_uindex` (`id`),
  KEY `to_user_message___fk` (`to_user`),
  KEY `from_user_message___fk` (`from_user`),
  KEY `related_nalog` (`nalog_related`),
  KEY `related_zahtevek` (`zahtevek_related`),
  CONSTRAINT `from_user_message___fk` FOREIGN KEY (`from_user`) REFERENCES `user` (`id`),
  CONSTRAINT `related_nalog` FOREIGN KEY (`nalog_related`) REFERENCES `nalog` (`id`),
  CONSTRAINT `related_zahtevek` FOREIGN KEY (`zahtevek_related`) REFERENCES `zahtevek` (`id`),
  CONSTRAINT `to_user_message___fk` FOREIGN KEY (`to_user`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Data exporting was unselected.


-- Dumping structure for tabela travel-expense.nalog
CREATE TABLE IF NOT EXISTS `nalog` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `status` int(11) DEFAULT NULL,
  `location` varchar(50) DEFAULT NULL,
  `purpose` varchar(1024) DEFAULT NULL,
  `fromDate` date DEFAULT NULL,
  `toDate` date DEFAULT NULL,
  `approved_by` int(11) DEFAULT NULL,
  `archived` tinyint(4) NOT NULL DEFAULT '0',
  `notes` varchar(1024) DEFAULT NULL,
  `content` varchar(2048) DEFAULT NULL,
  `covered` double NOT NULL DEFAULT '0',
  `executed_on` date DEFAULT NULL,
  `owned_by` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `nalog_id_uindex` (`id`),
  KEY `approved_by_nalog___fk` (`approved_by`),
  KEY `owned_by_fk` (`owned_by`),
  CONSTRAINT `approved_by_nalog___fk` FOREIGN KEY (`approved_by`) REFERENCES `user` (`id`),
  CONSTRAINT `owned_by_fk` FOREIGN KEY (`owned_by`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Data exporting was unselected.


-- Dumping structure for tabela travel-expense.service
CREATE TABLE IF NOT EXISTS `service` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` varchar(50) DEFAULT NULL,
  `notes` varchar(1024) DEFAULT NULL,
  `price` double DEFAULT NULL,
  `approved_price` double DEFAULT NULL,
  `nalog` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `service_id_uindex` (`id`),
  KEY `nalog` (`nalog`),
  CONSTRAINT `nalog` FOREIGN KEY (`nalog`) REFERENCES `nalog` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Data exporting was unselected.


-- Dumping structure for tabela travel-expense.user
CREATE TABLE IF NOT EXISTS `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  `surname` varchar(50) DEFAULT NULL,
  `type` int(5) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_id_uindex` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Data exporting was unselected.


-- Dumping structure for tabela travel-expense.zahtevek
CREATE TABLE IF NOT EXISTS `zahtevek` (
  `location` varchar(50) DEFAULT NULL,
  `fromDate` date DEFAULT NULL,
  `toDate` date DEFAULT NULL,
  `costs` double DEFAULT NULL,
  `content` varchar(1024) DEFAULT NULL,
  `reviewed_by` int(11) DEFAULT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `status` int(11) DEFAULT NULL,
  `archived` tinyint(4) NOT NULL DEFAULT '0',
  `nalog` int(11) DEFAULT NULL,
  `owned_by` int(11) NOT NULL,
  `note_by_managment` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `zahtevek_id_uindex` (`id`),
  KEY `reviewed_by_zahtevek___fk` (`reviewed_by`),
  KEY `nalog_fk` (`nalog`),
  KEY `owned_by__fk` (`owned_by`),
  CONSTRAINT `nalog_fk` FOREIGN KEY (`nalog`) REFERENCES `nalog` (`id`),
  CONSTRAINT `owned_by__fk` FOREIGN KEY (`owned_by`) REFERENCES `user` (`id`),
  CONSTRAINT `reviewed_by_zahtevek___fk` FOREIGN KEY (`reviewed_by`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Data exporting was unselected.
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
