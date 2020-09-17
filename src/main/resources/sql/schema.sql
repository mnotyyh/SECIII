DROP DATABASE IF EXISTS docs;
CREATE DATABASE docs DEFAULT CHARACTER SET utf8;
USE docs;

CREATE TABLE paper (
    id   INT AUTO_INCREMENT PRIMARY KEY,
    Document_title TEXT,
    Authors LONGTEXT,
    Author_Affiliations TEXT,
    Publication_Title TEXT,
    Date_Added_To_Xplore TEXT,
    Publication_Year VARCHAR(10),
    Volume TEXT,
    Issue TEXT,
    Start_Page VARCHAR(10),
    End_Page VARCHAR(10),
    Abstract TEXT,
    ISSN TEXT,
    ISBNs TEXT,
    DOI TEXT,
    Funding_Information TEXT,
    PDF_Link VARCHAR(255),
    INDEX pdf (PDF_Link(120)),
    Author_Keywords TEXT,
    IEEE_Terms TEXT,
    INSPEC_Controlled_Terms TEXT,
    INSPEC_Non_Controlled_Terms TEXT,
    Mesh_Terms TEXT,
    Article_Citation_Count VARCHAR(10),
    Reference_Count VARCHAR(10),
    License TEXT,
    Online_Date TEXT,
    Issue_Date TEXT,
    Meeting_Date TEXT,
    Publisher VARCHAR(45),
    Document_Identifier VARCHAR(45)
)ENGINE=InnoDB;


CREATE TABLE simplepaper(
    id   INT AUTO_INCREMENT PRIMARY KEY,
    Document_title  TEXT,
    paper_id  INT,
    Authors VARCHAR(50),
    INDEX author (Authors(25)),
    Author_Affiliations TEXT,
    INDEX org (Author_Affiliations(100)),
    Publication_Title TEXT,
    Publication_Year TEXT,
    Author_Keywords TEXT,
    Article_Citation_Count VARCHAR(10)
)ENGINE=InnoDB;

CREATE TABLE author(
    id  INT AUTO_INCREMENT PRIMARY KEY,
    Author_name     LONGTEXT,
    INDEX author (Author_name(25)),
    Org LONGTEXT,
    INDEX org (Org(100)),
    Paper_list      LONGTEXT,
    Paper_num       INT,
    Citation_sum    INT,
    Point           FLOAT
)ENGINE=InnoDB;

CREATE TABLE org(
    id  INT AUTO_INCREMENT PRIMARY KEY,
    Org_name     TEXT,
    INDEX orgname (Org_name(100)),
    Author_list        LONGTEXT,
    Paper_list      TEXT,
    Paper_num       INT,
    Author_num      INT,
    Citation_sum    INT,
    Point           FLOAT
)ENGINE=InnoDB;

CREATE TABLE field(
    id  INT AUTO_INCREMENT PRIMARY KEY,
    Field_name      TEXT,
    Paper_list      LONGTEXT,
    Paper_num       INT,
    Citation_sum    INT,
    Point           FLOAT
)ENGINE=InnoDB;

COMMIT;