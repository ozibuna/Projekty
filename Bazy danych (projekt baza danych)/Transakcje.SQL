
--Transakcja obejmuje wprowadzenie danych nowego seniora, 
--jego adresu oraz opcjonalne przypisanie do pokoju w domu seniora.
BEGIN TRANSACTION;
-- Dodanie nowego seniora
INSERT INTO Seniorzy
VALUES (1, 'Adrian', 'Lis', TO_DATE('1955-05-12', 'YYYY-MM-DD'), 'M',
 TO_DATE('2024-11-01', 'YYYY-MM-DD'), 1, 101);
-- Dodanie adresu seniora
INSERT INTO Adresy
VALUES (101, 'Warszawa', 'Pokorna', '2', '5');
-- Przypisanie seniora do pokoju
UPDATE Pokoje
SET Status_Pokoju = 'Z'
WHERE Id_Pokoju = 201;
COMMIT;

--Sprawdzenie po pierwszej transakcji
SELECT * FROM Seniorzy WHERE Id_Seniora = 1;
SELECT * FROM Adresy WHERE Id_Adresu = 101;
SELECT Status_Pokoju FROM Pokoje WHERE Id_Pokoju = 201;


--Transakcja aktualizuje dane zdrowotne seniora po nowym badaniu.
BEGIN TRANSACTION;
-- Dodanie nowych pomiarów do karty zdrowia
INSERT INTO Karty_Zdrowia
VALUES (301, 'Stabilny', TO_DATE('2024-11-20', 'YYYY-MM-DD'), 70.5, 170, 120, 80, 5.2, 190.0, 1);
-- Weryfikacja poprawności danych (np. czy senior istnieje)
SELECT * FROM Seniorzy WHERE Id_Seniora = 1;
COMMIT;

-- Sprawdzenie po drugiej transakcji
SELECT * FROM Karty_Zdrowia WHERE Id_Karty = 301;
SELECT * FROM Seniorzy WHERE Id_Seniora = 1;


--Transakcja zmienia status seniora w systemie oraz uwalnia zajmowany pokój.
BEGIN TRANSACTION;
-- Aktualizacja danych seniora
UPDATE Seniorzy
SET Data_Wypisu = TO_DATE('2024-11-22', 'YYYY-MM-DD')
WHERE Id_Seniora = 1;
-- Zmiana statusu pokoju na wolny
UPDATE Pokoje
SET Status_Pokoju = 'W'
WHERE Id_Pokoju = (SELECT Id_Pokoj FROM Seniorzy WHERE Id_Seniora = 1);
COMMIT;

-- Sprawdzenie po trzeciej transakcji
SELECT * FROM Seniorzy WHERE Id_Seniora = 1;
SELECT Status_Pokoju FROM Pokoje WHERE Id_Pokoju = 101;