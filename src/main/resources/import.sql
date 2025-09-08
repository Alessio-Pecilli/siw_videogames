-- ========================
-- Stati
-- ========================
INSERT INTO stato (id, descrizione) VALUES (nextval('stato_seq'), 'Disponibile');
INSERT INTO stato (id, descrizione) VALUES (nextval('stato_seq'), 'Beta');
INSERT INTO stato (id, descrizione) VALUES (nextval('stato_seq'), 'In Arrivo');



-- ========================
-- Categorie
-- ========================
INSERT INTO categoria (id, nome) VALUES (nextval('categoria_seq'), 'Avventura');
INSERT INTO categoria (id, nome) VALUES (nextval('categoria_seq'), 'Horror');
INSERT INTO categoria (id, nome) VALUES (nextval('categoria_seq'), 'RPG');
INSERT INTO categoria (id, nome) VALUES (nextval('categoria_seq'), 'Sparatutto');
INSERT INTO categoria (id, nome) VALUES (nextval('categoria_seq'), 'Platform');
INSERT INTO categoria (id, nome) VALUES (nextval('categoria_seq'), 'Simulazione');
INSERT INTO categoria (id, nome) VALUES (nextval('categoria_seq'), 'Strategia');
INSERT INTO categoria (id, nome) VALUES (nextval('categoria_seq'), 'Sportivo');

-- ========================
-- Videogiochi
-- ========================
INSERT INTO videogioco ( id, titolo, descrizione, stato_id, url_immagine) VALUES ( nextval('videogioco_id_seq'), 'Pokémon: Surging Sparks', 'Un nuovo capitolo nella saga Pokémon, ambientato in una regione elettrizzante dove nuove creature, antiche leggende e battaglie strategiche ti attendono. Scopri segreti nascosti, affronta rivali e cattura ogni Pokémon in un’avventura mozzafiato.', (SELECT s.id FROM stato AS s WHERE s.descrizione = 'Beta'), 'https://sm.ign.com/ign_it/screenshot/default/pokemon-surging-sparks-hero_enum.jpg');
INSERT INTO videogioco ( id, titolo, descrizione, stato_id, url_immagine) VALUES ( nextval('videogioco_id_seq'), 'Hollow Knight', 'Un survival horror ambientato in un manicomio abbandonato dove il confine tra follia e realtà si fa sempre più labile. Atmosfere cupe, enigmi psicologici e presenze oscure ti accompagneranno in un viaggio da brividi.', (SELECT s.id FROM stato AS s WHERE s.descrizione = 'In Arrivo'), 'https://multiplayer.net-cdn.it/thumbs/images/2025/04/19/hollow-knight-speciale-neoclassici-apertura_jpg_1600x900_crop_q85.jpg');
INSERT INTO videogioco ( id, titolo, descrizione, stato_id, url_immagine) VALUES ( nextval('videogioco_id_seq'), 'The Legend of Zelda: Breath of the Wild', 'Link deve risvegliarsi per sconfiggere Calamity Ganon e salvare Hyrule. Esplora un mondo aperto, completa missioni e combatti nemici in questa epica avventura.', (SELECT s.id FROM stato AS s WHERE s.descrizione = 'Disponibile'), 'https://wallpapercave.com/wp/wp2386759.jpg');
INSERT INTO videogioco ( id, titolo, descrizione, stato_id, url_immagine) VALUES ( nextval('videogioco_id_seq'), 'Elden Ring Nightreign', 'ELDEN RING NIGHTREIGN è un avventura standalone ambientata nell universo di ELDEN RING, sviluppata da FromSoftware per offrire ai giocatori una nuova esperienza di gioco reinventandone le caratteristiche chiave.', (SELECT s.id FROM stato AS s WHERE s.descrizione = 'Disponibile'), 'https://sm.ign.com/ign_it/cover/e/elden-ring/elden-ring-nightreign_6nc8.jpg');
INSERT INTO videogioco ( id, titolo, descrizione, stato_id, url_immagine) VALUES ( nextval('videogioco_id_seq'), 'God Of War: Ragnarok', 'Avventurati in un viaggio epico ed emozionante: accompagna Kratos e Atreus nella loro lotta all’insegna del resistere e del lasciarsi andare.', (SELECT s.id FROM stato AS s WHERE s.descrizione = 'Beta'), 'https://multiplayer.net-cdn.it/thumbs/images/2022/08/30/gowcover_jpg_1600x900_crop_q85.jpg');


