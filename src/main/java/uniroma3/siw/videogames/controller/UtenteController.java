package uniroma3.siw.videogames.controller;


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
import org.springframework.web.bind.annotation.RequestParam;

import static uniroma3.siw.videogames.model.Credentials.ADMIN_ROLE;

import jakarta.validation.Valid;
import uniroma3.siw.videogames.model.Credentials;
import uniroma3.siw.videogames.model.Utente;
import uniroma3.siw.videogames.service.CredentialsService;
import uniroma3.siw.videogames.service.UtenteService;


@Controller
public class UtenteController {

	@Autowired
	private UtenteService utenteService;

	@Autowired
	private CredentialsService credentialsService;

	private boolean verificaId(Long id) {
		return id == this.utenteService.getUtenteCorrente().getId();
	}

	private boolean verificaAdmin() {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return credentialsService.getCredentialsByUsername(userDetails.getUsername()).getRole().equals(ADMIN_ROLE);
	}

	@GetMapping("/utente/{id}")
	public String showUtente(@PathVariable("id") Long id,
			@RequestParam(value = "showPasswordModal", required = false, defaultValue = "false") boolean showPasswordModal,
			Model model) {
		if (!verificaId(id))
			return "redirect:/login";
		
		model.addAttribute("showPasswordModal", showPasswordModal);
		return "utente/utente.html";
	}

	@GetMapping("/utente/{id}/modificaProfilo")
	public String showFormUpdateInfoUser(@PathVariable("id") Long id, Model model) {
		if (!verificaId(id))
			return "redirect:/login";
		
		return "utente/formModificaUtente.html";
	}

	@PostMapping("/utente/{id}/modificaProfilo")
	public String updateInfoUser(@PathVariable("id") Long id, @ModelAttribute @Valid Utente utente,
			BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("msgError", "Campi non validi");
			model.addAttribute("utente", utente);
			return "utente/formModificaUtente.html";
		}
		if (!verificaId(id))
			return "redirect:/login";
		this.utenteService.saveUtente(utente);
		return "redirect:/utente/" + utente.getId();
	}

	@PostMapping("/utente/{id}/cambiaPassword")
	public String updateCredentialsUser(@PathVariable("id") Long id, @RequestParam @Valid String confirmPwd,
			@RequestParam @Valid String newPwd, Model model) {
		if (newPwd == null || confirmPwd == null || newPwd.equals("") || confirmPwd.equals("")
				|| !newPwd.equals(confirmPwd)) {
			model.addAttribute("msgError", "Il campo della nuova password è vuota");
			model.addAttribute("utente", this.utenteService.getUtenteCorrente());
			model.addAttribute("showPasswordModal", true);
			return "utente/utente.html";
		}
		Utente utente = this.utenteService.getUtenteCorrente();
		if (!verificaId(id))
			return "redirect:/login";
		Credentials credentials = this.credentialsService.getCredentialsByUtente(utente);
		credentials.setPassword(newPwd);
		this.credentialsService.saveCredentials(credentials);
		return "redirect:/utente/" + utente.getId();
	}

	@GetMapping("/operatore/{id}")
	public String showOperatore( @PathVariable("id") Long id, 
			@RequestParam(value = "showPasswordModal", required = false, defaultValue = "false") boolean showPasswordModal,
			Model model) {
		if (!verificaId(id) || !verificaAdmin())
			return "redirect:/login";
		
		model.addAttribute("showPasswordModal", showPasswordModal);
		return "operatore/utente.html";
	}

	@GetMapping("/operatore/{id}/modificaProfilo")
	public String showFormUpdateInfoAdmin(@PathVariable("id") Long id, Model model) {
		if (!verificaId(id) || !verificaAdmin())
			return "redirect:/login";
		
		return "operatore/formModificaUtente.html";
	}

	@PostMapping("/operatore/{id}/modificaProfilo")
	public String updateInfoAdmin(@PathVariable("id") Long id, @ModelAttribute @Valid Utente utente,
			BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("msgError", "Campi non validi");
			model.addAttribute("utente", utente);
			return "operatore/formModificaUtente.html";
		}

		if (!verificaId(id) || !verificaAdmin())
			return "redirect:/login";
		this.utenteService.saveUtente(utente);
		return "redirect:/operatore/" + utente.getId();
	}

	@PostMapping("/operatore/{id}/cambiaPassword")
	public String updateCredentialsAdmin(@PathVariable("id") Long id, @RequestParam @Valid String confirmPwd,
			@RequestParam @Valid String newPwd, Model model) {
		if (newPwd == null || confirmPwd == null || newPwd.equals("") || confirmPwd.equals("")
				|| !newPwd.equals(confirmPwd)) {
			model.addAttribute("msgError", "Il campo della nuova password è vuota");
			model.addAttribute("showPasswordModal", true);
			model.addAttribute("utente", this.utenteService.getUtenteCorrente());
			return "operatore/utente.html";
		}
		Utente utente = this.utenteService.getUtenteCorrente();
		Credentials credentials = this.credentialsService.getCredentialsByUtente(utente);
		if (!verificaId(id) || !verificaAdmin())
			return "redirect:/login";
		credentials.setPassword(newPwd);
		this.credentialsService.saveCredentials(credentials);
		return "redirect:/operatore/" + utente.getId();
	}
}
