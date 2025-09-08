package uniroma3.siw.videogames.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uniroma3.siw.videogames.model.Categoria;
import uniroma3.siw.videogames.model.Stato;
import uniroma3.siw.videogames.repository.CategoriaRepository;

@Service
public class CategoriaService {
    @Autowired
    private CategoriaRepository categoriaRepository;

    public List<Categoria> getAllCategorie() {
        return (List<Categoria>) categoriaRepository.findAll();
    }

    public Categoria getById(Long id) {
		return categoriaRepository.findById(id).orElse(null);
	}

	public Iterable<Categoria> getCategorieById(List<Long> categorieId) {
		return  categoriaRepository.findAllById(categorieId);
	}
}
