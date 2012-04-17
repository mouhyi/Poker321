-- phpMyAdmin SQL Dump
-- version 3.4.5
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Apr 17, 2012 at 04:21 AM
-- Server version: 5.5.16
-- PHP Version: 5.3.8

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `team1`
--

-- --------------------------------------------------------

--
-- Table structure for table `Friends`
--

CREATE TABLE IF NOT EXISTS `Friends` (
  `u_id` int(11) NOT NULL,
  `f_id` int(11) NOT NULL,
  PRIMARY KEY (`u_id`,`f_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `Friends`
--

INSERT INTO `Friends` (`u_id`, `f_id`) VALUES
(1, 2),
(1, 4),
(2, 1),
(4, 1),
(4, 6),
(5, 6),
(6, 4),
(6, 5);

-- --------------------------------------------------------

--
-- Table structure for table `Players`
--

CREATE TABLE IF NOT EXISTS `Players` (
  `u_id` int(11) NOT NULL,
  `wins` int(11) NOT NULL,
  `losses` int(11) NOT NULL,
  `gameWinnings` double NOT NULL,
  PRIMARY KEY (`u_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `Players`
--

INSERT INTO `Players` (`u_id`, `wins`, `losses`, `gameWinnings`) VALUES
(5, 19, 27, 369),
(6, 13, 32, -72),
(8, 0, 1, -20);

-- --------------------------------------------------------

--
-- Table structure for table `Users`
--

CREATE TABLE IF NOT EXISTS `Users` (
  `u_id` int(10) NOT NULL AUTO_INCREMENT,
  `email` varchar(32) NOT NULL,
  `password` varchar(32) NOT NULL,
  `name` varchar(32) NOT NULL,
  `chips` double NOT NULL,
  `online` tinyint(1) NOT NULL,
  PRIMARY KEY (`u_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=9 ;

--
-- Dumping data for table `Users`
--

INSERT INTO `Users` (`u_id`, `email`, `password`, `name`, `chips`, `online`) VALUES
(1, 'test@test.ca', '1234', 'null', 440, 1),
(2, 'mouhyi@test.ca', 'qwerty12', 'mouhyi', 100, 0),
(3, 'test2@test.ca', 'dvsk', 'name', 65, 1),
(4, 'm@m.m', '0000', 'alves', 490, 1),
(5, 'q', '2', 'q', 1779, 1),
(6, '1', '1', '1', 979, 1),
(8, 'a', 'a', 'a', 480, 1);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
