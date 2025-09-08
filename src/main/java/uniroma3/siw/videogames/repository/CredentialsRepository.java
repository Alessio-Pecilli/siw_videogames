package uniroma3.siw.videogames.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import uniroma3.siw.videogames.model.Credentials;
import uniroma3.siw.videogames.model.Utente;

import java.util.List;


public interface CredentialsRepository extends CrudRepository<Credentials, Long> {
	
	public Optional<Credentials> findByUsername(String username);
	
	public Optional<Credentials> findByUtente(Utente utente);
	
	public boolean existsByUsername(String username);
}
