-- Dane początkowe dla bazy H2
INSERT INTO aktorzy (imie, nazwisko, rok_debiutu) VALUES
('Robert', 'De Niro', 1965),
('Marlon', 'Brando', 1950),
('Al', 'Pacino', 1969),
('Meryl', 'Streep', 1975),
('Leonardo', 'DiCaprio', 1991);

INSERT INTO filmy (tytul, rok_produkcji, rezyser, gatunek) VALUES
('Ojciec chrzestny', 1972, 'Francis Ford Coppola', 'Dramat'),
('Taksówkarz', 1976, 'Martin Scorsese', 'Dramat'),
('Ojciec chrzestny II', 1974, 'Francis Ford Coppola', 'Dramat'),
('Scarface', 1983, 'Brian De Palma', 'Dramat'),
('Titanic', 1997, 'James Cameron', 'Romans'),
('Incepcja', 2010, 'Christopher Nolan', 'Sci-Fi');

-- Relacje między aktorami a filmami
INSERT INTO aktorzy_filmy (aktor_id, film_id) VALUES
(1, 1), -- Robert De Niro w Ojcu chrzestnym
(1, 2), -- Robert De Niro w Taksówkarzu
(1, 3), -- Robert De Niro w Ojcu chrzestnym II
(2, 1), -- Marlon Brando w Ojcu chrzestnym
(2, 3), -- Marlon Brando w Ojcu chrzestnym II
(3, 1), -- Al Pacino w Ojcu chrzestnym
(3, 3), -- Al Pacino w Ojcu chrzestnym II
(3, 4), -- Al Pacino w Scarface
(5, 5), -- Leonardo DiCaprio w Titanicu
(5, 6); -- Leonardo DiCaprio w Incepcji