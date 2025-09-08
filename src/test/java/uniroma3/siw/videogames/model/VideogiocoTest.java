package uniroma3.siw.videogames.model;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@DisplayName("Test per la classe Videogioco")
class VideogiocoTest {

    private Videogioco videogioco;
    private Stato stato;
    private List<Categoria> categorie;
    private Categoria categoria1;
    private Categoria categoria2;

    @BeforeEach
    void setUp() {
        // Inizializzazione degli oggetti mock
        stato = new Stato();
        stato.setId(1L);
        stato.setDescrizione("Pubblicato");

        categoria1 = new Categoria();
        categoria1.setId(1L);
        categoria1.setNome("Action");

        categoria2 = new Categoria();
        categoria2.setId(2L);
        categoria2.setNome("Adventure");

        categorie = Arrays.asList(categoria1, categoria2);

        videogioco = new Videogioco("Test Game", "Descrizione del gioco di test", 
                                   "http://example.com/image.jpg", stato, categorie);
    }

    @Test
    @DisplayName("Test costruttore con parametri")
    void testCostruttoreConParametri() {
        assertEquals("Test Game", videogioco.getTitolo());
        assertEquals("Descrizione del gioco di test", videogioco.getDescrizione());
        assertEquals("http://example.com/image.jpg", videogioco.getUrlImmagine());
        assertEquals(stato, videogioco.getStato());
        assertEquals(2, videogioco.getCategorie().size());
        assertTrue(videogioco.getCategorie().contains(categoria1));
        assertTrue(videogioco.getCategorie().contains(categoria2));
        assertNotNull(videogioco.getRecensioni());
        assertNotNull(videogioco.getMessaggi());
        assertTrue(videogioco.getRecensioni().isEmpty());
        assertTrue(videogioco.getMessaggi().isEmpty());
    }

    @Test
    @DisplayName("Test costruttore vuoto")
    void testCostruttoreVuoto() {
        Videogioco videogiocoVuoto = new Videogioco();
        assertNull(videogiocoVuoto.getTitolo());
        assertNull(videogiocoVuoto.getDescrizione());
        assertNull(videogiocoVuoto.getUrlImmagine());
        assertNull(videogiocoVuoto.getStato());
        assertNotNull(videogiocoVuoto.getCategorie());
        assertTrue(videogiocoVuoto.getCategorie().isEmpty());
    }

    @Test
    @DisplayName("Test setter e getter")
    void testSetterEGetter() {
        videogioco.setId(1L);
        assertEquals(1L, videogioco.getId());

        videogioco.setTitolo("Nuovo Titolo");
        assertEquals("Nuovo Titolo", videogioco.getTitolo());

        videogioco.setDescrizione("Nuova Descrizione");
        assertEquals("Nuova Descrizione", videogioco.getDescrizione());

        videogioco.setUrlImmagine("http://example.com/new-image.jpg");
        assertEquals("http://example.com/new-image.jpg", videogioco.getUrlImmagine());

        Stato nuovoStato = new Stato();
        nuovoStato.setDescrizione("In Sviluppo");
        videogioco.setStato(nuovoStato);
        assertEquals(nuovoStato, videogioco.getStato());
    }

    @Test
    @DisplayName("Test calcolo media recensioni")
    void testCalcoloMediaRecensioni() {
        // Crea recensioni mock
        Recensione recensione1 = new Recensione();
        recensione1.setVoto(4);
        
        Recensione recensione2 = new Recensione();
        recensione2.setVoto(5);
        
        Recensione recensione3 = new Recensione();
        recensione3.setVoto(3);

        List<Recensione> recensioni = Arrays.asList(recensione1, recensione2, recensione3);
        videogioco.setRecensioni(recensioni);

        float mediaAttesa = (4.0f + 5.0f + 3.0f) / 3;
        assertEquals(mediaAttesa, videogioco.getMedia(), 0.01f);
    }

    @Test
    @DisplayName("Test equals - oggetti uguali")
    void testEqualsOggettiUguali() {
        Videogioco altroVideogioco = new Videogioco("Test Game", "Descrizione del gioco di test",
                                                   "http://example.com/image.jpg", stato, categorie);
        
        assertTrue(videogioco.equals(altroVideogioco));
        assertTrue(altroVideogioco.equals(videogioco));
        assertEquals(videogioco.hashCode(), altroVideogioco.hashCode());
    }

    @Test
    @DisplayName("Test equals - oggetti diversi")
    void testEqualsOggettiDiversi() {
        Videogioco altroVideogioco = new Videogioco("Altro Game", "Altra descrizione",
                                                   "http://example.com/other-image.jpg", stato, categorie);
        
        assertFalse(videogioco.equals(altroVideogioco));
        assertFalse(altroVideogioco.equals(videogioco));
    }

    @Test
    @DisplayName("Test equals - casi speciali")
    void testEqualsCasiSpeciali() {
        // Test con se stesso
        assertTrue(videogioco.equals(videogioco));
        
        // Test con null
        assertFalse(videogioco.equals(null));
        
        // Test con oggetto di classe diversa
        assertFalse(videogioco.equals("String"));
    }

    @Test
    @DisplayName("Test gestione categorie null nel costruttore")
    void testCategorieNullNelCostruttore() {
        Videogioco videogiocoConCategorieNull = new Videogioco("Test", "Descrizione", 
                                                              "url", stato, null);
        
        assertNotNull(videogiocoConCategorieNull.getCategorie());
        assertTrue(videogiocoConCategorieNull.getCategorie().isEmpty());
    }

    @Test
    @DisplayName("Test gestione lista vuota categorie")
    void testListaVuotaCategorie() {
        List<Categoria> categorieVuote = new ArrayList<>();
        videogioco.setCategorie(categorieVuote);
        
        assertTrue(videogioco.getCategorie().isEmpty());
    }

    @Test
    @DisplayName("Test aggiunta categorie a lista esistente")
    void testAggiuntaCategorieAListaEsistente() {
        Categoria nuovaCategoria = new Categoria();
        nuovaCategoria.setId(3L);
        nuovaCategoria.setNome("RPG");
        
        videogioco.getCategorie().add(nuovaCategoria);
        
        assertEquals(3, videogioco.getCategorie().size());
        assertTrue(videogioco.getCategorie().contains(nuovaCategoria));
    }
}
