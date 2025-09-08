package uniroma3.siw.videogames.repository;

import org.springframework.data.repository.CrudRepository;

import uniroma3.siw.videogames.model.Utente;
import java.util.List;
import java.util.Optional;


public interface UtenteRepository extends CrudRepository<Utente, Long> {

}
