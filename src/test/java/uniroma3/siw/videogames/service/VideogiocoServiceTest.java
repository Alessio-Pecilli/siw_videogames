package uniroma3.siw.videogames.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import uniroma3.siw.videogames.model.Categoria;
import uniroma3.siw.videogames.model.Stato;
import uniroma3.siw.videogames.model.Videogioco;
import uniroma3.siw.videogames.repository.VideogiocoRepository;

@ExtendWith(MockitoExtension.class)
@DisplayName("Test per VideogiocoService")
class VideogiocoServiceTest {

    @Mock
    private VideogiocoRepository videogiocoRepository;

    @InjectMocks
    private VideogiocoService videogiocoService;

    private Videogioco videogioco1;
    private Videogioco videogioco2;
    private Stato stato;
    private Categoria categoria;

    @BeforeEach
    void setUp() {
        stato = new Stato();
        stato.setId(1L);
        stato.setDescrizione("Pubblicato");

        categoria = new Categoria();
        categoria.setId(1L);
        categoria.setNome("Action");

        videogioco1 = new Videogioco();
        videogioco1.setId(1L);
        videogioco1.setTitolo("Game 1");
        videogioco1.setDescrizione("Descrizione Game 1");
        videogioco1.setStato(stato);

        videogioco2 = new Videogioco();
        videogioco2.setId(2L);
        videogioco2.setTitolo("Game 2");
        videogioco2.setDescrizione("Descrizione Game 2");
        videogioco2.setStato(stato);
    }

    @Test
    @DisplayName("Test getAllVideogames - restituisce tutti i videogiochi")
    void testGetAllVideogames() {
        // Given
        List<Videogioco> videogiochi = Arrays.asList(videogioco1, videogioco2);
        when(videogiocoRepository.findAll()).thenReturn(videogiochi);

        // When
        List<Videogioco> result = videogiocoService.getAllVideogames();

        // Then
        assertEquals(2, result.size());
        assertTrue(result.contains(videogioco1));
        assertTrue(result.contains(videogioco2));
        verify(videogiocoRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Test getByStato - restituisce videogiochi per stato")
    void testGetByStato() {
        // Given
        List<Videogioco> videogiochi = Arrays.asList(videogioco1, videogioco2);
        when(videogiocoRepository.findByStato(stato)).thenReturn(videogiochi);

        // When
        List<Videogioco> result = videogiocoService.getByStato(stato);

        // Then
        assertEquals(2, result.size());
        assertTrue(result.contains(videogioco1));
        assertTrue(result.contains(videogioco2));
        verify(videogiocoRepository, times(1)).findByStato(stato);
    }

    @Test
    @DisplayName("Test getByCategoria - restituisce videogiochi per categoria")
    void testGetByCategoria() {
        // Given
        List<Videogioco> videogiochi = Arrays.asList(videogioco1);
        when(videogiocoRepository.findByCategorie(categoria)).thenReturn(videogiochi);

        // When
        List<Videogioco> result = videogiocoService.getByCategoria(categoria);

        // Then
        assertEquals(1, result.size());
        assertTrue(result.contains(videogioco1));
        verify(videogiocoRepository, times(1)).findByCategorie(categoria);
    }

    @Test
    @DisplayName("Test getByCategorie - restituisce videogiochi per lista categorie")
    void testGetByCategorie() {
        // Given
        List<Categoria> categorie = Arrays.asList(categoria);
        List<Videogioco> videogiochi = Arrays.asList(videogioco1, videogioco2);
        when(videogiocoRepository.findByCategorieIn(categorie)).thenReturn(videogiochi);

        // When
        List<Videogioco> result = videogiocoService.getByCategorie(categorie);

        // Then
        assertEquals(2, result.size());
        assertTrue(result.contains(videogioco1));
        assertTrue(result.contains(videogioco2));
        verify(videogiocoRepository, times(1)).findByCategorieIn(categorie);
    }

    @Test
    @DisplayName("Test getVideogameById - videogioco esistente")
    void testGetVideogameByIdEsistente() {
        // Given
        Long id = 1L;
        when(videogiocoRepository.findById(id)).thenReturn(Optional.of(videogioco1));

        // When
        Videogioco result = videogiocoService.getVideogameById(id);

        // Then
        assertNotNull(result);
        assertEquals(videogioco1, result);
        verify(videogiocoRepository, times(1)).findById(id);
    }

    @Test
    @DisplayName("Test getVideogameById - videogioco non esistente")
    void testGetVideogameByIdNonEsistente() {
        // Given
        Long id = 999L;
        when(videogiocoRepository.findById(id)).thenReturn(Optional.empty());

        // When
        Videogioco result = videogiocoService.getVideogameById(id);

        // Then
        assertNull(result);
        verify(videogiocoRepository, times(1)).findById(id);
    }

    @Test
    @DisplayName("Test getVideogameById - eccezione durante la ricerca")
    void testGetVideogameByIdConEccezione() {
        // Given
        Long id = 1L;
        when(videogiocoRepository.findById(id)).thenThrow(new RuntimeException("Database error"));

        // When
        Videogioco result = videogiocoService.getVideogameById(id);

        // Then
        assertNull(result);
        verify(videogiocoRepository, times(1)).findById(id);
    }

    @Test
    @DisplayName("Test salvaNuovoVideogame - salvataggio corretto")
    void testSalvaNuovoVideogame() {
        // Given
        when(videogiocoRepository.save(videogioco1)).thenReturn(videogioco1);

        // When
        videogiocoService.salvaNuovoVideogame(videogioco1);

        // Then
        verify(videogiocoRepository, times(1)).save(videogioco1);
    }

    @Test
    @DisplayName("Test eliminaVideogioco - eliminazione corretta")
    void testEliminaVideogioco() {
        // Given
        Long id = 1L;
        doNothing().when(videogiocoRepository).deleteById(id);

        // When
        videogiocoService.eliminaVideogioco(id);

        // Then
        verify(videogiocoRepository, times(1)).deleteById(id);
    }

    @Test
    @DisplayName("Test existsVideogioco - videogioco esistente")
    void testExistsVideogiocoEsistente() {
        // Given
        Long id = 1L;
        when(videogiocoRepository.existsById(id)).thenReturn(true);

        // When
        boolean result = videogiocoService.existsVideogioco(id);

        // Then
        assertTrue(result);
        verify(videogiocoRepository, times(1)).existsById(id);
    }

    @Test
    @DisplayName("Test existsVideogioco - videogioco non esistente")
    void testExistsVideogiocoNonEsistente() {
        // Given
        Long id = 999L;
        when(videogiocoRepository.existsById(id)).thenReturn(false);

        // When
        boolean result = videogiocoService.existsVideogioco(id);

        // Then
        assertFalse(result);
        verify(videogiocoRepository, times(1)).existsById(id);
    }

    @Test
    @DisplayName("Test getAllVideogames - lista vuota")
    void testGetAllVideogamesListaVuota() {
        // Given
        when(videogiocoRepository.findAll()).thenReturn(Arrays.asList());

        // When
        List<Videogioco> result = videogiocoService.getAllVideogames();

        // Then
        assertTrue(result.isEmpty());
        verify(videogiocoRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Test getByStato - nessun videogioco trovato")
    void testGetByStatoNessunVideogioco() {
        // Given
        when(videogiocoRepository.findByStato(stato)).thenReturn(Arrays.asList());

        // When
        List<Videogioco> result = videogiocoService.getByStato(stato);

        // Then
        assertTrue(result.isEmpty());
        verify(videogiocoRepository, times(1)).findByStato(stato);
    }
}
