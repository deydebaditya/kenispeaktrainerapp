-- phpMyAdmin SQL Dump
-- version 4.7.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Feb 04, 2018 at 03:49 PM
-- Server version: 10.1.25-MariaDB
-- PHP Version: 5.6.31

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `keni`
--

-- --------------------------------------------------------

--
-- Table structure for table `activity`
--

CREATE TABLE `activity` (
  `activity_num` varchar(20) NOT NULL,
  `pri_mob_num` bigint(20) NOT NULL,
  `_date` varchar(20) NOT NULL,
  `school_id` varchar(20) NOT NULL,
  `_leave` int(11) NOT NULL DEFAULT '0',
  `leave_reason` varchar(2000) DEFAULT NULL,
  `holiday` int(11) NOT NULL DEFAULT '0',
  `holiday_reason` varchar(2000) DEFAULT NULL,
  `sessions` int(11) NOT NULL DEFAULT '0',
  `num_of_sessions` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `assessment`
--

CREATE TABLE `assessment` (
  `school_code` varchar(20) NOT NULL,
  `pri_mob_num` bigint(20) NOT NULL,
  `name_trainer` varchar(500) NOT NULL,
  `school_name` varchar(1000) NOT NULL,
  `term_num` bigint(20) NOT NULL,
  `goal_num` bigint(20) NOT NULL,
  `class` varchar(100) NOT NULL,
  `section` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `marks`
--

CREATE TABLE `marks` (
  `school_code` varchar(20) NOT NULL,
  `student_id` varchar(100) NOT NULL,
  `student_name` varchar(1000) NOT NULL,
  `term_num` bigint(20) NOT NULL,
  `goal_num` bigint(20) NOT NULL,
  `question_num` bigint(20) NOT NULL,
  `marks` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `schools`
--

CREATE TABLE `schools` (
  `school_code` varchar(20) NOT NULL,
  `school_name` varchar(2000) NOT NULL,
  `school_run_by` varchar(20000) NOT NULL,
  `board` varchar(2000) NOT NULL,
  `_for` varchar(2000) NOT NULL,
  `type` varchar(2000) NOT NULL,
  `client` varchar(2000) NOT NULL,
  `school_pri_mob_num` bigint(20) NOT NULL,
  `landline` bigint(20) NOT NULL,
  `add_line1` varchar(1000) NOT NULL,
  `add_line2` varchar(2000) NOT NULL,
  `add_line3` varchar(3000) DEFAULT NULL,
  `city` varchar(1000) NOT NULL,
  `state` varchar(1000) NOT NULL,
  `pincode` bigint(20) NOT NULL,
  `contact_name` varchar(1000) NOT NULL,
  `designation` varchar(1000) NOT NULL,
  `name_principal` varchar(1000) NOT NULL,
  `contact_principal` bigint(15) NOT NULL,
  `school_strength` bigint(15) NOT NULL,
  `num_students_training` bigint(15) NOT NULL,
  `classes_from` bigint(15) NOT NULL,
  `classes_upto` bigint(15) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `sessions`
--

CREATE TABLE `sessions` (
  `_date` varchar(20) NOT NULL,
  `pri_mob_num` bigint(20) NOT NULL,
  `school_code` varchar(20) NOT NULL,
  `record_num` bigint(20) NOT NULL,
  `class` varchar(100) NOT NULL,
  `section` varchar(100) DEFAULT NULL,
  `activity_num` varchar(20) NOT NULL,
  `session_num` varchar(20) NOT NULL,
  `brief_of_activities` mediumtext NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `students`
--

CREATE TABLE `students` (
  `school_code` varchar(20) NOT NULL,
  `school_name` varchar(1000) NOT NULL,
  `class` varchar(100) NOT NULL,
  `section` varchar(100) NOT NULL,
  `student_id` varchar(100) NOT NULL,
  `student_name` varchar(1000) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `trainer`
--

CREATE TABLE `trainer` (
  `name` varchar(300) NOT NULL,
  `email` varchar(300) NOT NULL,
  `password` varchar(300) NOT NULL,
  `pri_mob_num` bigint(20) NOT NULL,
  `alt_mob_num` bigint(20) DEFAULT NULL,
  `landline` bigint(20) DEFAULT NULL,
  `add_line1` varchar(1000) NOT NULL,
  `add_line2` varchar(2000) NOT NULL,
  `add_line3` varchar(3000) DEFAULT NULL,
  `city` varchar(200) NOT NULL,
  `state` varchar(200) NOT NULL,
  `pincode` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `activity`
--
ALTER TABLE `activity`
  ADD PRIMARY KEY (`activity_num`),
  ADD KEY `pri_mob_num` (`pri_mob_num`);

--
-- Indexes for table `assessment`
--
ALTER TABLE `assessment`
  ADD PRIMARY KEY (`school_code`,`pri_mob_num`);

--
-- Indexes for table `marks`
--
ALTER TABLE `marks`
  ADD PRIMARY KEY (`school_code`,`student_id`,`term_num`,`goal_num`,`question_num`);

--
-- Indexes for table `schools`
--
ALTER TABLE `schools`
  ADD PRIMARY KEY (`school_code`);

--
-- Indexes for table `sessions`
--
ALTER TABLE `sessions`
  ADD PRIMARY KEY (`_date`,`pri_mob_num`,`school_code`),
  ADD KEY `school_code` (`school_code`),
  ADD KEY `activity_num` (`activity_num`);

--
-- Indexes for table `students`
--
ALTER TABLE `students`
  ADD PRIMARY KEY (`school_code`,`student_id`);

--
-- Indexes for table `trainer`
--
ALTER TABLE `trainer`
  ADD PRIMARY KEY (`pri_mob_num`),
  ADD UNIQUE KEY `email` (`email`),
  ADD UNIQUE KEY `password` (`password`),
  ADD UNIQUE KEY `pri_mob_num` (`pri_mob_num`);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `activity`
--
ALTER TABLE `activity`
  ADD CONSTRAINT `activity_ibfk_1` FOREIGN KEY (`pri_mob_num`) REFERENCES `trainer` (`pri_mob_num`) ON UPDATE CASCADE,
  ADD CONSTRAINT `activity_ibfk_2` FOREIGN KEY (`pri_mob_num`) REFERENCES `trainer` (`pri_mob_num`) ON UPDATE CASCADE;

--
-- Constraints for table `assessment`
--
ALTER TABLE `assessment`
  ADD CONSTRAINT `assessment_ibfk_1` FOREIGN KEY (`school_code`) REFERENCES `schools` (`school_code`) ON UPDATE CASCADE,
  ADD CONSTRAINT `assessment_ibfk_2` FOREIGN KEY (`school_code`) REFERENCES `schools` (`school_code`) ON UPDATE CASCADE;

--
-- Constraints for table `marks`
--
ALTER TABLE `marks`
  ADD CONSTRAINT `marks_ibfk_1` FOREIGN KEY (`school_code`) REFERENCES `schools` (`school_code`) ON UPDATE CASCADE,
  ADD CONSTRAINT `marks_ibfk_2` FOREIGN KEY (`school_code`) REFERENCES `schools` (`school_code`) ON UPDATE CASCADE;

--
-- Constraints for table `sessions`
--
ALTER TABLE `sessions`
  ADD CONSTRAINT `sessions_ibfk_1` FOREIGN KEY (`school_code`) REFERENCES `schools` (`school_code`) ON UPDATE CASCADE,
  ADD CONSTRAINT `sessions_ibfk_2` FOREIGN KEY (`activity_num`) REFERENCES `activity` (`activity_num`) ON UPDATE CASCADE,
  ADD CONSTRAINT `sessions_ibfk_3` FOREIGN KEY (`school_code`) REFERENCES `schools` (`school_code`) ON UPDATE CASCADE,
  ADD CONSTRAINT `sessions_ibfk_4` FOREIGN KEY (`activity_num`) REFERENCES `activity` (`activity_num`) ON UPDATE CASCADE;

--
-- Constraints for table `students`
--
ALTER TABLE `students`
  ADD CONSTRAINT `students_ibfk_1` FOREIGN KEY (`school_code`) REFERENCES `schools` (`school_code`) ON UPDATE CASCADE,
  ADD CONSTRAINT `students_ibfk_2` FOREIGN KEY (`school_code`) REFERENCES `schools` (`school_code`) ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
