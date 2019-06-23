package it.polito.tdp.model;

import com.javadocmd.simplelatlng.util.LengthUnit;

public class Vicino implements Comparable<Vicino> {
	
	private Distretto distretto;
	private Double distanza;
	
	public Vicino(Distretto distretto, Double distanza) {
		super();
		this.distretto = distretto;
		this.distanza = distanza;
	}

	public Distretto getDistretto() {
		return distretto;
	}

	public void setDistretto(Distretto distretto) {
		this.distretto = distretto;
	}

	public Double getDistanza() {
		return distanza;
	}

	public void setDistanza(Double distanza) {
		this.distanza = distanza;
	}
	

	@Override
	public int compareTo(Vicino other) {
		// TODO Auto-generated method stub
		return this.getDistanza().compareTo(other.getDistanza());
	}
	
	
	
	

}
