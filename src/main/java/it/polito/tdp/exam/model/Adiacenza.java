package it.polito.tdp.exam.model;

import java.util.Objects;

public class Adiacenza {
	private int anno;
	private  double peso;
	public Adiacenza(int anno, double peso) {
		super();
		this.anno = anno;
		this.peso = peso;
	}
	public int getAnno() {
		return anno;
	}
	public void setAnno(int anno) {
		this.anno = anno;
	}
	public double getPeso() {
		return peso;
	}
	public void setPeso(double peso) {
		this.peso = peso;
	}
	@Override
	public int hashCode() {
		return Objects.hash(anno, peso);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Adiacenza other = (Adiacenza) obj;
		return anno == other.anno && Double.doubleToLongBits(peso) == Double.doubleToLongBits(other.peso);
	}
	@Override
	public String toString() {
		return + anno + " (" + peso + ")";
	}
	
	
	

}
