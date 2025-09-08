package uniroma3.siw.videogames.service;

import java.util.List;
import java.util.Optional;
import java.util.Locale.Category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uniroma3.siw.videogames.repository.VideogiocoRepository;
import uniroma3.siw.videogames.model.Categoria;
import uniroma3.siw.videogames.model.Stato;
import uniroma3.siw.videogames.model.Videogioco;


@Service
public class VideogiocoService {

	@Autowired
	private VideogiocoRepository videogiocoRepository;

	public List<Videogioco> getAllVideogames() {
		return (List<Videogioco>) videogiocoRepository.findAll();
	}

	public List<Videogioco> getByStato(Stato stato) {
	return videogiocoRepository.findByStato(stato);
	}

    public List<Videogioco> getByCategoria(Categoria categoria) {
        return videogiocoRepository.findByCategorie(categoria);
    }

    public List<Videogioco> getByCategorie(List<Categoria> categorie) {
        return videogiocoRepository.findByCategorieIn(categorie);
    }

	public Videogioco getVideogameById(Long id) {
		try {
			return videogiocoRepository.findById(id).get();
		} catch (Exception e) {
			return null;
		}
	}

	public void salvaNuovoVideogame(Videogioco videogioco) {
        videogiocoRepository.save(videogioco);
    }

	public void eliminaVideogioco(Long id) {
    videogiocoRepository.deleteById(id);
	}

	public boolean existsVideogioco(Long idVideogioco) {
		return this.videogiocoRepository.existsById(idVideogioco);
	}

}
