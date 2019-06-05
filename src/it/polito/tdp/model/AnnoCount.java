package it.polito.tdp.model;

public class AnnoCount {
	
	private int anno;
	private int nCrimini;
	
	public AnnoCount(int anno, int nCrimini) {
		super();
		this.anno = anno;
		this.nCrimini = nCrimini;
	}

	public int getAnno() {
		return anno;
	}

	public void setAnno(int anno) {
		this.anno = anno;
	}

	public int getnCrimini() {
		return nCrimini;
	}

	public void setnCrimini(int nCrimini) {
		this.nCrimini = nCrimini;
	}

	@Override
	public String toString() {
		return anno + "(nCrimini=" + nCrimini+")";
	}

	
	

}
