package it.polito.tdp.model;

import java.time.Duration;
import java.time.LocalTime;

public class Evento implements Comparable<Evento>{
	
	public enum TipoEvento{
		CRIMINE,
		INTERVENTO_AGENTE,
		FINE_INTERVENTO
	}

	private TipoEvento tipo;
	private LocalTime tempoInizio;
	private LocalTime tempoFine;
	private Duration durataIntervento;
	private int idAgente;
	
	public Evento(TipoEvento tipo, LocalTime tempoInizio, LocalTime tempoFine, Duration durataIntervento,
			int idAgente) {
		super();
		this.tipo = tipo;
		this.tempoInizio = tempoInizio;
		this.tempoFine = tempoFine;
		this.durataIntervento = durataIntervento;
		this.idAgente = idAgente;
	}

	


	public TipoEvento getTipo() {
		return tipo;
	}




	public void setTipo(TipoEvento tipo) {
		this.tipo = tipo;
	}




	public LocalTime getTempoInizio() {
		return tempoInizio;
	}




	public void setTempoInizio(LocalTime tempoInizio) {
		this.tempoInizio = tempoInizio;
	}




	public LocalTime getTempoFine() {
		return tempoFine;
	}




	public void setTempoFine(LocalTime tempoFine) {
		this.tempoFine = tempoFine;
	}




	public Duration getDurataIntervento() {
		return durataIntervento;
	}




	public void setDurataIntervento(Duration durataIntervento) {
		this.durataIntervento = durataIntervento;
	}




	public int getIdAgente() {
		return idAgente;
	}




	public void setIdAgente(int idAgente) {
		this.idAgente = idAgente;
	}




	@Override
	public int compareTo(Evento other) {
		// TODO Auto-generated method stub
		return this.tempoInizio.compareTo(other.getTempoInizio());
	}
	
}
