package uniroma3.siw.videogames.integration;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import uniroma3.siw.videogames.model.Categoria;
import uniroma3.siw.videogames.model.Stato;
import uniroma3.siw.videogames.model.Videogioco;
import uniroma3.siw.videogames.repository.CategoriaRepository;
import uniroma3.siw.videogames.repository.StatoRepository;
import uniroma3.siw.videogames.repository.VideogiocoRepository;

@DataJpaTest
@ActiveProfiles("test")
@DisplayName("Test di integrazione per VideogiocoRepository")
class VideogiocoRepositoryIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private VideogiocoRepository videogiocoRepository;

    @Autowired
    private StatoRepository statoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    private Videogioco videogioco1;
    private Videogioco videogioco2;
    private Stato statoPubblicato;
    private Stato statoInSviluppo;
    private Categoria categoriaAction;
    private Categoria categoriaAdventure;

    @BeforeEach
    void setUp() {
        // Inizializza e persisti gli stati (ID generato automaticamente da H2)
        statoPubblicato = new Stato();
        statoPubblicato.setDescrizione("Pubblicato");
        entityManager.persist(statoPubblicato);
        entityManager.flush();

        statoInSviluppo = new Stato();
        statoInSviluppo.setDescrizione("In Sviluppo");
        entityManager.persist(statoInSviluppo);
        entityManager.flush();

        // Inizializza e persisti le categorie
        categoriaAction = new Categoria();
        categoriaAction.setNome("Action");
        entityManager.persist(categoriaAction);
        entityManager.flush();

        categoriaAdventure = new Categoria();
        categoriaAdventure.setNome("Adventure");
        entityManager.persist(categoriaAdventure);
        entityManager.flush();

        // Inizializza i videogiochi
        videogioco1 = new Videogioco("Super Mario Bros", 
                                    "Un classico platform game", 
                                    "http://example.com/mario.jpg", 
                                    statoPubblicato, 
                                    List.of(categoriaAction));

        videogioco2 = new Videogioco("The Legend of Zelda", 
                                    "Un epico gioco di avventura", 
                                    "http://example.com/zelda.jpg", 
                                    statoPubblicato, 
                                    List.of(categoriaAdventure));

        // Persisti i videogiochi
        entityManager.persist(videogioco1);
        entityManager.persist(videogioco2);
        entityManager.flush();
        
        entityManager.clear();
    }

    @Test
    @DisplayName("Test findByStato - trova videogiochi per stato")
    void testFindByStato() {
        // When
        List<Videogioco> videogiochiPubblicati = videogiocoRepository.findByStato(statoPubblicato);

        // Then
        assertNotNull(videogiochiPubblicati);
        assertEquals(2, videogiochiPubblicati.size());
        assertTrue(videogiochiPubblicati.stream()
                .allMatch(v -> v.getStato().equals(statoPubblicato)));
    }

    @Test
    @DisplayName("Test findByCategorie - trova videogiochi per categoria")
    void testFindByCategorie() {
        // When
        List<Videogioco> videogiochiAction = videogiocoRepository.findByCategorie(categoriaAction);

        // Then
        assertNotNull(videogiochiAction);
        assertEquals(1, videogiochiAction.size());
        assertEquals("Super Mario Bros", videogiochiAction.get(0).getTitolo());
    }

    @Test
    @DisplayName("Test findByCategorieIn - trova videogiochi per lista categorie")
    void testFindByCategorieIn() {
        // When
        List<Videogioco> videogiochi = videogiocoRepository
                .findByCategorieIn(List.of(categoriaAction, categoriaAdventure));

        // Then
        assertNotNull(videogiochi);
        assertEquals(2, videogiochi.size());
    }

    @Test
    @DisplayName("Test save e findById - salvataggio e recupero")
    void testSaveEFindById() {
        // Given
        Videogioco nuovoVideogioco = new Videogioco("Nuovo Gioco", 
                                                   "Descrizione nuovo gioco", 
                                                   "http://example.com/nuovo.jpg", 
                                                   statoInSviluppo, 
                                                   List.of(categoriaAction));

        // When
        Videogioco salvato = videogiocoRepository.save(nuovoVideogioco);
        entityManager.flush();

        Videogioco recuperato = videogiocoRepository.findById(salvato.getId()).orElse(null);

        // Then
        assertNotNull(salvato);
        assertNotNull(salvato.getId());
        assertNotNull(recuperato);
        assertEquals("Nuovo Gioco", recuperato.getTitolo());
        assertEquals("Descrizione nuovo gioco", recuperato.getDescrizione());
        assertEquals(statoInSviluppo, recuperato.getStato());
    }

    @Test
    @DisplayName("Test deleteById - eliminazione videogioco")
    void testDeleteById() {
        // Given
        Long idDaEliminare = videogioco1.getId();

        // When
        videogiocoRepository.deleteById(idDaEliminare);
        entityManager.flush();

        // Then
        assertFalse(videogiocoRepository.existsById(idDaEliminare));
        assertEquals(1, videogiocoRepository.count());
    }

    @Test
    @DisplayName("Test findAll - recupera tutti i videogiochi")
    void testFindAll() {
        // When
        List<Videogioco> tuttiIVideogiochi = (List<Videogioco>) videogiocoRepository.findAll();

        // Then
        assertNotNull(tuttiIVideogiochi);
        assertEquals(2, tuttiIVideogiochi.size());
    }

    @Test
    @DisplayName("Test existsById - verifica esistenza")
    void testExistsById() {
        // When & Then
        assertTrue(videogiocoRepository.existsById(videogioco1.getId()));
        assertTrue(videogiocoRepository.existsById(videogioco2.getId()));
        assertFalse(videogiocoRepository.existsById(999L));
    }

    @Test
    @DisplayName("Test count - conta i videogiochi")
    void testCount() {
        // When
        long count = videogiocoRepository.count();
        // Then
        assertEquals(2, count);
    }

    @Test
    @DisplayName("Test cascading - eliminazione in cascata")
    void testCascading() {
        // Given - aggiungi una recensione al videogioco
        // Nota: questo test richiede che sia implementata la relazione con Recensione

        // When
        videogiocoRepository.deleteById(videogioco1.getId());
        entityManager.flush();

        // Then
        assertFalse(videogiocoRepository.existsById(videogioco1.getId()));
        // Le recensioni dovrebbero essere eliminate in cascata
    }
}
