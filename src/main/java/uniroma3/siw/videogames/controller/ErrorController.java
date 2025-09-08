package uniroma3.siw.videogames.controller;

import static uniroma3.siw.videogames.model.Credentials.ADMIN_ROLE;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import uniroma3.siw.videogames.model.HttpStatusError;
import uniroma3.siw.videogames.model.Utente;
import uniroma3.siw.videogames.service.CredentialsService;
import uniroma3.siw.videogames.service.UtenteService;

@Controller
public class ErrorController {
	@Autowired
	public UtenteService utenteService;
	@Autowired
	public CredentialsService credentialsService;

	private boolean verificaAdmin() {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return this.credentialsService.getCredentialsByUsername(userDetails.getUsername()).getRole().equals(ADMIN_ROLE);
	}

	@GetMapping("/error/{status}")
	public String showError(@PathVariable("status") Long status, Model model) {
		model.addAttribute("status", status);
		model.addAttribute("error",  HttpStatusError.getDescriptionByCode(status));
		return "error.html";
	}
	
	@GetMapping("/utente/error/{status}")
	public String showErrorUser(@PathVariable("status") Long status, Model model) {
		Utente utente = this.utenteService.getUtenteCorrente();
		if(utente == null)
			return "redirect:/error/" + status;
		
		model.addAttribute("status", status);
		model.addAttribute("error",  HttpStatusError.getDescriptionByCode(status));
		return "utente/error.html";
	}
	
	@GetMapping("/operatore/error/{status}")
	public String showErrorAdmin(@PathVariable("status") Long status, Model model) {
		Utente utente = this.utenteService.getUtenteCorrente();
		if(utente == null || !verificaAdmin())
			return "redirect:/error/" + status;
		
		model.addAttribute("status", status);
		model.addAttribute("error",  HttpStatusError.getDescriptionByCode(status));
		return "operatore/error.html";
	}
	
}