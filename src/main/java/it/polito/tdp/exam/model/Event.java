package it.polito.tdp.exam.model;

import java.util.Objects;

public class Event  implements Comparable<Event>{
	private int tempo;
	public enum EvenType{
		rimane,
		cambia
	}
	private People p;
	private int annoVeccio;
	private EvenType type;
	public Event(int tempo, EvenType type, People p, int annoVecchio) {
		super();
		this.tempo = tempo;
		this.type = type;
		this.p =p;
		this.annoVeccio = annoVecchio;
	}
	public int getTempo() {
		return tempo;
	}
	public void setTempo(int tempo) {
		this.tempo = tempo;
	}
	public EvenType getType() {
		return type;
	}
	public void setType(EvenType type) {
		this.type = type;
	}
	@Override
	public int hashCode() {
		return Objects.hash(tempo, type);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Event other = (Event) obj;
		return tempo == other.tempo && type == other.type;
	}
	@Override
	public int compareTo(Event o) {
		// TODO Auto-generated method stub
		return this.tempo-o.tempo;
	}
	public People getP() {
		return p;
	}
	public void setP(People p) {
		this.p = p;
	}
	public int getAnnoVeccio() {
		return annoVeccio;
	}
	public void setAnnoVeccio(int annoVeccio) {
		this.annoVeccio = annoVeccio;
	}
	
	

}