-- ========================
-- Join Videogioco-Categoria
-- ========================
INSERT INTO videogioco_categoria (videogioco_id,categoria_id) VALUES ((SELECT v.id FROM videogioco AS v WHERE v.titolo = 'Pokémon: Surging Sparks'), (SELECT c.id FROM categoria AS c WHERE c.nome = 'Avventura'));
INSERT INTO videogioco_categoria (videogioco_id,categoria_id) VALUES ((SELECT v.id FROM videogioco AS v WHERE v.titolo = 'Hollow Knight'), (SELECT c.id FROM categoria AS c WHERE c.nome = 'Avventura'));
INSERT INTO videogioco_categoria (videogioco_id,categoria_id) VALUES ((SELECT v.id FROM videogioco AS v WHERE v.titolo = 'Hollow Knight'), (SELECT c.id FROM categoria AS c WHERE c.nome = 'Horror'));
INSERT INTO videogioco_categoria (videogioco_id,categoria_id) VALUES ((SELECT v.id FROM videogioco AS v WHERE v.titolo = 'The Legend of Zelda: Breath of the Wild'), (SELECT c.id FROM categoria AS c WHERE c.nome = 'RPG'));
INSERT INTO videogioco_categoria (videogioco_id,categoria_id) VALUES ((SELECT v.id FROM videogioco AS v WHERE v.titolo = 'God Of War: Ragnarok'), (SELECT c.id FROM categoria AS c WHERE c.nome = 'Avventura'));
INSERT INTO videogioco_categoria (videogioco_id,categoria_id) VALUES ((SELECT v.id FROM videogioco AS v WHERE v.titolo = 'God Of War: Ragnarok'), (SELECT c.id FROM categoria AS c WHERE c.nome = 'Horror'));
INSERT INTO videogioco_categoria (videogioco_id,categoria_id) VALUES ((SELECT v.id FROM videogioco AS v WHERE v.titolo = 'Elden Ring Nightreign'), (SELECT c.id FROM categoria AS c WHERE c.nome = 'Strategia'));
INSERT INTO videogioco_categoria (videogioco_id,categoria_id) VALUES ((SELECT v.id FROM videogioco AS v WHERE v.titolo = 'Elden Ring Nightreign'), (SELECT c.id FROM categoria AS c WHERE c.nome = 'RPG'));



INSERT INTO utente (id, nome, cognome, email) VALUES (nextval('utente_seq'), 'Alessio', 'Scarpa', 'alessio.scarpa@example.com');
INSERT INTO utente (id, nome, cognome, email) VALUES (nextval('utente_seq'), 'Alessio', 'Scarpa', 'alessio@example.com');
INSERT INTO utente (id, nome, cognome, email) VALUES (nextval('utente_seq'), 'Mario', 'Rossi', 'mario.rossi@example.com');
INSERT INTO utente (id, nome, cognome, email) VALUES (nextval('utente_seq'), 'Lucia', 'Verdi', 'lucia.verdi@example.com');

INSERT INTO credentials(id, utente_id, password, role, username) VALUES (nextval('credentials_seq'),(SELECT u.id FROM utente as u WHERE u.email='alessio.scarpa@example.com'),'$2a$10$Nz4769bR1Iutd8perNFRPuB9xf5CbEMqRd02hg8twA.6jqE1Gq1Iy', 'ADMIN','administrator');

INSERT INTO credentials(id, utente_id, password, role, username) VALUES (nextval('credentials_seq'),(SELECT u.id FROM utente as u WHERE u.email='alessio@example.com'),'$2a$10$Nz4769bR1Iutd8perNFRPuB9xf5CbEMqRd02hg8twA.6jqE1Gq1Iy', 'DEFAULT','ale003');

INSERT INTO utente (id, nome, cognome, email) VALUES (nextval('utente_seq'), 'Giulia', 'Bianchi', 'giulia.bianchi@example.com');
INSERT INTO credentials(id, utente_id, password, role, username) VALUES (nextval('credentials_seq'), (SELECT id FROM utente WHERE email='giulia.bianchi@example.com'), '$2a$10$Nz4769bR1Iutd8perNFRPuB9xf5CbEMqRd02hg8twA.6jqE1Gq1Iy', 'DEFAULT', 'giulia001');

INSERT INTO utente (id, nome, cognome, email) VALUES (nextval('utente_seq'), 'Marco', 'Neri', 'marco.neri@example.com');
INSERT INTO credentials(id, utente_id, password, role, username) VALUES (nextval('credentials_seq'), (SELECT id FROM utente WHERE email='marco.neri@example.com'), '$2a$10$Nz4769bR1Iutd8perNFRPuB9xf5CbEMqRd02hg8twA.6jqE1Gq1Iy', 'DEFAULT', 'marco001');

