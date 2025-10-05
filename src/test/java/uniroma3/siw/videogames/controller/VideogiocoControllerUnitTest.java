package uniroma3.siw.videogames.controller;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;

import uniroma3.siw.videogames.model.Categoria;
import uniroma3.siw.videogames.model.Stato;
import uniroma3.siw.videogames.model.Videogioco;
import uniroma3.siw.videogames.service.CategoriaService;
import uniroma3.siw.videogames.service.StatoService;
import uniroma3.siw.videogames.service.VideogiocoService;

@ExtendWith(MockitoExtension.class)
@DisplayName("Test unitari per VideogiocoController senza Spring Context")
class VideogiocoControllerUnitTest {

    @Mock
    private VideogiocoService videogiocoService;

    @Mock
    private StatoService statoService;

    @Mock
    private CategoriaService categoriaService;

    @Mock
    private Model model;

    @InjectMocks
    private VideogiocoController videogiocoController;

    private Videogioco videogioco1;
    private Videogioco videogioco2;
    private Stato stato;
    private Categoria categoria1;
    private Categoria categoria2;
    private List<Videogioco> videogiochi;
    private List<Stato> stati;
    private List<Categoria> categorie;

    @BeforeEach
    void setUp() {
        stato = new Stato();
        stato.setId(1L);
        stato.setDescrizione("Pubblicato");

        categoria1 = new Categoria();
        categoria1.setId(1L);
        categoria1.setNome("Action");

        categoria2 = new Categoria();
        categoria2.setId(2L);
        categoria2.setNome("Adventure");

        videogioco1 = new Videogioco();
        videogioco1.setId(1L);
        videogioco1.setTitolo("Game 1");
        videogioco1.setDescrizione("Descrizione Game 1");
        videogioco1.setStato(stato);
        videogioco1.setCategorie(Arrays.asList(categoria1));

        videogioco2 = new Videogioco();
        videogioco2.setId(2L);
        videogioco2.setTitolo("Game 2");
        videogioco2.setDescrizione("Descrizione Game 2");
        videogioco2.setStato(stato);
        videogioco2.setCategorie(Arrays.asList(categoria2));

        videogiochi = Arrays.asList(videogioco1, videogioco2);
        stati = Arrays.asList(stato);
        categorie = Arrays.asList(categoria1, categoria2);
    }

    @Test
    @DisplayName("Test mostra catalogo - logica del controller")
    void testMostraCatalogoVideogiochi() {
        // Given
        when(videogiocoService.getAllVideogames()).thenReturn(videogiochi);
        when(statoService.getAllStates()).thenReturn(stati);
        when(categoriaService.getAllCategorie()).thenReturn(categorie);

        // When
        String risultato = videogiocoController.mostraCatalogoVideogiochi(model);

        // Then
        assertEquals("videogiochi.html", risultato);
        verify(model, times(1)).addAttribute("videogiochi", videogiochi);
        verify(model, times(1)).addAttribute("stati", stati);
        verify(model, times(1)).addAttribute("categorie", categorie);
        verify(videogiocoService, times(1)).getAllVideogames();
        verify(statoService, times(1)).getAllStates();
        verify(categoriaService, times(1)).getAllCategorie();
    }

    @Test
    @DisplayName("Test chiamate ai servizi")
    void testChiamateDeiServizi() {
        // Given
        when(videogiocoService.getAllVideogames()).thenReturn(videogiochi);
        when(statoService.getAllStates()).thenReturn(stati);
        when(categoriaService.getAllCategorie()).thenReturn(categorie);

        // When
        videogiocoController.mostraCatalogoVideogiochi(model);

        // Then
        verify(videogiocoService, times(1)).getAllVideogames();
        verify(statoService, times(1)).getAllStates();
        verify(categoriaService, times(1)).getAllCategorie();
    }
}