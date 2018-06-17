SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

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


CREATE TABLE IF NOT EXISTS `Feedback` (
  `tweet1` int(11) NOT NULL,
  `tweet2` int(11) NOT NULL,
  PRIMARY KEY (`tweet1`,`tweet2`),
  KEY `FK_Feedback_tweet2` (`tweet2`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `Likes` (
  `user` varchar(255) NOT NULL,
  `tweetID` int(11) NOT NULL,
  PRIMARY KEY (`user`,`tweetID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `Likes` (`user`, `tweetID`) VALUES('test', 1);
INSERT INTO `Likes` (`user`, `tweetID`) VALUES('test', 3);

CREATE TABLE IF NOT EXISTS `Relationship` (
  `userA` varchar(255) NOT NULL,
  `userB` varchar(255) NOT NULL,
  PRIMARY KEY (`userA`,`userB`),
  KEY `FK_Relationship_userB` (`userB`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `Relationship` (`userA`, `userB`) VALUES('test2', 'test');
INSERT INTO `Relationship` (`userA`, `userB`) VALUES('test3', 'test');
INSERT INTO `Relationship` (`userA`, `userB`) VALUES('test4', 'test');
INSERT INTO `Relationship` (`userA`, `userB`) VALUES('test5', 'test');
INSERT INTO `Relationship` (`userA`, `userB`) VALUES('test6', 'test');
INSERT INTO `Relationship` (`userA`, `userB`) VALUES('test7', 'test');
INSERT INTO `Relationship` (`userA`, `userB`) VALUES('test8', 'test');

CREATE TABLE IF NOT EXISTS `Tweets` (
  `tweetID` int(11) NOT NULL AUTO_INCREMENT,
  `author` varchar(255) NOT NULL,
  `message` varchar(255) NOT NULL,
  `publishDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `likes` int(11) DEFAULT NULL,
  `originalAuthor` varchar(255) DEFAULT NULL,
  `originalID` int(11) DEFAULT NULL,
  PRIMARY KEY (`tweetID`),
  KEY `FK_Tweets_author` (`author`),
  KEY `originalAuthor` (`originalAuthor`),
  KEY `originalAuthor_2` (`originalAuthor`),
  KEY `originalAuthor_3` (`originalAuthor`),
  KEY `originalAuthor_4` (`originalAuthor`),
  KEY `originalAuthor_5` (`originalAuthor`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=16 ;

CREATE TABLE IF NOT EXISTS `Users` (
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

INSERT INTO `Users` (`user`, `name`, `surname`, `birthDate`, `password`, `description`, `gender`, `youtubeChannelID`, `twitchChannelID`, `gameGenres`, `userConsoles`, `mail`, `isAdmin`) VALUES('test', 'test', 'test', '1992-12-12', 'testtest', NULL, 'male', NULL, NULL, NULL, NULL, 'test@test.com', b'0');
INSERT INTO `Users` (`user`, `name`, `surname`, `birthDate`, `password`, `description`, `gender`, `youtubeChannelID`, `twitchChannelID`, `gameGenres`, `userConsoles`, `mail`, `isAdmin`) VALUES('test2', 'test', 'test', '1992-12-12', 'testtest', NULL, 'male', NULL, NULL, NULL, NULL, 'test2@test.com', b'0');
INSERT INTO `Users` (`user`, `name`, `surname`, `birthDate`, `password`, `description`, `gender`, `youtubeChannelID`, `twitchChannelID`, `gameGenres`, `userConsoles`, `mail`, `isAdmin`) VALUES('test3', 'test', 'test', '1992-12-12', 'testtest', NULL, 'male', NULL, NULL, NULL, NULL, 'test3@test.com', b'0');
INSERT INTO `Users` (`user`, `name`, `surname`, `birthDate`, `password`, `description`, `gender`, `youtubeChannelID`, `twitchChannelID`, `gameGenres`, `userConsoles`, `mail`, `isAdmin`) VALUES('test4', 'test', 'test', '1992-12-12', 'testtest', NULL, 'male', NULL, NULL, NULL, NULL, 'test4@test.com', b'0');
INSERT INTO `Users` (`user`, `name`, `surname`, `birthDate`, `password`, `description`, `gender`, `youtubeChannelID`, `twitchChannelID`, `gameGenres`, `userConsoles`, `mail`, `isAdmin`) VALUES('test5', 'test', 'test', '1992-12-12', 'testtest', NULL, 'male', NULL, NULL, NULL, NULL, 'test5@test.com', b'0');
INSERT INTO `Users` (`user`, `name`, `surname`, `birthDate`, `password`, `description`, `gender`, `youtubeChannelID`, `twitchChannelID`, `gameGenres`, `userConsoles`, `mail`, `isAdmin`) VALUES('test6', 'test', 'test', '1992-12-12', 'testtest', NULL, 'male', NULL, NULL, NULL, NULL, 'test6@test.com', b'0');
INSERT INTO `Users` (`user`, `name`, `surname`, `birthDate`, `password`, `description`, `gender`, `youtubeChannelID`, `twitchChannelID`, `gameGenres`, `userConsoles`, `mail`, `isAdmin`) VALUES('test7', 'test', 'test', '1992-12-12', 'testtest', NULL, 'male', NULL, NULL, NULL, NULL, 'test7@test.com', b'0');
INSERT INTO `Users` (`user`, `name`, `surname`, `birthDate`, `password`, `description`, `gender`, `youtubeChannelID`, `twitchChannelID`, `gameGenres`, `userConsoles`, `mail`, `isAdmin`) VALUES('test8', 'test', 'test', '1992-12-12', 'testtest', NULL, 'male', NULL, NULL, NULL, NULL, 'test8@test.com', b'0');
INSERT INTO `Users` (`user`, `name`, `surname`, `birthDate`, `password`, `description`, `gender`, `youtubeChannelID`, `twitchChannelID`, `gameGenres`, `userConsoles`, `mail`, `isAdmin`) VALUES('admin', 'admin', 'admin', '1992-12-12', 'adminadmin', NULL, 'male', NULL, NULL, NULL, NULL, 'admin@admin.com', b'1');


ALTER TABLE `Feedback`
  ADD CONSTRAINT `FK_Feedback_tweet1` FOREIGN KEY (`tweet1`) REFERENCES `Tweets` (`tweetID`),
  ADD CONSTRAINT `FK_Feedback_tweet2` FOREIGN KEY (`tweet2`) REFERENCES `Tweets` (`tweetID`);

ALTER TABLE `Relationship`
  ADD CONSTRAINT `FK_Relationship_userA` FOREIGN KEY (`userA`) REFERENCES `Users` (`user`),
  ADD CONSTRAINT `FK_Relationship_userB` FOREIGN KEY (`userB`) REFERENCES `Users` (`user`);

ALTER TABLE `Tweets`
  ADD CONSTRAINT `FK_Tweets_author` FOREIGN KEY (`author`) REFERENCES `Users` (`user`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
