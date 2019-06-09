package it.polito.tdp.model;

public class Poliziotto {
	
	private int id;
	private boolean disponibile;
	private Distretto posizione;
	
	public Poliziotto(int id, Distretto partenza) {
		super();
		this.id = id;
		this.disponibile = true;
		this.posizione= partenza;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public boolean isDisponibile() {
		return disponibile;
	}
	public void setDisponibile(boolean disponibile) {
		this.disponibile = disponibile;
	}
	
	public Distretto getPosizione() {
		return posizione;
	}
	public void setPosizione(Distretto posizione) {
		this.posizione = posizione;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Poliziotto other = (Poliziotto) obj;
		if (id != other.id)
			return false;
		return true;
	}
	@Override
	public String toString() {
		return String.format("Poliziotto [id=%s, disponibile=%s, posizione=%s]", id, disponibile, posizione);
	}
	

	
}
