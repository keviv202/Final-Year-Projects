-- phpMyAdmin SQL Dump
-- version 3.4.5
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Dec 15, 2013 at 07:27 PM
-- Server version: 5.5.16
-- PHP Version: 5.3.8

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `cam_pro`
--

-- --------------------------------------------------------

--
-- Table structure for table `person`
--

CREATE TABLE IF NOT EXISTS `person` (
  `id` int(12) NOT NULL DEFAULT '0',
  `username` varchar(15) DEFAULT NULL,
  `password` varchar(15) DEFAULT NULL,
  `user_type` varchar(10) DEFAULT NULL,
  `name` varchar(25) DEFAULT NULL,
  `mo_no` bigint(10) DEFAULT NULL,
  `email` varchar(25) DEFAULT NULL,
  `s_question` varchar(200) DEFAULT NULL,
  `s_answer` varchar(25) DEFAULT NULL,
  `block_value` int(1) NOT NULL DEFAULT '1',
  `form_value` int(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `person`
--

INSERT INTO `person` (`id`, `username`, `password`, `user_type`, `name`, `mo_no`, `email`, `s_question`, `s_answer`, `block_value`, `form_value`) VALUES
(2, 'vivek', 'vivek', 'Admin', 'vivek gupta', 456456453, 'kjbhj,bvjh', 'What is Your Favourite Palace?', 'iuhu', 1, 1),
(3, 'binit', '', 'User', 'binit vaghani', 5646546, 'jkhnjbjhb', 'What is Your Favourite Color?', 'blue', 0, 1),
(4, 'abhi', '', 'User', 'abhi', 9712024414, 'fvevfv@gmail.com', 'What is Your Favourite Color?', 'blue', 1, 1),
(545, 'sunil', 'admin', 'Admin', 'sunil lakkad', 9712024414, 'lakkads1@gmail.com', 'What is Your Favourite Color?', 'blue', 1, 1),
(676, 'vinay', 'vinay', 'User', 'vinay vekariya', 9865321452, 'vinay@yahoo.com', 'What is Your Favourite Color?', 'blue', 1, 1),
(899, 'user', '', 'User', 'sunil lakkad', 9712024414, 'lakkads1@gmail.com', 'What is Your Favourite Color?', 'blue', 1, 1);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
