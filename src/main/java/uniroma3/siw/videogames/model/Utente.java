package uniroma3.siw.videogames.model;

import java.util.List;
import java.util.Objects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotEmpty;

@Entity
public class Utente {
    
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@NotEmpty
    private String nome;
    @NotEmpty
    private String cognome;
    @NotEmpty
    @Column(unique = true)
    private String email;

    
    @OneToMany(mappedBy = "utente", cascade = CascadeType.ALL)
    private List<Recensione> recensioni;

    @OneToMany(mappedBy = "utente", cascade = CascadeType.ALL)
    private List<Messaggio> messaggi;

    public Utente() {
    	this(null, null, null);
    }
    
    public Utente(String nome, String cognome, String email) {
    	this.nome = nome;
    	this.cognome = cognome;
    	this.email = email;
    }
    
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	@Override
	public int hashCode() {
		return Objects.hash(cognome, email, nome);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Utente other = (Utente) obj;
		return  Objects.equals(email, other.email);
	}
    
}