INSERT INTO messaggio (id, data, descrizione, ora, utente_id, videogioco_id) VALUES (nextval('messaggio_id_seq'), '2025-04-20', 'Bellissimo gioco, lo sto ancora esplorando!', '16:00:00',(SELECT u.id FROM utente as u WHERE u.nome='Mario'), (SELECT id FROM videogioco WHERE titolo = 'Pokémon: Surging Sparks'));
INSERT INTO messaggio (id, data, descrizione, ora, utente_id, videogioco_id) VALUES (nextval('messaggio_id_seq'), '2025-04-20', 'Troppo difficile ma affascinante.', '16:15:00',( SELECT u.id FROM utente as u WHERE u.nome='Lucia'), (SELECT id FROM videogioco WHERE titolo = 'Pokémon: Surging Sparks'));

INSERT INTO recensione (id, data, feedback, ora, voto, utente_id, videogioco_id) VALUES (nextval('recensione_id_seq'), '2025-04-20', 'Gioco molto ben fatto, anche se con qualche bug.', '16:45:00', 4, (SELECT u.id FROM utente AS u WHERE u.nome = 'Lucia'), (SELECT id FROM videogioco WHERE titolo = 'Pokémon: Surging Sparks'));

INSERT INTO messaggio (id, data, descrizione, ora, utente_id, videogioco_id) VALUES (nextval('messaggio_id_seq'), '2025-04-25', 'Grafica incredibile!', '14:41:00', (SELECT id FROM utente WHERE email='giulia.bianchi@example.com'), (SELECT id FROM videogioco WHERE titolo = 'Pokémon: Surging Sparks'));
INSERT INTO recensione (id, data, feedback, ora, voto, utente_id, videogioco_id) VALUES (nextval('recensione_id_seq'), '2025-04-25', 'Trama debole ma gameplay buono.', '14:41:00', 4, (SELECT id FROM utente WHERE email='giulia.bianchi@example.com'), (SELECT id FROM videogioco WHERE titolo = 'Pokémon: Surging Sparks'));

INSERT INTO messaggio (id, data, descrizione, ora, utente_id, videogioco_id) VALUES (nextval('messaggio_id_seq'), '2025-04-25', 'Perfetto per passare il tempo.', '14:55:00', (SELECT id FROM utente WHERE email='marco.neri@example.com'), (SELECT id FROM videogioco WHERE titolo = 'Pokémon: Surging Sparks'));
INSERT INTO recensione (id, data, feedback, ora, voto, utente_id, videogioco_id) VALUES (nextval('recensione_id_seq'), '2025-04-25', 'Esperienza molto immersiva.', '14:55:00', 5, (SELECT id FROM utente WHERE email='marco.neri@example.com'), (SELECT id FROM videogioco WHERE titolo = 'Pokémon: Surging Sparks'));

INSERT INTO messaggio (id, data, descrizione, ora, utente_id, videogioco_id) VALUES (nextval('messaggio_id_seq'), '2025-04-25', 'Coinvolgente e ben fatto.', '15:06:00', (SELECT id FROM utente WHERE email='giulia.bianchi@example.com'), (SELECT id FROM videogioco WHERE titolo = 'Hollow Knight'));
INSERT INTO recensione (id, data, feedback, ora, voto, utente_id, videogioco_id) VALUES (nextval('recensione_id_seq'), '2025-04-25', 'Troppi bug, da sistemare.', '15:06:00', 3, (SELECT id FROM utente WHERE email='giulia.bianchi@example.com'), (SELECT id FROM videogioco WHERE titolo = 'Hollow Knight'));

INSERT INTO messaggio (id, data, descrizione, ora, utente_id, videogioco_id) VALUES (nextval('messaggio_id_seq'), '2025-04-25', 'Grafica incredibile!', '15:29:00', (SELECT id FROM utente WHERE email='marco.neri@example.com'), (SELECT id FROM videogioco WHERE titolo = 'Hollow Knight'));
INSERT INTO recensione (id, data, feedback, ora, voto, utente_id, videogioco_id) VALUES (nextval('recensione_id_seq'), '2025-04-25', 'Musiche epiche, mi è piaciuto un sacco.', '15:29:00', 4, (SELECT id FROM utente WHERE email='marco.neri@example.com'), (SELECT id FROM videogioco WHERE titolo = 'Hollow Knight'));

