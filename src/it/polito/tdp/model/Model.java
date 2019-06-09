package it.polito.tdp.model;

import java.time.LocalDateTime;
import java.util.*;

import org.jgrapht.*;
import org.jgrapht.graph.*;

import com.javadocmd.simplelatlng.LatLngTool;
import com.javadocmd.simplelatlng.util.LengthUnit;

import it.polito.tdp.db.EventsDao;

public class Model {
	Graph<Distretto, DefaultWeightedEdge>grafo;
	EventsDao dao;
	List<Distretto> distretti;
	
	Simulatore sim;
	
	public Model(){
		dao=new EventsDao();
		
	}

	public List<AnnoCount> getAnni() {
		return dao.getAnni();
	}

	public void creaGrafo(AnnoCount annoScelto) {
		// TODO Auto-generated method stub
		grafo=new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		distretti=new ArrayList<>(dao.getDistrettiAnno(annoScelto.getAnno()));
		//Aggiungo i vertici
		Graphs.addAllVertices(this.grafo, distretti);
		//Faccio una stampa per vedere se ha creato i vertici
		System.out.println(grafo.vertexSet().size()+" vertici aggiunti");
		
		//Ora devo aggiungere gli archi pesati
		//Il peso di ogni arco � dato dalla distanza tra il centro di un ditretto e un altro
		
		
		for(Distretto d: grafo.vertexSet()) {
			for(Distretto d1: grafo.vertexSet()) {
				if(!d.equals(d1) && !grafo.containsEdge(d, d1)) {
					Graphs.addEdge(this.grafo, d, d1, LatLngTool.distance(d.getCentroGeografico(), d1.getCentroGeografico(), LengthUnit.KILOMETER));
				}
			}
		}
		
		System.out.println(grafo.edgeSet().size()+" archi aggiunti");
	
	}
	
	
	public Map<Distretto, List<Distretto>> getDistrettiOrdinati(){
		Map<Distretto, List<Distretto>> mappaVicini = new HashMap<Distretto, List<Distretto>>();
		
		for(Distretto v: grafo.vertexSet()) {
			List<Distretto> vicini=Graphs.neighborListOf(this.grafo, v);
			Collections.sort(vicini, new Comparator<Distretto>() {

				@Override
				public int compare(Distretto o1, Distretto o2) {
					DefaultWeightedEdge e1 = grafo.getEdge(v, o1);
					DefaultWeightedEdge e2 = grafo.getEdge(v, o2);
					Double peso1 = grafo.getEdgeWeight(e1);
					Double peso2 = grafo.getEdgeWeight(e2);
					return peso1.compareTo(peso2);
				}
			});
			
			mappaVicini.put(v, vicini);
			
		}
		return mappaVicini;
	}
	
	public Double getPesoArco(Distretto d1, Distretto d2) {
		return grafo.getEdgeWeight(grafo.getEdge(d1, d2));
	}

	public int getNVertici() {
		// TODO Auto-generated method stub
		return this.grafo.vertexSet().size();
	}
	public int getNArchi() {
		// TODO Auto-generated method stub
		return this.grafo.edgeSet().size();
	}

	public Graph<Distretto, DefaultWeightedEdge> getGrafo() {
		return grafo;
	}

	public EventsDao getDao() {
		return dao;
	}

	public List<Distretto> getDistretti() {
		return distretti;
	}

	public List<Integer> getMesi() {
		// TODO Auto-generated method stub
		List<Integer>result=new ArrayList<Integer>();
		for(int i=1; i<=12; i++)
			result.add(i);
		return result;
	}
	
	public List<Integer> getGiorni() {
		// TODO Auto-generated method stub
		List<Integer>result=new ArrayList<Integer>();
		for(int i=1; i<=31; i++)
			result.add(i);
		return result;
	}

	public boolean cercaData(int annoScelto, int meseScelto, int giornoScelto) {
		// TODO Auto-generated method stub
		return dao.cercaData(annoScelto, meseScelto, giornoScelto);
	}

	public Distretto getDistrettoMenoCriminalita() {
		// TODO Auto-generated method stub
		Distretto min = new Distretto(Integer.MIN_VALUE);
		System.out.println("Il distretto con minor criminalita' e': "+min.toString());
		for(Distretto d: distretti) {
			if(d.getnCrimini()>min.getnCrimini())
				min=d;
		}
    	System.out.println("Il distretto con minor criminalita' e': "+min.toString());

		return min;
	}
	
	
	public void simula(Distretto partenza, int nAgenti, int annoScelto, int meseScelto, int giornoScelto) {
		sim=new Simulatore();
		
		//Al mio simulatore devo passare il nodo di partenza, il nAgentiTotale inserito dall'utente e la data inserita
		sim.init(grafo, partenza, nAgenti, annoScelto, meseScelto, giornoScelto);
		
		sim.run();		
	}

	public int getEventiMalGestiti() {
		// TODO Auto-generated method stub
		return sim.getEventiMalGestiti();
	}
}
