package uniroma3.siw.videogames.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Entity
public class Videogioco {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @NotEmpty
    private String titolo;
    @NotNull
    @NotEmpty
    @Column(length = 2000)
    private String descrizione;
    @NotNull
    @NotEmpty
    @Column(length = 2000)
    private String urlImmagine;
    @NotNull
    @ManyToOne
    private Stato stato;
    @ManyToMany
    @JoinTable(
        name = "videogioco_categoria",
        joinColumns = @JoinColumn(name = "videogioco_id"),
        inverseJoinColumns = @JoinColumn(name = "categoria_id")
    )
    private List<Categoria> categorie;
    @OneToMany(mappedBy = "videogioco", cascade = {CascadeType.ALL})
    private List<Recensione> recensioni;

    @OneToMany(mappedBy = "videogioco", cascade = {CascadeType.ALL})
    private List<Messaggio> messaggi;

    public Videogioco() {
    	this(null, null, null, null, null);
    }
    
    
    
    public Videogioco(String titolo, String descrizione, String urlImmagine, Stato stato, List<Categoria> categorie) {
		this.titolo = titolo;
		this.descrizione = descrizione;
		this.urlImmagine = urlImmagine;
		this.stato = stato;
		this.messaggi = new ArrayList<>();
    	this.recensioni = new ArrayList<>();
        this.categorie = new ArrayList<>();
        if(categorie != null)
        	this.categorie.addAll(categorie);
	}



	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public Stato getStato() {
        return stato;
    }

    public void setStato(Stato stato) {
        this.stato = stato;
    }

	public List<Recensione> getRecensioni() {
		return recensioni;
	}

	public void setRecensioni(List<Recensione> recensioni) {
		this.recensioni = recensioni;
	}

	public List<Messaggio> getMessaggi() {
		return messaggi;
	}

	public void setMessaggi(List<Messaggio> messaggi) {
		this.messaggi = messaggi;
	}
	
	public String getUrlImmagine() {
		return urlImmagine;
	}

	public void setUrlImmagine(String urlImmagine) {
		this.urlImmagine = urlImmagine;
	}

    public List<Categoria> getCategorie() {
        return this.categorie;
    }
    
    public void setCategorie(List<Categoria> categorie) {
        this.categorie = categorie;
    }
    

	@Override
	public int hashCode() {
		return Objects.hash(descrizione, stato, titolo);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Videogioco other = (Videogioco) obj;
		return Objects.equals(descrizione, other.descrizione) && Objects.equals(stato, other.stato)
				&& Objects.equals(titolo, other.titolo);
	}
    
	public float getMedia() {
		float res = 0;
		for(Recensione r : recensioni)
			res += r.getVoto();
		return res/(this.recensioni.size());
	}

}