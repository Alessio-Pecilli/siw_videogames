package uniroma3.siw.videogames.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uniroma3.siw.videogames.model.Messaggio;
import uniroma3.siw.videogames.model.Utente;
import uniroma3.siw.videogames.model.Videogioco;
import uniroma3.siw.videogames.repository.MessaggioRepository;

@Service
public class MessaggioService {
    @Autowired
    private MessaggioRepository messaggioRepository;

    public List<Messaggio> getMessaggiPerVideogioco(Videogioco videogioco) {
        return messaggioRepository.findByVideogiocoOrderByDataAscOraAsc(videogioco);
    }

    public List<Messaggio> getMessaggiPerUtente(Utente utente) {
        return messaggioRepository.findByUtente(utente);
    }

    public void salvaMessaggio(Messaggio messaggio) {
       
        messaggioRepository.save(messaggio); 
    }

    public void cancellaMessaggio(Messaggio messaggio) {
        messaggioRepository.delete(messaggio);
    }

    public Messaggio getMessaggio(Long id) {
        return messaggioRepository.findById(id).orElse(null);
    }

	public boolean existsMessaggio(Long messaggioId) {
		return this.messaggioRepository.existsById(messaggioId);
	}
}
