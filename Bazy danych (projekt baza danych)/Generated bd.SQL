/*
Created: 2024-11-06
Modified: 2024-11-22
Project: Dom_Seniora
Model: Model Logiczny
Company: Poletechnika Warszawska
Author: Adrian Lis, Daria Shevchenko
Version: 3.0
Database: Oracle 18c
*/


-- Create tables section -------------------------------------------------

-- Table Domy_Seniora

CREATE TABLE Domy_Seniora(
  Id_Domu_Seniora Integer NOT NULL,
  Nazwa Varchar2(20 ) NOT NULL,
  Data_zalozenia Date NOT NULL,
  Liczba_Pokoi Integer NOT NULL,
  Max_Liczba_Mieszkancow Integer NOT NULL,
  Opis_Placowki Varchar2(400 ),
  Czynny_Calodobowo Char(1 ) NOT NULL
        CHECK (Czynny_Calodobowo IN ('T', 'N')),
  Srednia_Cena_Mieszkania Number(10,2) NOT NULL,
  Id_Adres Integer NOT NULL,
  Id_Sponsora Integer
)
/

-- Create indexes for table Domy_Seniora

CREATE INDEX IX_Relationship7 ON Domy_Seniora (Id_Adres)
/

CREATE INDEX IX_Relationship10 ON Domy_Seniora (Id_Sponsora)
/

-- Add keys for table Domy_Seniora

ALTER TABLE Domy_Seniora 
    ADD CONSTRAINT Unique_Identifier1 PRIMARY KEY (Id_Domu_Seniora)
/

-- Table Seniorzy

CREATE TABLE Seniorzy(
  Id_Seniora Integer NOT NULL,
  Imie Varchar2(20 ) NOT NULL,
  Nazwisko Varchar2(30 ) NOT NULL,
  Data_Urodzenia Date NOT NULL,
  PESEL Char(11 ),
  Plec Char(1 ) NOT NULL
        CHECK (Plec IN ('M', 'K')),
  Data_Przyjecia Date NOT NULL,
  Stan_cywilny Varchar2(15 ),
  Data_Wypisu Date,
  Telefon Varchar2(20 ),
  email Varchar2(50 ),
  Id_Dom_Seniora Integer NOT NULL,
  Id_Pokoj Integer,
  Id_Adres Integer NOT NULL
)
/

-- Create indexes for table Seniorzy

CREATE INDEX IX_Ma ON Seniorzy (Id_Dom_Seniora)
/

CREATE INDEX IX_Zajmuje ON Seniorzy (Id_Pokoj)
/

CREATE INDEX IX_Relationship12 ON Seniorzy (Id_Adres)
/

-- Add keys for table Seniorzy

ALTER TABLE Seniorzy 
    ADD CONSTRAINT Unique_Identifier2 PRIMARY KEY (Id_Seniora)
/

-- Table Pracownicy

CREATE TABLE Pracownicy(
  Id_Pracownika Integer NOT NULL,
  Imie Varchar2(20 ) NOT NULL,
  Nazwisko Varchar2(30 ) NOT NULL,
  Data_Zatrudnienia Date NOT NULL,
  Data_Urodzenia Date NOT NULL,
  Id_Adres Integer NOT NULL,
  Id_Stanowkisko Integer NOT NULL,
  Id_Domu_Seniora Integer NOT NULL
)
/

-- Create indexes for table Pracownicy

CREATE INDEX IX_Relationship13 ON Pracownicy (Id_Adres)
/

CREATE INDEX IX_Relationship11 ON Pracownicy (Id_Stanowkisko)
/

CREATE INDEX IX_Relationship1 ON Pracownicy (Id_Domu_Seniora)
/

-- Add keys for table Pracownicy

ALTER TABLE Pracownicy 
    ADD CONSTRAINT Unique_Identifier4 PRIMARY KEY (Id_Pracownika)
/

-- Table Karty_Zdrowia

CREATE TABLE Karty_Zdrowia(
  Id_Karty_Zdrowia Integer NOT NULL,
  Stan_Zdrowia Varchar2(400 ) NOT NULL,
  Data_Pomiaru Date NOT NULL,
  Waga Number(5,2) NOT NULL,
  Wzrost Integer NOT NULL,
  Cisnienie_Gorne Integer NOT NULL,
  Cisnienie_Dolne Integer,
  Poziom_Cukru Number(5,2) NOT NULL,
  Poziom_Cholesterolu Number(5,2) NOT NULL,
  Id_Senior Integer NOT NULL
)
/

