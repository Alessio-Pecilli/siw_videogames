package uniroma3.siw.videogames.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

@Entity
public class Recensione {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String feedback;
    @NotNull
    @ManyToOne
    private Videogioco videogioco;
    @NotNull
    @Min(0)
    @Max(5)
    private Integer voto;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "utente_id", nullable = false)
    private Utente utente;
    @PastOrPresent
    private LocalTime ora;
    @PastOrPresent
    private LocalDate data;

    public Recensione() {
		this(null, null, null, null, null, null);
	}

    public Recensione(LocalTime ora, LocalDate data, String feedback, Videogioco videogioco, Utente utente, Integer voto) {
    	this.ora = ora == null ? null : ora.truncatedTo(ChronoUnit.MINUTES);
		this.data = data;
		this.feedback = feedback;
		this.videogioco = videogioco;
		this.utente = utente;
		this.voto = voto;
	}

    public LocalDate getData() {
        return data;
    }

    public LocalTime getOra() {
        return ora;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public void setOra(LocalTime ora) {
        this.ora = ora;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public Videogioco getVideogioco() {
        return videogioco;
    }

    public void setVideogioco(Videogioco videogioco) {
        this.videogioco = videogioco;
    }

    public Integer getVoto() {
        return voto;
    }

    public void setVoto(Integer voto) {
        this.voto = voto;
    }

    public Utente getUtente() {
        return utente;
    }

    public void setUtente(Utente utente) {
        this.utente = utente;
    }

	@Override
	public int hashCode() {
		return Objects.hash(feedback, utente, videogioco);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Recensione other = (Recensione) obj;
		return Objects.equals(feedback, other.feedback) && Objects.equals(utente, other.utente)
				&& Objects.equals(videogioco, other.videogioco);
	}
    
}