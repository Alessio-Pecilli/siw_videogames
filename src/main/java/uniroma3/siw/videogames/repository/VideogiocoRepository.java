package uniroma3.siw.videogames.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import uniroma3.siw.videogames.model.Categoria;
import uniroma3.siw.videogames.model.Stato;
import uniroma3.siw.videogames.model.Videogioco;
import uniroma3.siw.videogames.model.Recensione;


public interface VideogiocoRepository extends CrudRepository<Videogioco, Long> {
    List<Videogioco> findByStato(Stato stato);

    List<Videogioco> findByCategorie(Categoria categoria);

    List<Videogioco> findByCategorieIn(List<Categoria> categorie);
    

}