-- Create indexes for table Karty_Zdrowia

CREATE INDEX IX_Jest_Opisany_Przez ON Karty_Zdrowia (Id_Senior)
/

-- Add keys for table Karty_Zdrowia

ALTER TABLE Karty_Zdrowia 
    ADD CONSTRAINT Unique_Identifier5 PRIMARY KEY (Id_Karty_Zdrowia)
/

-- Table Krewni

CREATE TABLE Krewni(
  Id_Krewnego Integer NOT NULL,
  Imie Varchar2(20 ) NOT NULL,
  Nazwisko Varchar2(30 ) NOT NULL,
  Relacja Varchar2(12 ) NOT NULL
        CHECK (Relacja  IN ('syn','córka','żona','mąż','inny')),
  Zgoda_Na_Kontakt Char(1 ) NOT NULL,
  Telefon Varchar2(15 ),
  email Varchar2(50 ),
  Id_Adres Integer,
  Id_Seniora Integer NOT NULL
)
/

-- Create indexes for table Krewni

CREATE INDEX IX_Relationship6 ON Krewni (Id_Adres)
/

CREATE INDEX IX_Relationship3 ON Krewni (Id_Seniora)
/

-- Add keys for table Krewni

ALTER TABLE Krewni 
    ADD CONSTRAINT Unique_Identifier6 PRIMARY KEY (Id_Krewnego)
/

-- Table Pokoje

CREATE TABLE Pokoje(
  Id_Pokoju Integer NOT NULL,
  Numer_Pokoju Varchar2(10 ) NOT NULL,
  Pietro Integer NOT NULL,
  Liczba_Lozek Integer NOT NULL,
  Status_Pokoju Char(1 ) NOT NULL,
  Cena_Za_Dzien Number(10,2) NOT NULL,
  Opis Varchar2(400 ),
  Id_Dom_Seniora Integer NOT NULL
)
/

-- Create indexes for table Pokoje

CREATE INDEX IX_Zawiera ON Pokoje (Id_Dom_Seniora)
/

-- Add keys for table Pokoje

ALTER TABLE Pokoje 
    ADD CONSTRAINT Unique_Identifier7 PRIMARY KEY (Id_Pokoju)
/

-- Table Pracownicy_Seniorzy

CREATE TABLE Pracownicy_Seniorzy(
  Id_Pracownik Integer NOT NULL,
  Id_Seniora Integer NOT NULL,
  Data Date NOT NULL,
  Uwagi Varchar2(400 )
)
/

-- Table Adresy

CREATE TABLE Adresy(
  Id_Adresu Integer NOT NULL,
  Miasto Varchar2(20 ) NOT NULL,
  Ulica Varchar2(30 ) NOT NULL,
  Nr_lokalu Varchar2(5 ),
  Nr_budynku Varchar2(5 ) NOT NULL
)
/

-- Add keys for table Adresy

ALTER TABLE Adresy 
    ADD CONSTRAINT PK_Adresy PRIMARY KEY (Id_Adresu)
/

-- Table Stanowiska

CREATE TABLE Stanowiska(
  Id_Stanowiska Integer NOT NULL,
  Nazwa Varchar2(20 ) NOT NULL,
  Opis Varchar2(800 ) NOT NULL
)
/

-- Add keys for table Stanowiska

ALTER TABLE Stanowiska 
    ADD CONSTRAINT PK_Stanowiska PRIMARY KEY (Id_Stanowiska)
/

ALTER TABLE Stanowiska ADD CONSTRAINT Nazwa UNIQUE (Nazwa)
/

-- Table Wynagrodzenia

CREATE TABLE Wynagrodzenia(
  Id_Wynagrodzenia Integer NOT NULL,
  Data Date NOT NULL,
  Kwota Number(10,2) NOT NULL,
  Kwota_dod Number(10,2),
  Id_Pracownika Integer NOT NULL
)
/

-- Create indexes for table Wynagrodzenia

CREATE INDEX IX_Relationship2 ON Wynagrodzenia (Id_Pracownika)
/

-- Add keys for table Wynagrodzenia

ALTER TABLE Wynagrodzenia 
    ADD CONSTRAINT PK_Wynagrodzenia PRIMARY KEY (Id_Wynagrodzenia)
/

-- Table Czujniki_Bezpieczenstwa

CREATE TABLE Czujniki_Bezpieczenstwa(
  Id_Czujnika Integer NOT NULL,
  Typ Varchar2(20 ) DEFAULT ON NULL 0 NOT NULL,
  Id_Pokoju Integer NOT NULL,
  Ostatnia_Aktywacja Date
)
/

