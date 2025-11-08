package uniroma3.siw.videogames.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.context.ActiveProfiles;

import uniroma3.siw.videogames.model.Categoria;
import uniroma3.siw.videogames.model.Stato;
import uniroma3.siw.videogames.model.Videogioco;
import uniroma3.siw.videogames.service.CategoriaService;
import uniroma3.siw.videogames.service.StatoService;
import uniroma3.siw.videogames.service.VideogiocoService;

@SpringBootTest(
    classes = {
        uniroma3.siw.videogames.VideogamesApplication.class,
        uniroma3.siw.videogames.config.ControllerTestConfig.class
    },
    webEnvironment = SpringBootTest.WebEnvironment.MOCK
)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DisplayName("Test per VideogiocoController")
class VideogiocoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VideogiocoService videogiocoService;

    @MockBean
    private StatoService statoService;

    @MockBean
    private CategoriaService categoriaService;

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
    @DisplayName("Test GET /videogiochi - mostra catalogo")
    void testMostraCatalogoVideogiochi() throws Exception {
        // Given
        when(videogiocoService.getAllVideogames()).thenReturn(videogiochi);
        when(statoService.getAllStates()).thenReturn(stati);
        when(categoriaService.getAllCategorie()).thenReturn(categorie);

        // When & Then
        mockMvc.perform(get("/videogiochi"))
                .andExpect(status().isOk())
                .andExpect(view().name("videogiochi.html"))
                .andExpect(model().attribute("videogiochi", videogiochi))
                .andExpect(model().attribute("stati", stati))
                .andExpect(model().attribute("categorie", categorie));

        verify(videogiocoService, times(1)).getAllVideogames();
        verify(statoService, times(1)).getAllStates();
        verify(categoriaService, times(1)).getAllCategorie();
    }

    @Test
    @DisplayName("Test GET /videogiochi/filter - filtro per stato")
    void testFiltraVideogiochiPerStato() throws Exception {
        // Given
        Long statoId = 1L;
        when(statoService.getById(statoId)).thenReturn(stato);
        when(videogiocoService.getByStato(stato)).thenReturn(Arrays.asList(videogioco1));
        when(statoService.getAllStates()).thenReturn(stati);
        when(categoriaService.getAllCategorie()).thenReturn(categorie);

        // When & Then
        mockMvc.perform(get("/videogiochi/filter")
                .param("stato", statoId.toString()))
                .andExpect(status().isOk())
                .andExpect(view().name("videogiochi.html"))
                .andExpect(model().attribute("videogiochi", Arrays.asList(videogioco1)))
                .andExpect(model().attribute("statoSelezionato", statoId))
                .andExpect(model().attribute("filtroAttivo", true));

        verify(statoService, times(1)).getById(statoId);
        verify(videogiocoService, times(1)).getByStato(stato);
    }

    @Test
    @DisplayName("Test GET /videogiochi/filter - filtro per categorie")
    void testFiltraVideogiochiPerCategorie() throws Exception {
        // Given
        List<Long> categorieIds = Arrays.asList(1L, 2L);
        when(categoriaService.getById(1L)).thenReturn(categoria1);
        when(categoriaService.getById(2L)).thenReturn(categoria2);
        when(videogiocoService.getByCategorie(Arrays.asList(categoria1, categoria2))).thenReturn(videogiochi);
        when(statoService.getAllStates()).thenReturn(stati);
        when(categoriaService.getAllCategorie()).thenReturn(categorie);

        // When & Then
        mockMvc.perform(get("/videogiochi/filter")
                .param("categorie", "1", "2"))
                .andExpect(status().isOk())
                .andExpect(view().name("videogiochi.html"))
                .andExpect(model().attribute("videogiochi", videogiochi))
                .andExpect(model().attribute("categorieSelezionate", categorieIds));

        verify(categoriaService, times(1)).getById(1L);
        verify(categoriaService, times(1)).getById(2L);
        verify(videogiocoService, times(1)).getByCategorie(Arrays.asList(categoria1, categoria2));
    }

    @Test
    @DisplayName("Test GET /videogiochi/filter - filtro per query testuale")
    void testFiltraVideogiochiPerQuery() throws Exception {
        // Given
        String query = "Game 1";
        when(videogiocoService.getAllVideogames()).thenReturn(videogiochi);
        when(statoService.getAllStates()).thenReturn(stati);
        when(categoriaService.getAllCategorie()).thenReturn(categorie);

        // When & Then
        mockMvc.perform(get("/videogiochi/filter")
                .param("query", query))
                .andExpect(status().isOk())
                .andExpect(view().name("videogiochi.html"))
                .andExpect(model().attribute("query", query))
                .andExpect(model().attributeExists("videogiochi"));

        verify(videogiocoService, times(1)).getAllVideogames();
    }

    @Test
    @DisplayName("Test GET /videogiochi/filter - nessun filtro applicato")
    void testFiltraVideogiochiSenzaFiltri() throws Exception {
        // Given
        when(videogiocoService.getAllVideogames()).thenReturn(videogiochi);
        when(statoService.getAllStates()).thenReturn(stati);
        when(categoriaService.getAllCategorie()).thenReturn(categorie);

        // When & Then
        mockMvc.perform(get("/videogiochi/filter"))
                .andExpect(status().isOk())
                .andExpect(view().name("videogiochi.html"))
                .andExpect(model().attribute("videogiochi", videogiochi));

        verify(videogiocoService, times(1)).getAllVideogames();
    }

    @Test
    @DisplayName("Test GET /videogiochi/{id} - videogioco esistente")
    void testShowVideogiochiEsistente() throws Exception {
        // Given
        Long id = 1L;
        when(videogiocoService.getVideogameById(id)).thenReturn(videogioco1);

        // When & Then
        mockMvc.perform(get("/videogiochi/{id}", id))
                .andExpect(status().isOk())
                .andExpect(view().name("videogioco.html"))
                .andExpect(model().attribute("videogioco", videogioco1));

        verify(videogiocoService, times(1)).getVideogameById(id);
    }

    @Test
    @DisplayName("Test GET /videogiochi/{id} - videogioco non esistente")
    void testShowVideogiochiNonEsistente() throws Exception {
        // Given
        Long id = 999L;
        when(videogiocoService.getVideogameById(id)).thenReturn(null);

        // When & Then
        mockMvc.perform(get("/videogiochi/{id}", id))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/error/404"));

        verify(videogiocoService, times(1)).getVideogameById(id);
    }

    @Test
    @DisplayName("Test filtro combinato stato e categorie")
    void testFiltroCombinato() throws Exception {
        // Given
        Long statoId = 1L;
        
        when(statoService.getById(statoId)).thenReturn(stato);
        when(categoriaService.getById(1L)).thenReturn(categoria1);
        when(videogiocoService.getByStato(stato)).thenReturn(Arrays.asList(videogioco1));
        when(statoService.getAllStates()).thenReturn(stati);
        when(categoriaService.getAllCategorie()).thenReturn(categorie);

        // When & Then
        mockMvc.perform(get("/videogiochi/filter")
                .param("stato", statoId.toString())
                .param("categorie", "1"))
                .andExpect(status().isOk())
                .andExpect(view().name("videogiochi.html"))
                .andExpect(model().attributeExists("videogiochi"));

        verify(statoService, times(1)).getById(statoId);
        verify(categoriaService, times(1)).getById(1L);
        verify(videogiocoService, times(1)).getByStato(stato);
    }
}
