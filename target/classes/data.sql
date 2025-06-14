-- Dane początkowe dla bazy H2
INSERT INTO aktorzy (imie, nazwisko, rok_debiutu) VALUES
('Robert', 'Downey Jr.', 1983),
('Chris', 'Evans', 2000),
('Scarlett', 'Johansson', 1994),
('Chris', 'Hemsworth', 2002),
('Gal', 'Gadot', 2009),
('Henry', 'Cavill', 2001),
('Ezra', 'Miller', 2008),
('Emma', 'Watson', 2001),
('Daniel', 'Radcliffe', 1999),
('Rupert', 'Grint', 2001),
('Ian', 'McKellen', 1969),
('Elijah', 'Wood', 1989),
('Mark', 'Hamill', 1970),
('Carrie', 'Fisher', 1975),
('Hugh', 'Jackman', 1999),
('Robert', 'Englund', 1974),
('Arnold', 'Schwarzenegger', 1970),
('Carl', 'Weathers', 1975),
('Danny', 'Glover', 1979),
('Tobin', 'Bell', 1979),
('Sigourney', 'Weaver', 1976),
('Kane', 'Hodder', 1983),
('Gunnar', 'Hansen', 1974),
('Sylvester', 'Stallone', 1970),
('Peter', 'Weller', 1973),
('Skeet', 'Ulrich', 1996);

INSERT INTO filmy (tytul, rok_produkcji, rezyser, gatunek) VALUES
('Iron Man', 2008, 'Jon Favreau', 'akcja'),
('Avengers: Koniec gry', 2019, 'Anthony i Joe Russo', 'akcja'),
('Kapitan Ameryka: Pierwsze starcie', 2011, 'Joe Johnston', 'akcja'),
('Czarna Wdowa', 2021, 'Cate Shortland', 'akcja'),
('Thor', 2011, 'Kenneth Branagh', 'akcja'),
('Wonder Woman', 2017, 'Patty Jenkins', 'akcja'),
('Człowiek ze stali', 2013, 'Zack Snyder', 'akcja'),
('Flash', 2023, 'Andy Muschietti', 'akcja'),
('Harry Potter i Kamień Filozoficzny', 2001, 'Chris Columbus', 'fantasy'),
('Władca Pierścieni: Drużyna Pierścienia', 2001, 'Peter Jackson', 'fantasy'),
('Gwiezdne wojny: Nowa nadzieja', 1977, 'George Lucas', 'sci-fi'),
('Logan', 2017, 'James Mangold', 'akcja'),
('Koszmar z ulicy Wiązów', 1984, 'Wes Craven', 'horror'),
('Predator', 1987, 'John McTiernan', 'sci-fi'),
('Obcy – 8. pasażer Nostromo', 1979, 'Ridley Scott', 'sci-fi'),
('Piątek, trzynastego VII: Nowa krew', 1988, 'John Carl Buechler', 'horror'),
('Teksańska masakra piłą mechaniczną', 1974, 'Tobe Hooper', 'horror'),
('Piła', 2004, 'James Wan', 'horror'),
('Rambo: Pierwsza krew', 1982, 'Ted Kotcheff', 'akcja'),
('RoboCop', 1987, 'Paul Verhoeven', 'sci-fi'),
('Krzyk', 1996, 'Wes Craven', 'horror');

-- Relacje między aktorami a filmami
INSERT INTO aktorzy_filmy (aktor_id, film_id) VALUES
(1, 1), -- Downey Jr. - Iron Man
(1, 2), -- Downey Jr. - Avengers: Endgame
(2, 3), -- Evans - Kapitan Ameryka
(2, 2), -- Evans - Endgame
(3, 4), -- Scarlett - Black Widow
(3, 2), -- Scarlett - Endgame
(4, 5), -- Hemsworth - Thor
(4, 2), -- Hemsworth - Endgame
(5, 6), -- Gadot - Wonder Woman
(6, 7), -- Cavill - Man of Steel
(7, 8), -- Miller - Flash
(8, 9), -- Watson - Harry Potter
(9, 9), -- Radcliffe
(10, 9), -- Grint
(11, 10), -- McKellen - LOTR
(12, 10), -- Elijah Wood - LOTR
(13, 11), -- Hamill - Nowa nadzieja
(14, 11), -- Fisher
(15, 12), -- Jackman - Logan
(16, 13), -- Robert Englund
(17, 14), -- Schwarzenegger
(18, 14), -- Carl Weathers
(21, 15), -- Sigourney Weaver
(22, 16), -- Kane Hodder
(23, 17), -- Gunnar Hansen
(20, 18), -- Tobin Bell
(19, 20), -- Danny Glover
(24, 19), -- Sylvester Stallone
(25, 20), -- Peter Weller
(26, 21); -- Skeet Ulrich