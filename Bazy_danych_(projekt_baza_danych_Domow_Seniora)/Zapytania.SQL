--Dodanie domu seniora
INSERT INTO Domy_Seniora 
VALUES (1, 'Dom Spokojnej Jesieni', DATE '2005-04-15', 30, 50, 
        'Nowoczesny osrodek dla seniorow.', 'T', 2500.00, 1, NULL);

--Dodanie seniora
INSERT INTO Seniorzy 
VALUES (3, 'Katarzyna', 'Dobinska', DATE '1948-05-12', '48051212345', 
        'K', DATE '2024-05-10', 'wdowa', '123789456', 'katarzyna.dobinska@gmail.com', 1, 2, 3);

--Dodanie pracownika
INSERT INTO Pracownicy 
VALUES (3, 'Robert', 'Nowicki', DATE '2022-03-10', DATE '1980-04-15', 3, 1, 1);

--Dodanie adresu
INSERT INTO Adresy 
VALUES (1, 'Krakow', 'Jesionowa', '5', '2');

INSERT INTO Adresy 
VALUES (2, 'Lodz', 'Debowa', '8', '3');

--Dodanie pokoju
INSERT INTO Pokoje 
VALUES (1, '101', 1, 2, 'W', 150.00, 'Pokoj dwuosobowy z balkonem.', 1);

--Aktualizacja danych

-- Zmiana numeru telefonu seniora o Id_Seniora rownym 1
UPDATE Seniorzy
SET Telefon = '789456123'
WHERE Id_Seniora = 1;

-- Aktualizacja sredniej ceny mieszkania w domu seniora o Id_Domu_Seniora rownym 1
UPDATE Domy_Seniora
SET Srednia_Cena_Mieszkania = 2700.00
WHERE Id_Domu_Seniora = 1;

--Usuwanie danych

-- Usuniecie seniora o Id_Seniora rownym 1
DELETE FROM Seniorzy
WHERE Id_Seniora = 1;

-- Usuniecie czujnika bezpieczenstwa o Id_Czujnika rownym 2
DELETE FROM Czujniki_Bezpieczenstwa
WHERE Id_Czujnika = 2;

--Pobieranie danych

-- Pobranie imienia, nazwiska, daty urodzenia i telefonu seniorow 
--z domu seniora o nazwie "Dom Seniora Sloneczny"
SELECT S.Imie, S.Nazwisko, S.Data_Urodzenia, S.Telefon
SELECT S.Imie, S.Nazwisko, S.Data_Urodzenia, S.Telefon
FROM Seniorzy S
JOIN Domy_Seniora D ON S.Id_Dom_Seniora = D.Id_Domu_Seniora
WHERE D.Nazwa = 'Dom Seniora Sloneczny';

-- Pobranie numeru pokoju, pietra, liczby lozek 
--oraz statusu zajetosci pokoi w domu seniora o Id_Dom_Seniora rownym 1
SELECT P.Numer_Pokoju, P.Pietro, P.Liczba_Lozek, 
       CASE 
           WHEN S.Id_Pokoj IS NOT NULL THEN 'Zajety'
           ELSE 'Wolny'
       END AS Status
FROM Pokoje P
LEFT JOIN Seniorzy S ON P.Id_Pokoju = S.Id_Pokoj
WHERE P.Id_Dom_Seniora = 1;

-- Pobranie danych o imieniu, nazwisku, stanie zdrowia, wadze, 
--wzroscie i dacie pomiaru seniorow z domu seniora o Id_Dom_Seniora rownym 1
SELECT S.Imie, S.Nazwisko, K.Stan_Zdrowia, K.Waga, K.Wzrost, K.Data_Pomiaru
FROM Seniorzy S
JOIN Karty_Zdrowia K ON S.Id_Seniora = K.Id_Senior
WHERE S.Id_Dom_Seniora = 1;

-- Pobranie imienia, nazwiska pracownikow oraz nazwy ich stanowiska
SELECT P.Imie, P.Nazwisko, S.Nazwa AS Stanowisko
FROM Pracownicy P
JOIN Stanowiska S ON P.Id_Stanowkisko = S.Id_Stanowiska;

-- Pobranie imienia, nazwiska seniorow oraz ich wynikow cisnienia, 
--jesli cisnienie gorne > 140 lub dolne > 90
SELECT S.Imie, S.Nazwisko, K.Cisnienie_Gorne, K.Cisnienie_Dolne
FROM Seniorzy S
JOIN Karty_Zdrowia K ON S.Id_Seniora = K.Id_Senior
WHERE K.Cisnienie_Gorne > 140 OR K.Cisnienie_Dolne > 90;
