package uniroma3.siw.videogames.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import uniroma3.siw.videogames.model.Messaggio;
import uniroma3.siw.videogames.model.Utente;
import uniroma3.siw.videogames.model.Videogioco;

public interface MessaggioRepository extends CrudRepository<Messaggio, Long>{
    List<Messaggio> findByVideogioco(Videogioco videogioco);
    List<Messaggio> findByUtente(Utente utente);
    List<Messaggio> findByVideogiocoOrderByDataAscOraAsc(Videogioco videogioco);
    @Query("SELECT MAX(m.id) FROM Messaggio m")
    Optional<Long> findMaxId();
}
