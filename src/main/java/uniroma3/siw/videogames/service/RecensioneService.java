package uniroma3.siw.videogames.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uniroma3.siw.videogames.model.Recensione;
import uniroma3.siw.videogames.model.Utente;
import uniroma3.siw.videogames.model.Videogioco;
import uniroma3.siw.videogames.repository.RecensioneRepository;
@Service
public class RecensioneService {
    
    @Autowired
    private RecensioneRepository recensioneRepository;

    public List<Recensione> getRecensioniPerVideogioco(Videogioco videogioco) {
        return recensioneRepository.findByVideogiocoOrderByDataAscOraAsc(videogioco);
    }

    public List<Recensione> getRecensioniPerUtente(Utente utente) {
        return recensioneRepository.findByUtente(utente);
    }

    public void salvaRecensione(Recensione recensione) {
       
        recensioneRepository.save(recensione); 
    }

    public void eliminaPerId(Long id) {
        recensioneRepository.deleteById(id);
    }

    public Recensione getRecensione(Long id) {
        return recensioneRepository.findById(id).orElse(null);
    }

    

	public Recensione getRecensioniPerUtenteEVideogioco(Utente utente, Videogioco videogioco) {
		try {
			return this.recensioneRepository.findByUtenteAndVideogioco(utente, videogioco).get();
		} catch (Exception e) {
			return null;
		}
	}

	public boolean existsRecensioneOfVideogioco(Long idRecensione, Long idVideogioco) {
		try {
			Recensione recensione = this.recensioneRepository.findById(idRecensione).orElse(null);
			return recensione.getVideogioco().getId() == idVideogioco;
		} catch (Exception e) {
			return false;
		}
	}
    
}
