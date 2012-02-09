drop database if exists BApure;
create database BApure default character set utf8 collate utf8_bin;
set character_set_client=utf8;
set character_set_connection=utf8;
set character_set_server=utf8;

use BApure;

create table Graphs(
	BrandId int not null,
	TickerId int not null,
	Tstamp timestamp not null,
	Val double not null
);

create table Ticker(
	Id int primary key not null auto_increment,
	TickerName varchar(300) not null
);

create table Brand(
	Id int primary key not null auto_increment,
	Name varchar(100) not null,
	Description varchar(1000),
	Website varchar(400),
	BranchId int,
	JsonParams varchar(400) not null
);

create table Branch(
	Id int primary key not null auto_increment,
	Name varchar(100) not null
);

create table Article(
	Id int primary key not null auto_increment,
	BrandId int,
	InfoSourceId int not null,
	Title varchar(500),
	Content longtext not null,
	Link varchar(500),
	NumLikes int,
	Tstamp timestamp
);

create table InformationSource(
	Id int primary key not null auto_increment,
	TypeId int not null,
	Title varchar(100) not null,
    Description varchar(1000),
	Website varchar(100) not null,
    RSSSource varchar(100)
);

ALTER TABLE Article ADD CONSTRAINT ForArtileInfroSource
FOREIGN KEY (InfoSourceId)
REFERENCES InformationSource(Id);

ALTER TABLE Article ADD CONSTRAINT ForArtileBrand
FOREIGN KEY (BrandId)
REFERENCES Brand(Id);

ALTER TABLE Brand ADD CONSTRAINT ForBrandBranch
FOREIGN KEY (BranchId)
REFERENCES Branch(Id);

ALTER TABLE Graphs ADD CONSTRAINT ForGraphsBrand
FOREIGN KEY (BrandId)
REFERENCES Brand(Id);

ALTER TABLE Graphs ADD CONSTRAINT ForGraphsTicker
FOREIGN KEY (TickerId)
REFERENCES Ticker(Id);

INSERT INTO Branch (Name) VALUES("IT: программное обеспечение");
INSERT INTO Branch (Name) VALUES("мобильная связь");
INSERT INTO Branch (Name) VALUES("IT: железо");

INSERT INTO InformationSource (TypeId, Title, Description, Website) VALUES(0, "Хабрахабр", "социальная сеть и блог", "habrahabr.ru");
INSERT INTO InformationSource (TypeId, Title, Description, Website) VALUES(0, "Twitter", "твиты, твиты", "twitter.com");
INSERT INTO InformationSource (TypeId, Title, Description, Website) VALUES(1, "Лента.ру", "новости", "lenta.ru");
INSERT INTO InformationSource (TypeId, Title, Description, Website) VALUES(1, "ИТАР ТАСС", "новости", "itar-tass.com");
INSERT INTO InformationSource (TypeId, Title, Description, Website) VALUES(1, "РБК", "новости", "rbc.ru");
INSERT INTO InformationSource (TypeId, Title, Description, Website) VALUES(1, "РИА новости", "новости", "ria.ru");
INSERT INTO InformationSource (TypeId, Title, Description, Website) VALUES(1, "ФИНАМ","всякая бурда","finam.ru");
INSERT INTO InformationSource (TypeId, Title, Description, Website) VALUES(1, "Коммерсант", "унылые новости", "kommersant.ru");
INSERT INTO InformationSource (TypeId, Title, Description, Website) VALUES(1, "Газета.RU", "новости", "gazeta.ru");

INSERT INTO Ticker (TickerName) VALUES("Статистика упоминаний в новостях");
INSERT INTO Ticker (TickerName) VALUES("Статистика положительных упоминаний в новостях");
INSERT INTO Ticker (TickerName) VALUES("Статистика нейтральных упоминаний в новостях");
INSERT INTO Ticker (TickerName) VALUES("Статистика отрицательных упоминаний в новостях");