-- Create indexes for table Czujniki_Bezpieczenstwa

CREATE INDEX IX_Relationship8 ON Czujniki_Bezpieczenstwa (Id_Pokoju)
/

-- Add keys for table Czujniki_Bezpieczenstwa

ALTER TABLE Czujniki_Bezpieczenstwa 
    ADD CONSTRAINT PK_Czujniki_Bezpieczenstwa PRIMARY KEY (Id_Czujnika)
/

-- Table Sponsorzy

CREATE TABLE Sponsorzy(
  Id_Sponsora Integer NOT NULL,
  Nazwa Varchar2(20 ) NOT NULL,
  Kwota_Miesiecznego_Wsparcia Number(10,2) DEFAULT ON NULL 0
)
/

-- Add keys for table Sponsorzy

ALTER TABLE Sponsorzy ADD CONSTRAINT PK_Sponsorzy PRIMARY KEY (Id_Sponsora)
/


-- Create foreign keys (relationships) section ------------------------------------------------- 

ALTER TABLE Pokoje 
    ADD CONSTRAINT Dom_Seniora_Zawiera_Pokoje 
        FOREIGN KEY (Id_Dom_Seniora) REFERENCES Domy_Seniora (Id_Domu_Seniora)
/



ALTER TABLE Seniorzy  
    ADD CONSTRAINT Gosci_Seniorow 
        FOREIGN KEY (Id_Dom_Seniora) REFERENCES Domy_Seniora (Id_Domu_Seniora)
/



ALTER TABLE Seniorzy 
    ADD CONSTRAINT Senior_Zajmuje_Pokoj 
        FOREIGN KEY (Id_Pokoj) REFERENCES Pokoje (Id_Pokoju)
/



ALTER TABLE Karty_Zdrowia 
    ADD CONSTRAINT Senior_Jest_Opisany_Przez_Zdrowie 
        FOREIGN KEY (Id_Senior) REFERENCES Seniorzy (Id_Seniora)
/



ALTER TABLE Seniorzy 
    ADD CONSTRAINT Senior_Ma_Adres 
        FOREIGN KEY (Id_Adres) REFERENCES Adresy (Id_Adresu)
/



ALTER TABLE Pracownicy 
    ADD CONSTRAINT Pracownik_Ma_Adres 
        FOREIGN KEY (Id_Adres) REFERENCES Adresy (Id_Adresu)
/



ALTER TABLE Krewni 
    ADD CONSTRAINT Krewny_Ma_Adres 
        FOREIGN KEY (Id_Adres) REFERENCES Adresy (Id_Adresu)
/



ALTER TABLE Domy_Seniora 
    ADD CONSTRAINT Dom_Seniora_Ma_Adres 
        FOREIGN KEY (Id_Adres) REFERENCES Adresy (Id_Adresu)
/



ALTER TABLE Pracownicy 
    ADD CONSTRAINT Pracownik_Ma_Stanowisko 
        FOREIGN KEY (Id_Stanowkisko) REFERENCES Stanowiska (Id_Stanowiska)
/



ALTER TABLE Pracownicy 
    ADD CONSTRAINT Zatrudnia 
        FOREIGN KEY (Id_Domu_Seniora) REFERENCES Domy_Seniora (Id_Domu_Seniora)
/



ALTER TABLE Wynagrodzenia 
    ADD CONSTRAINT Otrzymuje 
        FOREIGN KEY (Id_Pracownika) REFERENCES Pracownicy (Id_Pracownika)
/



ALTER TABLE Krewni 
    ADD CONSTRAINT Senior_Jest_Powiazany_Z_Krewnym 
        FOREIGN KEY (Id_Seniora) REFERENCES Seniorzy (Id_Seniora)
/



ALTER TABLE Pracownicy_Seniorzy 
    ADD CONSTRAINT Pracownicy_opiekuja_sie_seniorami 
        FOREIGN KEY (Id_Seniora) REFERENCES Seniorzy (Id_Seniora)
/



ALTER TABLE Domy_Seniora 
    ADD CONSTRAINT Dom_seniora_ma_sponsora 
        FOREIGN KEY (Id_Sponsora) REFERENCES Sponsorzy (Id_Sponsora)
/



ALTER TABLE Czujniki_Bezpieczenstwa 
    ADD CONSTRAINT Pokoj_ma_zamontowany_czujnik 
        FOREIGN KEY (Id_Pokoju) REFERENCES Pokoje (Id_Pokoju)
/





