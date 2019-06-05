package it.polito.tdp.model;

import com.javadocmd.simplelatlng.LatLng;

public class Distretto {
	private int id;
	private int nCrimini;
	private int anno;
	private LatLng centroGeografico;
	
	public Distretto(int id, int nCrimini, int anno, double latitude, double longitude) {
		this.id=id;
		this.nCrimini=nCrimini;
		this.anno=anno;
		centroGeografico=new LatLng(latitude, longitude);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getnCrimini() {
		return nCrimini;
	}

	public void setnCrimini(int nCrimini) {
		this.nCrimini = nCrimini;
	}

	public int getAnno() {
		return anno;
	}

	public void setAnno(int anno) {
		this.anno = anno;
	}

	public LatLng getCentroGeografico() {
		return centroGeografico;
	}

	public void setCentroGeografico(LatLng centroGeografico) {
		this.centroGeografico = centroGeografico;
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
		Distretto other = (Distretto) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return String.format("Distretto [id=%s, nCrimini=%s, anno=%s, centroGeografico=%s]", id, nCrimini, anno,
				centroGeografico);
	}
	
	

}
