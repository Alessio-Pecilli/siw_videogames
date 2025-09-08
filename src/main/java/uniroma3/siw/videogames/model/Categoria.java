package uniroma3.siw.videogames.model;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

@Entity
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String nome;

    @ManyToMany(mappedBy = "categorie")
    private List<Videogioco> videogiochi;

    // Getter e Setter
    public Long getId() {
    	return id;
    }
    
    public void setId(Long id) {
    	this.id = id;
    }

    public String getNome() {
    	return nome;
    }
    
    public void setNome(String nome) {
    	this.nome = nome; 
    }

    public List<Videogioco> getVideogiochi() { 
    	return videogiochi; 
    }
    
    public void setVideogiochi(List<Videogioco> videogiochi) {
    	this.videogiochi = videogiochi;
    }

	@Override
	public int hashCode() {
		return Objects.hash(nome);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Categoria other = (Categoria) obj;
		return Objects.equals(nome, other.nome);
	}
    
    
}
