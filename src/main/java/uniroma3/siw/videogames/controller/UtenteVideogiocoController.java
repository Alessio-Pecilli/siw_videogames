package uniroma3.siw.videogames.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import uniroma3.siw.videogames.model.Categoria;
import uniroma3.siw.videogames.model.Stato;
import uniroma3.siw.videogames.model.Videogioco;
import uniroma3.siw.videogames.service.CategoriaService;
import uniroma3.siw.videogames.service.StatoService;
import uniroma3.siw.videogames.service.VideogiocoService;

@Controller
@RequestMapping("/utente/videogiochi")
public class UtenteVideogiocoController {

	@Autowired
	private VideogiocoService videogiocoService;

	@Autowired
	private StatoService statoService;

	@Autowired
	private CategoriaService categoriaService;
	
	
	@GetMapping("")
	public String showVideogiochiUtente(Model model) {
		model.addAttribute("videogiochi", this.videogiocoService.getAllVideogames());
		model.addAttribute("stati", statoService.getAllStates());
		model.addAttribute("categorie", categoriaService.getAllCategorie());
		
		return "utente/videogiochi.html";
	}
	
	@GetMapping("/filter")
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
		model.addAttribute("filtroAttivo", !videogiochiFiltrati.isEmpty());

		return "utente/videogiochi.html";
	}
	
	@GetMapping("/{id}")
	public String showVideogiochi(@PathVariable("id") Long id, Model model) {
		Videogioco videogioco = videogiocoService.getVideogameById(id);
		if(videogioco == null)
			return "redirect:/utente/error/404";
		
		model.addAttribute("videogioco", videogioco);
		return "utente/videogioco.html";
	}
	
}
