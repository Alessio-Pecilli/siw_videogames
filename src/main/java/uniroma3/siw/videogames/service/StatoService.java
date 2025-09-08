package uniroma3.siw.videogames.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uniroma3.siw.videogames.model.Stato;
import uniroma3.siw.videogames.repository.StatoRepository;

@Service
public class StatoService {

	@Autowired
	private StatoRepository statoRepository;
	
	public List<Stato> getAllStates() {
		return (List<Stato>) statoRepository.findAll();
	}

	public Stato getById(Long id) {
		return statoRepository.findById(id).orElse(null);
	}
}
