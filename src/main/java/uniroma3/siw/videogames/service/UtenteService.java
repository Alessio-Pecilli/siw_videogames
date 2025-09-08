package uniroma3.siw.videogames.service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import uniroma3.siw.videogames.model.Utente;
import uniroma3.siw.videogames.repository.UtenteRepository;

@Service
public class UtenteService {

    @Autowired
    private UtenteRepository utenteRepository;
    
    @Autowired
	private CredentialsService credentialsService;

    public List<Utente> findAll() {
        return (List<Utente>) utenteRepository.findAll();
    }

    public Utente findById(Long id) {
        Optional<Utente> result = utenteRepository.findById(id);
        return result.orElse(null);
    }

    public Utente saveUtente(Utente utente) {
        return utenteRepository.save(utente);
    }

    public void deleteById(Long id) {
        utenteRepository.deleteById(id);
    }

    public Utente getUtenteCorrente() {
    	UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	return this.credentialsService.getCredentialsByUsername(userDetails.getUsername()).getUtente();
    }
}
