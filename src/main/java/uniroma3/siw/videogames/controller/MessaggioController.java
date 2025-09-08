package uniroma3.siw.videogames.controller;

import static uniroma3.siw.videogames.model.Credentials.ADMIN_ROLE;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uniroma3.siw.videogames.model.Messaggio;
import uniroma3.siw.videogames.model.Utente;
import uniroma3.siw.videogames.model.Videogioco;
import uniroma3.siw.videogames.service.CredentialsService;
import uniroma3.siw.videogames.service.MessaggioService;
import uniroma3.siw.videogames.service.UtenteService;
import uniroma3.siw.videogames.service.VideogiocoService;

@Controller
public class MessaggioController {

	@Autowired
	private MessaggioService messaggioService;

	@Autowired
	private VideogiocoService videogiocoService;

	@Autowired
	private UtenteService utenteService;
	
	@Autowired
	private CredentialsService credentialsService;

	private boolean verificaAdmin() {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return credentialsService.getCredentialsByUsername(userDetails.getUsername()).getRole().equals(ADMIN_ROLE);
	}
	
	@GetMapping("/videogiochi/{id}/messaggi")
	public String mostraMessaggi(@PathVariable("id") Long videogiocoId, Model model) {
		Videogioco videogioco = videogiocoService.getVideogameById(videogiocoId);
		if(videogioco == null)
			return "redirect:/error/404";
		List<Messaggio> messaggi = messaggioService.getMessaggiPerVideogioco(videogioco);

		model.addAttribute("videogioco", videogioco);
		model.addAttribute("messaggi", messaggi);

		return "messaggi.html";
	}
	
	@GetMapping("/utente/videogiochi/{id}/messaggi")
	public String mostraMessaggiAUtente(@PathVariable("id") Long videogiocoId, Model model) {
		Videogioco videogioco = videogiocoService.getVideogameById(videogiocoId);
		if(videogioco == null)
			return "redirect:/error/404";
		List<Messaggio> messaggi = messaggioService.getMessaggiPerVideogioco(videogioco);

		model.addAttribute("videogioco", videogioco);
		model.addAttribute("messaggi", messaggi);
		

		return "utente/messaggi.html";
	}
	

	@PostMapping("/utente/videogiochi/{id}/messaggi")
	public String salvaMessaggio(@PathVariable("id") Long videogiocoId,
			@RequestParam("descrizione") String descrizione) {
		try {
			Utente user = utenteService.getUtenteCorrente();
			
			if(user == null)
				return "redirect:/login/utente";
			if(!this.videogiocoService.existsVideogioco(videogiocoId))
				return "redirect:/utente/error/404";
			Messaggio messaggio = new Messaggio(LocalTime.now(), LocalDate.now(), descrizione,
					videogiocoService.getVideogameById(videogiocoId), user);
			messaggioService.salvaMessaggio(messaggio);
			return "redirect:/utente/videogiochi/{id}/messaggi";
		} catch (Exception e) {
			return "redirect:/utente/error/500";
		}
	}
	
	@GetMapping("/operatore/videogiochi/{id}/messaggi")
	public String mostraMessaggiAOperatore(@PathVariable("id") Long videogiocoId, Model model) {
		if (!verificaAdmin())
			return "redirect:/login";
		Videogioco videogioco = videogiocoService.getVideogameById(videogiocoId);
		if(videogioco == null)
			return "redirect:/operatore/error/404";
		List<Messaggio> messaggi = messaggioService.getMessaggiPerVideogioco(videogioco);
		
		model.addAttribute("videogioco", videogioco);
		model.addAttribute("messaggi", messaggi);
		

		return "operatore/messaggi.html";
	}
	
	@GetMapping("/operatore/videogiochi/{idVideogioco}/messaggi/{idMessaggio}/delete")
	public String cancellaMessaggio(@PathVariable("idVideogioco") Long videogiocoId, @PathVariable("idMessaggio") Long messaggioId, Model model) {
		try {
			if (!verificaAdmin())
				return "redirect:/login";
			if(!this.videogiocoService.existsVideogioco(videogiocoId) || !this.messaggioService.existsMessaggio(messaggioId))
				return "redirect:/operatore/error/404";
			Messaggio messaggio = this.messaggioService.getMessaggio(messaggioId);
			if(messaggio == null || !(messaggio.getVideogioco().getId().equals(videogiocoId)))
				return "redirect:/operatore/videogiochi/" + videogiocoId + "/messaggi";
			this.messaggioService.cancellaMessaggio(messaggio);
			return "redirect:/operatore/videogiochi/" + videogiocoId + "/messaggi";
		} catch (Exception e) {
			return "redirect:/operatore/error/500";
		}
	}
	
}