INSERT INTO messaggio (id, data, descrizione, ora, utente_id, videogioco_id) VALUES (nextval('messaggio_id_seq'), '2025-04-25', 'Soundtrack da brividi!', '16:02:00', (SELECT id FROM utente WHERE email='mario.rossi@example.com'), (SELECT id FROM videogioco WHERE titolo = 'The Legend of Zelda: Breath of the Wild'));
INSERT INTO recensione (id, data, feedback, ora, voto, utente_id, videogioco_id) VALUES (nextval('recensione_id_seq'), '2025-04-25', 'Level design fantastico.', '16:02:00', 3, (SELECT id FROM utente WHERE email='mario.rossi@example.com'), (SELECT id FROM videogioco WHERE titolo = 'The Legend of Zelda: Breath of the Wild'));

INSERT INTO messaggio (id, data, descrizione, ora, utente_id, videogioco_id) VALUES (nextval('messaggio_id_seq'), '2025-04-25', 'Alcune parti ripetitive ma nel complesso ottimo.', '16:22:00', (SELECT id FROM utente WHERE email='lucia.verdi@example.com'), (SELECT id FROM videogioco WHERE titolo = 'The Legend of Zelda: Breath of the Wild'));
INSERT INTO recensione (id, data, feedback, ora, voto, utente_id, videogioco_id) VALUES (nextval('recensione_id_seq'), '2025-04-25', 'Narrazione ben costruita.', '16:22:00', 4, (SELECT id FROM utente WHERE email='lucia.verdi@example.com'), (SELECT id FROM videogioco WHERE titolo = 'The Legend of Zelda: Breath of the Wild'));

INSERT INTO messaggio (id, data, descrizione, ora, utente_id, videogioco_id) VALUES (nextval('messaggio_id_seq'), '2025-04-25', 'Ottimo sistema di combattimento.', '16:42:00', (SELECT id FROM utente WHERE email='alessio.scarpa@example.com'), (SELECT id FROM videogioco WHERE titolo = 'The Legend of Zelda: Breath of the Wild'));
INSERT INTO recensione (id, data, feedback, ora, voto, utente_id, videogioco_id) VALUES (nextval('recensione_id_seq'), '2025-04-25', 'Esperienza indimenticabile.', '16:42:00', 4, (SELECT id FROM utente WHERE email='alessio.scarpa@example.com'), (SELECT id FROM videogioco WHERE titolo = 'The Legend of Zelda: Breath of the Wild'));

INSERT INTO messaggio (id, data, descrizione, ora, utente_id, videogioco_id) VALUES (nextval('messaggio_id_seq'), '2025-04-25', 'Soundtrack da brividi!', '17:02:00', (SELECT id FROM utente WHERE email='alessio@example.com'), (SELECT id FROM videogioco WHERE titolo = 'The Legend of Zelda: Breath of the Wild'));
INSERT INTO recensione (id, data, feedback, ora, voto, utente_id, videogioco_id) VALUES (nextval('recensione_id_seq'), '2025-04-25', 'Narrazione ben costruita.', '17:02:00', 4, (SELECT id FROM utente WHERE email='alessio@example.com'), (SELECT id FROM videogioco WHERE titolo = 'The Legend of Zelda: Breath of the Wild'));

INSERT INTO messaggio (id, data, descrizione, ora, utente_id, videogioco_id) VALUES (nextval('messaggio_id_seq'), '2025-04-25', 'Gameplay fluido e coinvolgente.', '16:20:00', (SELECT id FROM utente WHERE email='mario.rossi@example.com'), (SELECT id FROM videogioco WHERE titolo = 'Elden Ring Nightreign'));
INSERT INTO recensione (id, data, feedback, ora, voto, utente_id, videogioco_id) VALUES (nextval('recensione_id_seq'), '2025-04-25', 'Esperienza indimenticabile.', '16:20:00', 4, (SELECT id FROM utente WHERE email='mario.rossi@example.com'), (SELECT id FROM videogioco WHERE titolo = 'Elden Ring Nightreign'));

INSERT INTO messaggio (id, data, descrizione, ora, utente_id, videogioco_id) VALUES (nextval('messaggio_id_seq'), '2025-04-25', 'Alcune parti ripetitive ma nel complesso ottimo.', '16:40:00', (SELECT id FROM utente WHERE email='lucia.verdi@example.com'), (SELECT id FROM videogioco WHERE titolo = 'Elden Ring Nightreign'));
INSERT INTO recensione (id, data, feedback, ora, voto, utente_id, videogioco_id) VALUES (nextval('recensione_id_seq'), '2025-04-25', 'Narrazione ben costruita.', '16:40:00', 4, (SELECT id FROM utente WHERE email='lucia.verdi@example.com'), (SELECT id FROM videogioco WHERE titolo = 'Elden Ring Nightreign'));

