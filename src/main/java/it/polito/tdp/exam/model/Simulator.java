package it.polito.tdp.exam.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;

import it.polito.tdp.exam.db.BaseballDAO;
import it.polito.tdp.exam.model.Event.EvenType;

public class Simulator {
	private int N;
	private BaseballDAO dao;
	private  Graph<Integer, DefaultWeightedEdge> grafo;
	private List<Integer> vertici;
	private Map<People, Integer > mappaTifosi;
	private Map<String, People> mappaTeam;
	private int partenza;
	private int abbandonano;
	private Team t;
	
	private PriorityQueue<Event> queue;

	public Simulator(Graph<Integer, DefaultWeightedEdge> grafo) {
		super();
		this.grafo = grafo;
		this.dao = new BaseballDAO();
	}
	
	public void initialize(int anno, Team t, int n, Map<String,People> mappa) {
		this.N =n;
		this.partenza = anno;
		this.t = t;
		this.mappaTeam = mappa;
		this.queue = new PriorityQueue<>();
		this.vertici = new ArrayList<>(this.grafo.vertexSet());
		for (People p: dao.getGiocatori(t, anno, mappa)){
			mappaTifosi.put(p, this.N/dao.getGiocatori(t, anno, mappa).size());
		}
		for (int i =0; i<vertici.size();i++) {
			List<People> giocatori1 = dao.getGiocatori(t, vertici.get(i), mappa);
			List<People> giocarori2 = dao.getGiocatori(t,vertici.get(i+1) , mappa);
			for (People p: giocatori1) {
				if (giocarori2.contains(p)) {
					queue.add(new Event(vertici.get(i+1), EvenType.rimane, p, vertici.get(i)));
				}else {
					queue.add(new Event(vertici.get(i+1), EvenType.cambia, p, vertici.get(i)));
				}
				
			}
			
		}
		
	}
	
	public void run() {
		while (!this.queue.isEmpty()) {
			Event e = queue.poll();
			processa(e);
		}
	}

	private void processa(Event e) {
		int tempo = e.getTempo();
		EvenType type = e.getType();
		int annoVecchio = e.getAnnoVeccio();
		People p = e.getP();
		switch (type) {
		case rimane: 
			int tifosi = mappaTifosi.get(p);
			int cambiano = (int) (tifosi*0.10);
			List<People> giocatoriAnno = dao.getGiocatori(t, cambiano, mappaTeam);
			giocatoriAnno.remove(p);
			double random = Math.random();
			int indice = (int) (random* giocatoriAnno.size());
			People giocatore = giocatoriAnno.get(indice);
			mappaTifosi.put(p, mappaTifosi.get(p)-cambiano);
			mappaTifosi.put(giocatore, mappaTifosi.get(giocatore)+cambiano);
			break;
		
		case cambia:
			int tif = mappaTifosi.get(p);
			int smettono = (int) (tif*0.10);
			abbandonano+= smettono;
			List<People> giocatorAnnoNuovo = dao.getGiocatori(t, tempo, mappaTeam);
			List<People> giovatoriAnnoVecchi = dao.getGiocatori(t, annoVecchio, mappaTeam);
			giovatoriAnnoVecchi.remove(p);
			List<People> nuovi = new ArrayList<>();
			List<People> vecchi = new ArrayList<>();
			for (People p1: giocatorAnnoNuovo) {
				if (giovatoriAnnoVecchi.contains(p1)) {
					vecchi.add(p1);
				}else {
					nuovi.add(p1);
				}
			}
			// ranom <0.75
			// random tra giocaoti nuovi per scegliere 
			// altrimenti random tra vecchi per scegliere 
			//modific la mappa dei due giocatori 
			
			break;
		
		
		
		}
	}

}
