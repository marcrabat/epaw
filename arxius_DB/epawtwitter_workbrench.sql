-- phpMyAdmin SQL Dump
-- version 3.5.1
-- http://www.phpmyadmin.net
--
-- Servidor: localhost
-- Tiempo de generación: 13-06-2018 a las 00:32:28
-- Versión del servidor: 5.5.24-log
-- Versión de PHP: 5.4.3

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

-- -----------------------------------------------------
-- Schema epawTwitter
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `epawTwitter` ;

-- -----------------------------------------------------
-- Schema epawTwitter
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `epawTwitter` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci ;
USE `epawTwitter` ;

-- -----------------------------------------------------
-- Drop Tables
-- -----------------------------------------------------

DROP TABLE IF EXISTS `epawTwitter`.`Users`;
DROP TABLE IF EXISTS `epawTwitter`.`Tweets`;
DROP TABLE IF EXISTS `epawTwitter`.`Feedback`;
DROP TABLE IF EXISTS `epawTwitter`.`Relationship`;
DROP TABLE IF EXISTS `epawTwitter`.`Likes`;

--
-- Base de datos: `epawtwitter`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `feedback`
--

CREATE TABLE IF NOT EXISTS `epawTwitter`.`Feedback` (
  `tweet1` int(11) NOT NULL,
  `tweet2` int(11) NOT NULL,
  PRIMARY KEY (`tweet1`,`tweet2`),
  KEY `FK_Feedback_tweet2` (`tweet2`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `likes`
--

CREATE TABLE IF NOT EXISTS `epawTwitter`.`Likes` (
  `user` varchar(255) NOT NULL,
  `tweetID` int(11) NOT NULL,
  PRIMARY KEY (`user`,`tweetID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `relationship`
--

CREATE TABLE IF NOT EXISTS `epawTwitter`.`Relationship` (
  `userA` varchar(255) NOT NULL,
  `userB` varchar(255) NOT NULL,
  PRIMARY KEY (`userA`,`userB`),
  KEY `FK_Relationship_userB` (`userB`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tweets`
--

CREATE TABLE IF NOT EXISTS `epawTwitter`.`Tweets` (
  `tweetID` int(11) NOT NULL AUTO_INCREMENT,
  `author` varchar(255) NOT NULL,
  `message` varchar(255) NOT NULL,
  `publishDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `likes` int(11) DEFAULT NULL,
  PRIMARY KEY (`tweetID`),
  KEY `FK_Tweets_author` (`author`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=5 ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `users`
--

CREATE TABLE IF NOT EXISTS `epawTwitter`.`Users` (
  `user` varchar(255) NOT NULL,
  `name` varchar(45) NOT NULL,
  `surname` varchar(45) NOT NULL,
  `birthDate` date NOT NULL,
  `password` varchar(45) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `gender` varchar(59) DEFAULT NULL,
  `youtubeChannelID` varchar(45) DEFAULT NULL,
  `twitchChannelID` varchar(45) DEFAULT NULL,
  `gameGenres` varchar(255) DEFAULT NULL,
  `userConsoles` varchar(255) DEFAULT NULL,
  `mail` varchar(80) NOT NULL,
  `isAdmin` bit(1) NOT NULL DEFAULT b'0',
  PRIMARY KEY (`user`,`mail`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `feedback`
--
ALTER TABLE `feedback`
  ADD CONSTRAINT `FK_Feedback_tweet1` FOREIGN KEY (`tweet1`) REFERENCES `tweets` (`tweetID`),
  ADD CONSTRAINT `FK_Feedback_tweet2` FOREIGN KEY (`tweet2`) REFERENCES `tweets` (`tweetID`);

--
-- Filtros para la tabla `relationship`
--
ALTER TABLE `relationship`
  ADD CONSTRAINT `FK_Relationship_userA` FOREIGN KEY (`userA`) REFERENCES `users` (`user`),
  ADD CONSTRAINT `FK_Relationship_userB` FOREIGN KEY (`userB`) REFERENCES `users` (`user`);

--
-- Filtros para la tabla `tweets`
--
ALTER TABLE `tweets`
  ADD CONSTRAINT `FK_Tweets_author` FOREIGN KEY (`author`) REFERENCES `users` (`user`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;


--
-- Volcado de datos para la tabla `users`
--

INSERT INTO `users` (`user`, `name`, `surname`, `birthDate`, `password`, `description`, `gender`, `youtubeChannelID`, `twitchChannelID`, `gameGenres`, `userConsoles`, `mail`, `isAdmin`) VALUES
('test', 'test', 'test', '1992-12-12', 'testtest', NULL, 'male', NULL, NULL, NULL, NULL, 'test@test.com', b'0'),
('test2', 'test', 'test', '1992-12-12', 'testtest', NULL, 'male', NULL, NULL, NULL, NULL, 'test2@test.com', b'0'),
('test3', 'test', 'test', '1992-12-12', 'testtest', NULL, 'male', NULL, NULL, NULL, NULL, 'test3@test.com', b'0'),
('test4', 'test', 'test', '1992-12-12', 'testtest', NULL, 'male', NULL, NULL, NULL, NULL, 'test4@test.com', b'0'),
('test5', 'test', 'test', '1992-12-12', 'testtest', NULL, 'male', NULL, NULL, NULL, NULL, 'test5@test.com', b'0'),
('test6', 'test', 'test', '1992-12-12', 'testtest', NULL, 'male', NULL, NULL, NULL, NULL, 'test6@test.com', b'0'),
('test7', 'test', 'test', '1992-12-12', 'testtest', NULL, 'male', NULL, NULL, NULL, NULL, 'test7@test.com', b'0'),
('test8', 'test', 'test', '1992-12-12', 'testtest', NULL, 'male', NULL, NULL, NULL, NULL, 'test8@test.com', b'0');

--
-- Volcado de datos para la tabla `tweets`
--

INSERT INTO `tweets` (`tweetID`, `author`, `message`, `publishDate`, `likes`) VALUES
(1, 'test', 'Test of tweet', '2018-06-08 10:14:57', NULL),
(2, 'test', 'Genaral Utils is working?', '2018-06-08 10:14:57', NULL),
(3, 'test', 'It looks like yes', '2018-06-08 10:14:57', NULL),
(4, 'test2', 'it still working?', '2018-06-08 10:14:57', NULL);

--
-- Volcado de datos para la tabla `relationship`
--

INSERT INTO `relationship` (`userA`, `userB`) VALUES
('test2', 'test'),
('test3', 'test'),
('test4', 'test'),
('test5', 'test'),
('test6', 'test'),
('test7', 'test'),
('test8', 'test');

--
-- Volcado de datos para la tabla `likes`
--

INSERT INTO `likes` (`user`, `tweetID`) VALUES
('test', 1),
('test', 3);