INSERT INTO messaggio (id, data, descrizione, ora, utente_id, videogioco_id) VALUES (nextval('messaggio_id_seq'), '2025-04-25', 'Ottimo sistema di combattimento.', '17:00:00', (SELECT id FROM utente WHERE email='alessio.scarpa@example.com'), (SELECT id FROM videogioco WHERE titolo = 'Elden Ring Nightreign'));
INSERT INTO recensione (id, data, feedback, ora, voto, utente_id, videogioco_id) VALUES (nextval('recensione_id_seq'), '2025-04-25', 'Mi ha tenuto incollato ore!', '17:00:00', 5, (SELECT id FROM utente WHERE email='alessio.scarpa@example.com'), (SELECT id FROM videogioco WHERE titolo = 'Elden Ring Nightreign'));

INSERT INTO messaggio (id, data, descrizione, ora, utente_id, videogioco_id) VALUES (nextval('messaggio_id_seq'), '2025-04-25', 'Gameplay fluido e coinvolgente.', '17:20:00', (SELECT id FROM utente WHERE email='alessio@example.com'), (SELECT id FROM videogioco WHERE titolo = 'Elden Ring Nightreign'));
INSERT INTO recensione (id, data, feedback, ora, voto, utente_id, videogioco_id) VALUES (nextval('recensione_id_seq'), '2025-04-25', 'Level design fantastico.', '17:20:00', 5, (SELECT id FROM utente WHERE email='alessio@example.com'), (SELECT id FROM videogioco WHERE titolo = 'Elden Ring Nightreign'));

INSERT INTO messaggio (id, data, descrizione, ora, utente_id, videogioco_id) VALUES (nextval('messaggio_id_seq'), '2025-04-25', 'Soundtrack da brividi!', '16:38:00', (SELECT id FROM utente WHERE email='mario.rossi@example.com'), (SELECT id FROM videogioco WHERE titolo = 'God Of War: Ragnarok'));
INSERT INTO recensione (id, data, feedback, ora, voto, utente_id, videogioco_id) VALUES (nextval('recensione_id_seq'), '2025-04-25', 'Level design fantastico.', '16:38:00', 4, (SELECT id FROM utente WHERE email='mario.rossi@example.com'), (SELECT id FROM videogioco WHERE titolo = 'God Of War: Ragnarok'));

INSERT INTO messaggio (id, data, descrizione, ora, utente_id, videogioco_id) VALUES (nextval('messaggio_id_seq'), '2025-04-25', 'Alcune parti ripetitive ma nel complesso ottimo.', '16:58:00', (SELECT id FROM utente WHERE email='lucia.verdi@example.com'), (SELECT id FROM videogioco WHERE titolo = 'God Of War: Ragnarok'));
INSERT INTO recensione (id, data, feedback, ora, voto, utente_id, videogioco_id) VALUES (nextval('recensione_id_seq'), '2025-04-25', 'Narrazione ben costruita.', '16:58:00', 3, (SELECT id FROM utente WHERE email='lucia.verdi@example.com'), (SELECT id FROM videogioco WHERE titolo = 'God Of War: Ragnarok'));

INSERT INTO messaggio (id, data, descrizione, ora, utente_id, videogioco_id) VALUES (nextval('messaggio_id_seq'), '2025-04-25', 'Ottimo sistema di combattimento.', '17:18:00', (SELECT id FROM utente WHERE email='alessio.scarpa@example.com'), (SELECT id FROM videogioco WHERE titolo = 'God Of War: Ragnarok'));
INSERT INTO recensione (id, data, feedback, ora, voto, utente_id, videogioco_id) VALUES (nextval('recensione_id_seq'), '2025-04-25', 'Mi ha tenuto incollato ore!', '17:18:00', 5, (SELECT id FROM utente WHERE email='alessio.scarpa@example.com'), (SELECT id FROM videogioco WHERE titolo = 'God Of War: Ragnarok'));

INSERT INTO messaggio (id, data, descrizione, ora, utente_id, videogioco_id) VALUES (nextval('messaggio_id_seq'), '2025-04-25', 'Soundtrack da brividi!', '17:38:00', (SELECT id FROM utente WHERE email='alessio@example.com'), (SELECT id FROM videogioco WHERE titolo = 'God Of War: Ragnarok'));
INSERT INTO recensione (id, data, feedback, ora, voto, utente_id, videogioco_id) VALUES (nextval('recensione_id_seq'), '2025-04-25', 'Esperienza indimenticabile.', '17:38:00', 4, (SELECT id FROM utente WHERE email='alessio@example.com'), (SELECT id FROM videogioco WHERE titolo = 'God Of War: Ragnarok'));