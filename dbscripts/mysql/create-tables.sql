--
-- Generated by AuDAO 2009 tool, a product of Spolecne s.r.o.
-- For more information please visit http://www.spoledge.com
--

-- ======================== MySQL P R O L O G ====================
-- manually: DROP DATABASE IF EXISTS __USERNAME__;
-- manually: CREATE DATABASE your_db;
-- manually: GRANT ALL PRIVILEGES ON your_db.* TO __USERNAME__@localhost IDENTIFIED BY 'your_password';

-- not needed: USE your_db; 


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;



-- ======================== TABLE USER_SECURITY_QUESTIONS ====================
CREATE TABLE USER_SECURITY_QUESTIONS (
	ua_name varchar(30) NOT NULL,
	question1 varchar(500) NOT NULL,
	answer1 varchar(500) NOT NULL,
	question2 varchar(500) NOT NULL,
	answer2 varchar(500) NOT NULL,
	question3 varchar(500) NOT NULL,
	answer3 varchar(500) NOT NULL,
	date_modified date NOT NULL,
	attempted_count bigint
--	attempted_count1 bigint,
--	attempted_count2 bigint,
--	attempted_count3 bigint
	, PRIMARY KEY(ua_name )
	, UNIQUE INDEX inx_qna( ua_name )
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- ==================== F O R E I G N   K E Y S  ====================
-- ======================== D A T A ========================
-- ======================== MySQL E P I L O G ====================

/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
