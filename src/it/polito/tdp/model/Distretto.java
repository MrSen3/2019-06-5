package it.polito.tdp.model;

import java.util.*;

import com.javadocmd.simplelatlng.LatLng;

public class Distretto {
	private int id;
	private int nCrimini;
	private int anno;
	private LatLng centroGeografico;
	private List<Vicino> vicini;
	private List<Poliziotto> poliziottiNelDistretto;
	
	public Distretto(int id, int nCrimini, int anno, double latitude, double longitude) {
		this.id=id;
		this.nCrimini=nCrimini;
		this.anno=anno;
		centroGeografico=new LatLng(latitude, longitude);
	}

	public Distretto(int minValue) {
		// TODO Auto-generated constructor stub
		this.nCrimini=minValue;
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

	
	public List<Vicino> getVicini() {
		return vicini;
	}

	public void setVicini(List<Vicino> vicini) {
		this.vicini = vicini;
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

	public void addVicino(Vicino vicino) {
		// TODO Auto-generated method stub
		this.vicini.add(vicino);
		Collections.sort(vicini);
		
	}

	public void addAllPoliziotti(List<Poliziotto> listaAgenti) {
		// TODO Auto-generated method stub
		poliziottiNelDistretto.addAll(listaAgenti);
		for(Poliziotto p: poliziottiNelDistretto) {
		System.out.println(p+"\n");}
	}
	public void addPoliziotto(Poliziotto poliziotto) {
		// TODO Auto-generated method stub
		poliziottiNelDistretto.add(poliziotto);
	}
	public void removePoliziotto() {
		// TODO Auto-generated method stub
		poliziottiNelDistretto.remove(0);
		
	}
	
	public int numPoliziottiNelDistretto() {
		return poliziottiNelDistretto.size();
	}
	
	public int numPoliziottiDisponibiliNelDistretto() {
		int count=0;
		for(Poliziotto p: poliziottiNelDistretto) {
			if(p.isDisponibile())
				count++;
		}
		return count;
	}
	
	public List<Poliziotto> getPoliziottiNelDistretto() {
		return poliziottiNelDistretto;
	}

	public void setPoliziottiNelDistretto(List<Poliziotto> poliziottiNelDistretto) {
		this.poliziottiNelDistretto = poliziottiNelDistretto;
	}
	
	

}
