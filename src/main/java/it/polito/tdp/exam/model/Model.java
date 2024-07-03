package it.polito.tdp.exam.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.exam.db.BaseballDAO;

public class Model {
	private BaseballDAO dao;
	private List<Team> teams;
	private List<Integer> anni;
	private Graph<Integer, DefaultWeightedEdge> grafo;
	private Map<String, People> mappa;
	private Map<String,Team> mappaT;
	
	public Model() {
		super();
		this.dao = new BaseballDAO();
		this.teams = new ArrayList<>();
		this.anni = new ArrayList<>();
		this.grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		this.mappa = new HashMap<>();
		this.mappaT = new HashMap<>();
	}
	
	public List<Team> getTeams(){
		for (Team t : dao.readAllTeams()) {
			mappaT.put(t.getName(), t);
		}
		this.teams = dao.getTeam(mappaT);
		return teams;
	}
	
	public void creaGrafo(Team t) {
		Graphs.addAllVertices(this.grafo, dao.getAnni(t));
		for (Integer i1: this.grafo.vertexSet()) {
			for (Integer i2: this.grafo.vertexSet()) {
				if (!i1.equals(i2)) {
					List<People> giocatori1 = dao.getGiocatori(t, i1, mappa);
					List<People> giocatori2 = dao.getGiocatori(t, i2, mappa);
					giocatori1.retainAll(giocatori2);
					Graphs.addEdgeWithVertices(this.grafo, i1, i2,giocatori1.size() );
				}
			}
		}
	}
	
	public int getV() {
		return this.grafo.vertexSet().size();
	}
	public int getA() {
		return this.grafo.edgeSet().size();
	}
	public Set<Integer> getVertici(){
		return this.grafo.vertexSet();
	}
	
	public List<Adiacenza> getAdiacenti(int anno){
		List<Integer> adiacenti = Graphs.neighborListOf(this.grafo, anno);
		List<Adiacenza> resulr = new ArrayList<>(); 
		for (Integer i: adiacenti) {
			DefaultWeightedEdge e = this.grafo.getEdge(anno, i);
			double peso = this.grafo.getEdgeWeight(e);
			resulr.add(new Adiacenza(i, peso));
		}
		return resulr;
	}
}
