package uniroma3.siw.videogames.controller;

import static uniroma3.siw.videogames.model.Credentials.ADMIN_ROLE;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import uniroma3.siw.videogames.model.Categoria;
import uniroma3.siw.videogames.model.Stato;
import uniroma3.siw.videogames.model.Videogioco;
import uniroma3.siw.videogames.service.CategoriaService;
import uniroma3.siw.videogames.service.CredentialsService;
import uniroma3.siw.videogames.service.StatoService;
import uniroma3.siw.videogames.service.UtenteService;
import uniroma3.siw.videogames.service.VideogiocoService;

@Controller
@RequestMapping("/operatore/videogiochi")
public class OperatoreVideogiocoController {

	@Autowired
	private VideogiocoService videogiocoService;

	@Autowired
	private StatoService statoService;

	@Autowired
	private CategoriaService categoriaService;
	
	@Autowired
	private UtenteService utenteService;
	
	@Autowired
	private CredentialsService credentialsService;
	
	private boolean verificaAdmin() {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return credentialsService.getCredentialsByUsername(userDetails.getUsername()).getRole().equals(ADMIN_ROLE);
	}
	
	@GetMapping("")
	public String showVideogiochi(Model model) {
		if (!verificaAdmin())
			return "redirect:/login";
		
		model.addAttribute("videogiochi", videogiocoService.getAllVideogames());
		model.addAttribute("stati", statoService.getAllStates());
		model.addAttribute("categorie", categoriaService.getAllCategorie());
		return "operatore/videogiochi.html";
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

		model.addAttribute("utente", this.utenteService.getUtenteCorrente());
		model.addAttribute("videogiochi", videogiochiFiltrati);
		model.addAttribute("stati", statoService.getAllStates());
		model.addAttribute("categorie", categoriaService.getAllCategorie());
		model.addAttribute("query", query);
		model.addAttribute("statoSelezionato", stato);
		model.addAttribute("categorieSelezionate", categorieIds);
		model.addAttribute("filtroAttivo", !videogiochiFiltrati.isEmpty());

		return "operatore/videogiochi.html";
	}

	@GetMapping("/elimina/{id}")
	public String eliminaVideogioco(@PathVariable Long id) {
		try {
			if (!verificaAdmin())
				return "redirect:/login";
			videogiocoService.eliminaVideogioco(id);
			return "redirect:/operatore/videogiochi";
		} catch (Exception e) {
			return "redirect:/operatore/error/500";
		}
	}
	
	@GetMapping("/{id}")
	public String showVideogiochiAOperatore(@PathVariable("id") Long id, Model model) {
		Videogioco videogioco = videogiocoService.getVideogameById(id);
		if(videogioco == null)
			return "redirect/operatore/error/404";
		model.addAttribute("videogioco", videogioco);
		
		return "operatore/videogioco.html";
	}

	@GetMapping("/modifica/{id}")
	public String mostraFormModifica(@PathVariable Long id, Model model) {
		Videogioco vg = videogiocoService.getVideogameById(id);
		if(vg == null)
			return "redirect/operatore/error/404";
		
		model.addAttribute("videogioco", vg);
		model.addAttribute("stati", statoService.getAllStates());
		model.addAttribute("categorie", categoriaService.getAllCategorie());
		return "operatore/formNewVideogioco";

	}

	@PostMapping("/salva")
	public String salvaVideogioco(@ModelAttribute("videogioco") Videogioco videogioco, BindingResult bindingResult,
			@RequestParam(required = false) List<Long> categorie, Model model) {
		try {
			if(bindingResult.hasErrors()) {
				
				model.addAttribute("videogioco", videogioco);
				model.addAttribute("stati", statoService.getAllStates());
				model.addAttribute("categorie", categoriaService.getAllCategorie());
				return "operatore/formNewVideogioco";
			}
			if (categorie != null) {
				List<Categoria> listaCategorie = new ArrayList<>();
				categoriaService.getCategorieById(categorie).forEach(listaCategorie::add);
				videogioco.setCategorie(listaCategorie);
			} else {
				videogioco.setCategorie(new ArrayList<>());
			}

			videogiocoService.salvaNuovoVideogame(videogioco);
			return "redirect:/operatore/videogiochi";
		} catch (Exception e) {
			
			model.addAttribute("videogioco", videogioco);
			model.addAttribute("stati", statoService.getAllStates());
			model.addAttribute("categorie", categoriaService.getAllCategorie());
			model.addAttribute("msgError", "Non lasciare campi vuoti");
			return "operatore/formNewVideogioco";
		}
	}

	@GetMapping("/new")
	public String creaVideogioco(Model model) {
		
		model.addAttribute("videogioco", new Videogioco()); // inizializza oggetto vuoto
		model.addAttribute("stati", statoService.getAllStates()); // popola stati per il <select>
		model.addAttribute("categorie", categoriaService.getAllCategorie()); // popola categorie per i checkbox
		return "operatore/formNewVideogioco"; // carica correttamente il template
	}
	
}
