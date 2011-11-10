drop database if exists BAdirty;
create database BAdirty default character set utf8 collate utf8_bin;
set character_set_client=utf8;
set character_set_connection=utf8;
set character_set_server=utf8;

use BAdirty;

drop table if exists Graphs;
create table Graphs(
	BrandId int not null,
	TickerId int not null,
	Tstamp timestamp not null,
	Val double not null
);

drop table if exists Ticker;
create table Ticker(
	Id int primary key not null auto_increment,
	TickerName varchar(300) not null
);

drop table if exists Brand;
create table Brand(
	Id int primary key not null auto_increment,
	Name varchar(100) not null,
	Description varchar(1000),
	Website varchar(400),
	BranchId int
);

drop table if exists Branch;
create table Branch(
	Id int primary key not null auto_increment,
	Name varchar(100) not null
);

drop table if exists Article;
create table Article(
	Id int primary key not null auto_increment,
	BrandId int,
	InfoSourceId int not null,
	Title varchar(500),
	Content text not null,
	Link varchar(500),
	NumLikes int,
	Tstamp timestamp
);

drop table if exists InformationSource;
create table InformationSource(
	Id int primary key not null auto_increment,
	TypeId int not null,
	Title varchar(100) not null,
    Description varchar(1000),
	Website varchar(100) not null,
	RSSSource varchar(100)
);

drop table if exists BrandDictionary;
create table BrandDictionary(
	BrandId int not null,
	Term varchar(100) not null
);

drop table if exists SemanticDictionary;
create table SemanticDictionary(
    Term varchar(50) not null,
    SemanticValue double not null
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

ALTER TABLE BrandDictionary ADD CONSTRAINT ForDictionaryBrand
FOREIGN KEY (BrandId)
REFERENCES Brand(Id);

INSERT INTO Branch (Name) VALUES("IT: программное обеспечение");
INSERT INTO Branch (Name) VALUES("мобильная связь");
INSERT INTO Branch (Name) VALUES("IT: железо");

INSERT INTO InformationSource (TypeId, Title, Description, Website) VALUES(0, "Хабрахабр", "социальная сеть и блог", "http://habrahabr.ru");
INSERT INTO InformationSource (TypeId, Title, Description, Website) VALUES(0, "Twitter", "твиты, твиты", "http://twitter.com");
INSERT INTO InformationSource (TypeId, Title, Description, Website, RSSSource) VALUES(1, "Лента.ру", "новости", "http://lenta.ru","http://lenta.ru/rss/");
INSERT INTO InformationSource (TypeId, Title, Description, Website, RSSSource) VALUES(1, "ИТАР ТАСС", "новости", "http://itar-tass.com","http://www.itar-tass.com/rss/all.xml");
INSERT INTO InformationSource (TypeId, Title, Description, Website, RSSSource) VALUES(1, "РБК", "новости", "http://rbc.ru","");
INSERT INTO InformationSource (TypeId, Title, Description, Website, RSSSource) VALUES(1, "РИА новости", "новости", "http://ria.ru","");
INSERT INTO InformationSource (TypeId, Title, Description, Website, RSSSource) VALUES(1, "ФИНАМ","всякая бурда","http://finam.ru","");
INSERT INTO InformationSource (TypeId, Title, Description, Website, RSSSource) VALUES(1, "Коммерсант", "унылые новости", "http://kommersant.ru","");
INSERT INTO InformationSource (TypeId, Title, Description, Website, RSSSource) VALUES(1, "Газета.RU", "новости", "http://gazeta.ru","http://www.gazeta.ru/export/rss/first.xml");
INSERT INTO InformationSource (TypeId, Title, Description, Website, RSSSource) VALUES(1, "Фонтанка.RU", "новости", "http://fontanka.ru","http://www.fontanka.ru/fontanka.rss");

INSERT INTO Ticker (TickerName) VALUES("Статистика упоминаемости в новостях");

INSERT INTO Brand (Name,Description,WebSite,BranchId) VALUES ("Microsoft","Operation Systems","http://microsoft.com",0);
INSERT INTO Brand (Name,Description,WebSite,BranchId) VALUES ("Apple","iCorporation","http://apple.com",0);
INSERT INTO Brand (Name,Description,WebSite,BranchId) VALUES ("Google","Searcher","http://google.com",0);
INSERT INTO Brand (Name,Description,WebSite,BranchId) VALUES ("Yota","wimax","http://yota.ru",0);
INSERT INTO Brand (Name,Description,WebSite,BranchId) VALUES ("Яндекс","Searcher","http://yandex.ru",0);

INSERT INTO BrandDictionary (BrandId,Term) VALUES (5,"yandex");
INSERT INTO BrandDictionary (BrandId,Term) VALUES (5,"yndex");
INSERT INTO BrandDictionary (BrandId,Term) VALUES (5,"яндекс");
INSERT INTO BrandDictionary (BrandId,Term) VALUES (4,"ёта");
INSERT INTO BrandDictionary (BrandId,Term) VALUES (4,"йота");
INSERT INTO BrandDictionary (BrandId,Term) VALUES (4,"yta");
INSERT INTO BrandDictionary (BrandId,Term) VALUES (4,"yota");
INSERT INTO BrandDictionary (BrandId,Term) VALUES (3,"google");
INSERT INTO BrandDictionary (BrandId,Term) VALUES (3,"gogle");
INSERT INTO BrandDictionary (BrandId,Term) VALUES (3,"гугл");
INSERT INTO BrandDictionary (BrandId,Term) VALUES (2,"apple");
INSERT INTO BrandDictionary (BrandId,Term) VALUES (2,"aple");
INSERT INTO BrandDictionary (BrandId,Term) VALUES (2,"эйпл");
INSERT INTO BrandDictionary (BrandId,Term) VALUES (2,"эпл");
INSERT INTO BrandDictionary (BrandId,Term) VALUES (1,"microsoft");
INSERT INTO BrandDictionary (BrandId,Term) VALUES (1,"mikrosoft");
INSERT INTO BrandDictionary (BrandId,Term) VALUES (1,"Windows");
INSERT INTO BrandDictionary (BrandId,Term) VALUES (1,"макрософт");
INSERT INTO BrandDictionary (BrandId,Term) VALUES (1,"майкрософт");
INSERT INTO BrandDictionary (BrandId,Term) VALUES (1,"микрософт");





