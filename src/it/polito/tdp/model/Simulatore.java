package it.polito.tdp.model;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.*;

import org.jgrapht.Graph;
import org.jgrapht.alg.interfaces.ShortestPathAlgorithm;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;

import com.javadocmd.simplelatlng.util.LengthUnit;

import it.polito.tdp.db.EventsDao;
import it.polito.tdp.model.Evento.TipoEvento;

public class Simulatore {
	
	//Coda
	private PriorityQueue<Evento>queue;
	
	//Stato del mondo
	private List<Poliziotto> listaAgenti;
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
	private Map<Integer, Distretto> idMapDistretti;
	Map<Distretto, List<Distretto>> mappaVicini
	
	//Statistiche
	private int malGestiti;

	private EventsDao dao;
	
	public Simulatore() {
		
	}
	
	public void init(Graph<Distretto, DefaultWeightedEdge> grafo, Distretto partenza, int nAgenti, Map<Integer, Distretto> idMapDistretti, Map<Distretto, List<Distretto>> mappaVicini, int annoScelto, int meseScelto, int giornoScelto, List<Event> events) {
		this.grafo=grafo;//questo mi serve uguale a quello creato in precedenza perche' rappresenta l'anno selezionato
		eventiGiorno=new ArrayList<Evento>();
		this.idMapDistretti	= new HashMap<>(idMapDistretti);
		this.listaAgenti = new ArrayList<>();
		this.mappaVicini=new HashMap<>(mappaVicini);
		//Mi creo NAgenti poliziotti e li posiziono tutti in distretto partenza
		for(int i=1; i<=nAgenti; i++) {
			listaAgenti.add(new Poliziotto(i, partenza));
		}
		//Salvo i poliziotti all'interno dei distretti
		//All'inizio sono tutti nel distretto a minor criminalita
		for(Distretto d: grafo.vertexSet()) {
			if(d==partenza) {
				//Inserisco i poliziotti nel distretto a minor criminalita
				d.addAllPoliziotti(listaAgenti);
			}	
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
		//Dopodiche creo gli eventi e li aggiungo la coda
		for(Event e: events) {
			LocalTime oraInizio=e.getReported_date().toLocalTime();
			Duration durata = calcolaDurataIntervento(e.getOffense_category_id());
			eventiGiorno.add(new Evento(TipoEvento.CRIMINE, idMapDistretti.get(e.getDistrict_id()), oraInizio, durata, 0));
			//Inizialmente nella coda metto solo eventi di tipo Crimine
			//Quando faro' run creero' gli eventi di inizio e fine intervento
		}
		
		for(Evento e: eventiGiorno) {
			System.out.println(e+ "\n");
		}
		
		//Mi creo una mappaDiAgenti
		mappaAgenti=new HashMap<>();
		for(int i=1; i<=nAgentiTot+1; i++)
			mappaAgenti.put(i, new Poliziotto(i, partenza));
		
	}
	
	//Questo funge
	private Duration calcolaDurataIntervento(String offense_category_id) {
		// TODO Auto-generated method stub
		Duration result;
		Random rand = new Random();
		int prob=rand.nextInt(10)+1;
		if(offense_category_id.compareTo("all-other-crimes")==0) {
			if(prob<=5)//se esce 1,2,3,4,5
				result=Duration.of(1, ChronoUnit.HOURS);
			else//se esce 6,7,8,9,10
				result=Duration.of(2, ChronoUnit.HOURS);
		}
			
		else {
			result=Duration.of(2, ChronoUnit.HOURS);
		}
		return result;
	}



	public void run() {
		//Estraggo un evento alla volta dalla coda e lo eseguo finche la coda non si svuota (si svuotera' quando saranno finiti gli eventi)
		List<Distretto> vicini;
		
		while(!queue.isEmpty()) {
			Evento ev = queue.poll();
			
			switch(ev.getTipo()) {
			
			case CRIMINE:
				//devo vedere se ci sono agenti disponibili, partendo dal distretto piu' vicino a quello  in cui e' avvenuto il crimine fino ad arrivare a quello piu' lontano
				boolean trovato=false;
				double distanza=0;
				
				Distretto luogoCrimine = ev.getDistrettoEvento();
				for(Distretto x: mappaVicini.keySet()) {
					if(x.equals(luogoCrimine)) {
						vicini = (mappaVicini.get(x));
					}
				}
				
				//Mi serve prima il caso in cui nel distretto del crimine ho almeno un poliziotto disponibile
				
				
				
				//In vicini mi sono salvato la lista ordinata (in ordine crescente di distanza) di distretti  
				for(Distretto d: vicini) {//scorrendo la lista di vicini del distretto sto andando dal distretto piu  vicino a quello in cui e' avvenuto il misfatto, fino al piu  distante
					//se trovo un poliziotto disponibile
					if(d.numPoliziottiDisponibiliNelDistretto()>0) {
						trovato=true;
						//rimuovo l'agente arrivato in quel distretto per primo e lo aggiungero' al distretto destinazione
						d.removePoliziotto();
						
						break;
					}
					
				}
				
				//L'agente disponibile partira'
				
				//mi salvo la distanza da cui parte l'agente incaricato
				
				if(trovato) {
				//calcolo il tempo che ci mette ad arrivare nel distretto del crimine
				double velocitaMedia=60;
				//tempo=distanza(km)/velocita(km/h)
				double tempo = distanza/velocitaMedia;
				Duration tempoDiPercorrenza = Duration.of((long) tempo, ChronoUnit.MINUTES);
				
				//qui verra' generato un evento di tipo intervento_agente con oraInizio=oraInizio+tempoDiPercorrenza e sempre  qui tale agente verra' settato come disponibile=false
					queue.add(new Evento(TipoEvento.INTERVENTO_AGENTE, luogoCrimine, ev.getTempoInizio().plus(tempoDiPercorrenza), tempoDiPercorrenza, 0));
				
					//se questo tempo e' maggiore di 15min--->malgestiti++
					if(Duration.of(15, ChronoUnit.MINUTES).compareTo(tempoDiPercorrenza)>0)
						malGestiti++;
					//altrimenti significa che l'agente riesce ad arrivare in un tempo ragionevole
				
				}
				//se non e' disponibile nemmeno un agente allora malgestiti++
				else {
					malGestiti++;
				}
				break;
				
			case INTERVENTO_AGENTE:
				//Questo evento viene richiamato solo quando un agente
				//arriva sul posto, quindi in realta' mi serve solo per
				//creare un nuovo evento di tipo FINE_INTERVENTO in cui 
				//l'agente torna a essere disponibile, nel distretto in cui si trova
				
				
				
				
				break;
				
			case FINE_INTERVENTO:
				
				//L'agente torna a essere disponibile
				
				
				break;
			
			
			
			
			}
		}
		
		System.out.println("Numero totale di eventi mal gestiti = "+malGestiti);
		
	}



	public int getEventiMalGestiti() {
		// TODO Auto-generated method stub
		return malGestiti;
	}

}
