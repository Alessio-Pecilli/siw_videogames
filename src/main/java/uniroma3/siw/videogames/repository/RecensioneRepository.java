package uniroma3.siw.videogames.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import uniroma3.siw.videogames.model.Recensione;
import uniroma3.siw.videogames.model.Utente;
import uniroma3.siw.videogames.model.Videogioco;

public interface RecensioneRepository extends CrudRepository<Recensione, Long> {
    List<Recensione> findByVideogioco(Videogioco videogioco);
    List<Recensione> findByUtente(Utente utente);
    List<Recensione> findByVideogiocoOrderByDataAscOraAsc(Videogioco videogioco);
    @Query("SELECT MAX(r.id) FROM Recensione r")
    Optional<Long> findMaxId();
    Optional<Recensione> findByUtenteAndVideogioco(Utente utente, Videogioco videogioco);
}
