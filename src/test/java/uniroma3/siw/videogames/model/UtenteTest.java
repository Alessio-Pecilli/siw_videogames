package uniroma3.siw.videogames.model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@DisplayName("Test per la classe Utente")
class UtenteTest {

    private Utente utente;

    @BeforeEach
    void setUp() {
        utente = new Utente("Mario", "Rossi", "mario.rossi@example.com");
    }

    @Test
    @DisplayName("Test costruttore con parametri")
    void testCostruttoreConParametri() {
        assertEquals("Mario", utente.getNome());
        assertEquals("Rossi", utente.getCognome());
        assertEquals("mario.rossi@example.com", utente.getEmail());
    }

    @Test
    @DisplayName("Test costruttore vuoto")
    void testCostruttoreVuoto() {
        Utente utenteVuoto = new Utente();
        assertNull(utenteVuoto.getNome());
        assertNull(utenteVuoto.getCognome());
        assertNull(utenteVuoto.getEmail());
    }

    @Test
    @DisplayName("Test setter e getter per ID")
    void testSetterEGetterID() {
        utente.setId(1L);
        assertEquals(1L, utente.getId());
    }

    @Test
    @DisplayName("Test setter e getter per nome")
    void testSetterEGetterNome() {
        utente.setNome("Luigi");
        assertEquals("Luigi", utente.getNome());
    }

    @Test
    @DisplayName("Test setter e getter per cognome")
    void testSetterEGetterCognome() {
        utente.setCognome("Verdi");
        assertEquals("Verdi", utente.getCognome());
    }

    @Test
    @DisplayName("Test setter e getter per email")
    void testSetterEGetterEmail() {
        utente.setEmail("luigi.verdi@example.com");
        assertEquals("luigi.verdi@example.com", utente.getEmail());
    }

    @Test
    @DisplayName("Test gestione lista recensioni")
    void testGestioneListaRecensioni() {
        Recensione recensione1 = new Recensione();
        Recensione recensione2 = new Recensione();
        
        List<Recensione> recensioni = Arrays.asList(recensione1, recensione2);
        utente.setRecensioni(recensioni);
        
        assertEquals(2, utente.getRecensioni().size());
        assertTrue(utente.getRecensioni().contains(recensione1));
        assertTrue(utente.getRecensioni().contains(recensione2));
    }

    @Test
    @DisplayName("Test gestione lista messaggi")
    void testGestioneListaMessaggi() {
        Messaggio messaggio1 = new Messaggio();
        Messaggio messaggio2 = new Messaggio();
        
        List<Messaggio> messaggi = Arrays.asList(messaggio1, messaggio2);
        utente.setMessaggi(messaggi);
        
        assertEquals(2, utente.getMessaggi().size());
        assertTrue(utente.getMessaggi().contains(messaggio1));
        assertTrue(utente.getMessaggi().contains(messaggio2));
    }

    @Test
    @DisplayName("Test equals - utenti uguali (stessa email)")
    void testEqualsUtentiUguali() {
        Utente altroUtente = new Utente("Giovanni", "Bianchi", "mario.rossi@example.com");
        
        assertTrue(utente.equals(altroUtente));
        assertTrue(altroUtente.equals(utente));
        // Test hashCode solo se equals è true (come per il contratto Object)
        if (utente.equals(altroUtente)) {
            assertEquals(utente.hashCode(), altroUtente.hashCode());
        }
    }

    @Test
    @DisplayName("Test equals - utenti diversi (email diverse)")
    void testEqualsUtentiDiversi() {
        Utente altroUtente = new Utente("Mario", "Rossi", "mario.bianchi@example.com");
        
        assertFalse(utente.equals(altroUtente));
        assertFalse(altroUtente.equals(utente));
    }

    @Test
    @DisplayName("Test equals - casi speciali")
    void testEqualsCasiSpeciali() {
        // Test con se stesso
        assertTrue(utente.equals(utente));
        
        // Test con null
        assertFalse(utente.equals(null));
        
        // Test con oggetto di classe diversa
        assertFalse(utente.equals("String"));
    }

    @Test
    @DisplayName("Test equals con email null")
    void testEqualsConEmailNull() {
        Utente utenteConEmailNull1 = new Utente("Mario", "Rossi", null);
        Utente utenteConEmailNull2 = new Utente("Luigi", "Verdi", null);
        
        assertTrue(utenteConEmailNull1.equals(utenteConEmailNull2));
        
        Utente utenteConEmail = new Utente("Mario", "Rossi", "mario@example.com");
        assertFalse(utenteConEmailNull1.equals(utenteConEmail));
        assertFalse(utenteConEmail.equals(utenteConEmailNull1));
    }

    @Test
    @DisplayName("Test hashCode consistency")
    void testHashCodeConsistency() {
        // Il hashCode deve essere consistente per lo stesso oggetto
        int hashCode1 = utente.hashCode();
        int hashCode2 = utente.hashCode();
        assertEquals(hashCode1, hashCode2);
        
        // Test con oggetto uguale
        Utente altroUtente = new Utente("Giovanni", "Bianchi", "mario.rossi@example.com");
        if (utente.equals(altroUtente)) {
            assertEquals(utente.hashCode(), altroUtente.hashCode());
        }
    }

    @Test
    @DisplayName("Test gestione liste null")
    void testGestioneListeNull() {
        utente.setRecensioni(null);
        utente.setMessaggi(null);
        
        assertNull(utente.getRecensioni());
        assertNull(utente.getMessaggi());
    }

    @Test
    @DisplayName("Test gestione liste vuote")
    void testGestioneListeVuote() {
        List<Recensione> recensioniVuote = new ArrayList<>();
        List<Messaggio> messaggiVuoti = new ArrayList<>();
        
        utente.setRecensioni(recensioniVuote);
        utente.setMessaggi(messaggiVuoti);
        
        assertTrue(utente.getRecensioni().isEmpty());
        assertTrue(utente.getMessaggi().isEmpty());
    }
}
