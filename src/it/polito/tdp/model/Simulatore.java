package it.polito.tdp.model;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.*;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;

import it.polito.tdp.db.EventsDao;
import it.polito.tdp.model.Evento.TipoEvento;

public class Simulatore {
	
	//Coda
	private PriorityQueue<Evento>queue;
	
	//Stato del mondo
	private List<Poliziotto> listaAgenti = new ArrayList<>();
	private Graph<Distretto, DefaultWeightedEdge>grafo;
	
	//Parametri di soluzione
	private int nAgentiTot;
	private int nAgentiDisponibili;
	private LocalDate dataSelezionata;
	private Distretto partenza;
	private List<Event> events;
	private List<Evento> eventiGiorno;
	private Map<Distretto, Integer> agentiStanziali;
	private Map<Integer, Poliziotto> mappaAgenti;
	
	//Statistiche
	private int malGestiti;

	private EventsDao dao;
	
	public Simulatore() {
	}
	
	public void init(Graph<Distretto, DefaultWeightedEdge> grafo, Distretto partenza, int nAgenti, int annoScelto, int meseScelto, int giornoScelto) {
		this.grafo=grafo;//questo mi serve uguale a quello creato in precedenza perche' rappresenta l'anno selezionato
		eventiGiorno=new ArrayList<Evento>();
		//Salvo il nAgenti all'interno dei distretti
		//All'inizio sono tutti nel distretto a minor criminalita
		for(Distretto d: grafo.vertexSet()) {
			if(d==partenza) {
				d.setnAgenti(nAgenti);
			}
			else
				d.setnAgenti(0);
		}
		
		//Provo a farlo anche con la mappa di stanziali
		agentiStanziali=new HashMap<Distretto, Integer>();
		for(Distretto d: this.grafo.vertexSet()) {
			if(!d.equals(partenza))
				agentiStanziali.put(d, 0);
			else
				agentiStanziali.put(d, nAgentiTot);
		}
		
		queue = new PriorityQueue<Evento>();
		
		//Adesso per riempire la coda con gli eventi devo prima andarmi a prendere tutti gli eventi accaduti in un certo giorno
		events=dao.getEventiGiorno(annoScelto, meseScelto, giornoScelto);
		
		//Dopodiche creo gli eventi e li aggiungo la coda
		for(Event e: events) {
			LocalTime oraInizio=e.getReported_date().toLocalTime();
			Duration durata = calcolaDurataIntervento(e.getOffense_category_id());
			eventiGiorno.add(new Evento(TipoEvento.CRIMINE, oraInizio, oraInizio.plus(durata), durata, 0));
			//Inizialmente nella coda metto solo eventi di tipo Crimine
			//Quando faro' run creero' gli eventi di inizio e fine intervento
		}
		
		//Mi creo una mappaDiAgenti
		mappaAgenti=new HashMap<>();
		for(int i=1; i<=nAgentiTot+1; i++)
		mappaAgenti.put(i, new Poliziotto(i, partenza));
		
		
	}
	
	private Duration calcolaDurataIntervento(String offense_category_id) {
		// TODO Auto-generated method stub
		Duration result;
		Random rand = new Random();
		int prob=rand.nextInt(1);
		if(offense_category_id=="all_other_crimes") {
			if(prob<0.5)
				result=Duration.of(1, ChronoUnit.HOURS);
			else
				result=Duration.of(2, ChronoUnit.HOURS);
		}
			
		else {
			result=Duration.of(2, ChronoUnit.HOURS);
		}
		return result;
	}



	public void run() {
		
		
		
		
		
	}



	public int getEventiMalGestiti() {
		// TODO Auto-generated method stub
		return malGestiti;
	}

}
