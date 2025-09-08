package uniroma3.siw.videogames.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import uniroma3.siw.videogames.model.Recensione;
import uniroma3.siw.videogames.model.Utente;
import uniroma3.siw.videogames.model.Videogioco;
import uniroma3.siw.videogames.service.RecensioneService;
import uniroma3.siw.videogames.service.UtenteService;
import uniroma3.siw.videogames.service.VideogiocoService;

@Controller
public class RecensioneController {

	@Autowired
	private RecensioneService recensioneService;

	@Autowired
	private VideogiocoService videogiocoService;

	@Autowired
	private UtenteService utenteService;
	
	private boolean verificaRecensione(Long idVideogioco, Long idRecensione) {
		return utenteService.getUtenteCorrente() != null && videogiocoService.existsVideogioco(idVideogioco) &&
				recensioneService.existsRecensioneOfVideogioco(idRecensione, idVideogioco);
	}

	private void addAttributiPaginaRecensioni(Model model, Videogioco videogioco, List<Recensione> recensioni,
			double mediaVoti, boolean haRecensioni) {
		model.addAttribute("videogioco", videogioco);
		model.addAttribute("recensioni", recensioni);
		model.addAttribute("nuovaRecensione", new Recensione());
		model.addAttribute("haRecensioni", haRecensioni);
		model.addAttribute("mediaVoti", mediaVoti);
	}
	
	@GetMapping("/videogiochi/{id}/recensioni")
	public String mostraRecensioni(@PathVariable("id") Long videogiocoId, Model model) {
		Videogioco videogioco = videogiocoService.getVideogameById(videogiocoId);
		if(videogioco == null)
			return "redirect:/utente/error/404";
		List<Recensione> recensioni = recensioneService.getRecensioniPerVideogioco(videogioco);

		double mediaVoti = 0;
		boolean haRecensioni = !recensioni.isEmpty();

		if (haRecensioni) {
			mediaVoti = recensioni.stream().mapToInt(Recensione::getVoto).average().orElse(0);
		}

		addAttributiPaginaRecensioni(model, videogioco, recensioni, mediaVoti, haRecensioni);

		return "recensioni.html";
	}

	@GetMapping("/utente/videogiochi/{id}/recensioni")
	public String mostraRecensioniUtente(@PathVariable("id") Long videogiocoId, Model model) {
		Videogioco videogioco = videogiocoService.getVideogameById(videogiocoId);
		if(videogioco == null)
			return "redirect:/utente/error/404";
		List<Recensione> recensioni = recensioneService.getRecensioniPerVideogioco(videogioco);
		Utente utente = utenteService.getUtenteCorrente();

		double mediaVoti = 0;
		boolean haRecensioni = !recensioni.isEmpty();

		if (haRecensioni) {
			mediaVoti = recensioni.stream().mapToInt(Recensione::getVoto).average().orElse(0);
		}

		addAttributiPaginaRecensioni(model, videogioco, recensioni, mediaVoti, haRecensioni);
		model.addAttribute("recensioneUtente", recensioneService.getRecensioniPerUtenteEVideogioco(utente, videogioco));

		return "utente/recensioni.html";
	}

	@GetMapping("/operatore/videogiochi/{id}/recensioni")
	public String mostraRecensioniOperatore(@PathVariable("id") Long videogiocoId, Model model) {
		Videogioco videogioco = videogiocoService.getVideogameById(videogiocoId);
		if(videogioco == null)
			return "redirect:/operatore/error/404";
		List<Recensione> recensioni = recensioneService.getRecensioniPerVideogioco(videogioco);
		Utente utente = utenteService.getUtenteCorrente();

		double mediaVoti = 0;
		boolean haRecensioni = !recensioni.isEmpty();

		if (haRecensioni) {
			mediaVoti = recensioni.stream().mapToInt(Recensione::getVoto).average().orElse(0);
		}

		addAttributiPaginaRecensioni(model, videogioco, recensioni, mediaVoti, haRecensioni);
		model.addAttribute("recensioneUtente", recensioneService.getRecensioniPerUtenteEVideogioco(utente, videogioco));

		return "operatore/recensioni.html";
	}

	@PostMapping("/utente/videogiochi/{idVideogioco}/recensioni/{idRecensione}/delete")
	public String eliminaRecensioneUtente(@PathVariable("idVideogioco") Long idVideogioco,
			@PathVariable("idRecensione") Long idRecensione, Model model) {
		if (!verificaRecensione(idVideogioco, idRecensione))
			return "redirect:/utente/error/404";
		
		recensioneService.eliminaPerId(idRecensione);

		return "redirect:/utente/videogiochi/"+ idVideogioco +"/recensioni";
	}

	@PostMapping("/operatore/videogiochi/{idVideogioco}/recensioni/{idRecensione}/delete")
	public String eliminaRecensioneOperatore(@PathVariable("idVideogioco") Long idVideogioco,
			@PathVariable("idRecensione") Long idRecensione, Model model) {
		if (!verificaRecensione(idVideogioco, idRecensione))
			return "redirect:/operatore/error/404";
		
		recensioneService.eliminaPerId(idRecensione);

		return "redirect:/operatore/videogiochi/"+ idVideogioco +"/recensioni";
	}

	@PostMapping("/utente/videogiochi/{id}/recensioni")
	public String salvaRecensione(@PathVariable("id") Long videogiocoId, @RequestParam("feedback") String feedback,
			@RequestParam("voto") Integer voto) {
		if(!this.videogiocoService.existsVideogioco(videogiocoId))
			return "redirect:/utente/error/404";
		Recensione recensione = new Recensione(LocalTime.now(), LocalDate.now(), feedback,
				videogiocoService.getVideogameById(videogiocoId), utenteService.getUtenteCorrente(), voto);
		recensioneService.salvaRecensione(recensione);
		return "redirect:recensioni";
	}
}
