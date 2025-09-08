package uniroma3.siw.videogames.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Messaggio {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@PastOrPresent
	private LocalTime ora;
	@PastOrPresent
	private LocalDate data;
	@NotBlank
	private String descrizione;
	@ManyToOne
	private Videogioco videogioco;
	@ManyToOne
	private Utente utente;

	public Messaggio() {
		this(null, null, null, null, null);
	}

	public Messaggio(LocalTime ora, LocalDate data, Videogioco videogioco, Utente utente) {
		this(ora, data, null, videogioco, utente);
	}

	public Messaggio(LocalTime ora, LocalDate data, String descrizione, Videogioco videogioco, Utente utente) {
		this.ora = ora == null ? null : ora.truncatedTo(ChronoUnit.MINUTES);
		this.data = data;
		this.descrizione = descrizione;
		this.videogioco = videogioco;
		this.utente = utente;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalTime getOra() {
		return ora;
	}

	public void setOra(LocalTime ora) {
		this.ora = ora.truncatedTo(ChronoUnit.MINUTES);
	}

	public LocalDate getData() {
		return data;
	}

	public void setData(LocalDate data) {
		this.data = data;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public Videogioco getVideogioco() {
		return videogioco;
	}

	public void setVideogioco(Videogioco videogioco) {
		this.videogioco = videogioco;
	}

	public Utente getUtente() {
		return utente;
	}

	public void setUtente(Utente utente) {
		this.utente = utente;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		Messaggio other = (Messaggio) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

}