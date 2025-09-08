package uniroma3.siw.videogames.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import uniroma3.siw.videogames.model.Categoria;
import uniroma3.siw.videogames.model.Stato;
import uniroma3.siw.videogames.model.Videogioco;
import uniroma3.siw.videogames.service.CategoriaService;
import uniroma3.siw.videogames.service.StatoService;
import uniroma3.siw.videogames.service.VideogiocoService;

@Controller
public class VideogiocoController {

	@Autowired
	private VideogiocoService videogiocoService;

	@Autowired
	private StatoService statoService;

	@Autowired
	private CategoriaService categoriaService;

	@GetMapping("/videogiochi")
	public String mostraCatalogoVideogiochi(Model model) {
		// Popola sempre i dati pubblici per la pagina
		model.addAttribute("videogiochi", videogiocoService.getAllVideogames());
		model.addAttribute("stati", statoService.getAllStates());
		model.addAttribute("categorie", categoriaService.getAllCategorie());
		// Se occorrono altri filtri aggiungili come prima
		// SE UTENTE AUTENTICATO
		return "videogiochi.html";
	}

	@GetMapping("/videogiochi/filter")
	public String filtraVideogiochi(@RequestParam(required = false) Long stato,
			@RequestParam(required = false, name = "categorie") List<Long> categorieIds,
			@RequestParam(required = false) String query, Model model) {

		List<Videogioco> videogiochiFiltrati;

		// Conversione degli ID in oggetti Categoria
		List<Categoria> categorie = (categorieIds != null && !categorieIds.isEmpty())
				? categorieIds.stream().map(id -> categoriaService.getById(id)).toList()
				: null;

		if (stato != null && categorie != null) {
			Stato statoEntity = statoService.getById(stato);
			List<Videogioco> perStato = videogiocoService.getByStato(statoEntity);

			videogiochiFiltrati = perStato.stream().filter(v -> v.getCategorie().containsAll(categorie)).toList();

		} else if (stato != null) {
			Stato statoEntity = statoService.getById(stato);
			videogiochiFiltrati = videogiocoService.getByStato(statoEntity);

		} else if (categorie != null) {
			videogiochiFiltrati = videogiocoService.getByCategorie(categorie);

		} else {
			videogiochiFiltrati = videogiocoService.getAllVideogames();
		}

		if (query != null && !query.trim().isEmpty()) {
			String lowerQuery = query.toLowerCase();
			videogiochiFiltrati = videogiochiFiltrati.stream()
					.filter(v -> v.getTitolo().toLowerCase().contains(lowerQuery)
							|| v.getDescrizione().toLowerCase().contains(lowerQuery))
					.toList();
		}

		model.addAttribute("videogiochi", videogiochiFiltrati);
		model.addAttribute("stati", statoService.getAllStates());
		model.addAttribute("categorie", categoriaService.getAllCategorie());
		model.addAttribute("query", query);
		model.addAttribute("statoSelezionato", stato);
		model.addAttribute("categorieSelezionate", categorieIds);
		System.out.println("Numero di giochi trovati: " + videogiochiFiltrati.size());
		
		model.addAttribute("filtroAttivo", !videogiochiFiltrati.isEmpty());

		return "videogiochi.html";
	}

	@GetMapping("/videogiochi/{id}")
	public String showVideogiochi(@PathVariable("id") Long id, Model model) {
		Videogioco videogioco = videogiocoService.getVideogameById(id);
		if(videogioco  != null) {
			model.addAttribute("videogioco", videogioco);
			return "videogioco.html";
		}
		return "redirect:/error/404";
	}
}
