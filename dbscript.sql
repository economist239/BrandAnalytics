drop database if exists BrandAnalyticsDB;
create database BrandAnalyticsDB character set utf8 collate utf8_bin;;  
set character_set_client=utf8;
set character_set_connection=utf8;
set character_set_server=utf8;

use BrandAnalyticsDB;

drop table if exists BrandStats;
create table BrandStats(
	brand_id int primary key not null,
	timestmp timestamp,
	statsdata varchar(100)
);

drop table if exists Brand;
create table Brand(
	brand_id int primary key not null,
	name varchar(100) not null,
	description varchar(1000),
	website varchar(400),
	branch varchar(1000) 
);

drop table if exists Mention;
create table Mention(
	brand_id int primary key not null,
	article_id int not null,
	opinion varchar(100) not null
);

drop table if exists Article;
create table Article(
	article_id int not null,
	info_source_id int not null,
	title varchar(500) not null,
	content varchar(3000) not null,
	author varchar(100),
	timestmp timestamp,
	constraint article_uk unique (article_id, info_source_id)
);

drop table if exists InfoSource;
create table InfoSource(
	info_source_id int primary key not null,
	title varchar(100) not null,
	description varchar(1000),
	website varchar(100)	
);