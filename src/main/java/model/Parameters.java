package model;

import java.util.List;

/**
 * Modèle de données correspondants aux parametres d'entrée de notre simulation
 */
public class Parameters {
	
	private int height;
	
	private int width;
	
	private double propagationProbality;
	
	private List<List<Integer>> initialsFires;

	
	public Parameters(int height, int width, double probability, List<List<Integer>> initialsFires) {
		super();
		this.height = height;
		this.width = width;
		this.propagationProbality = probability;
		this.initialsFires = initialsFires;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public double getPropagationProbality() {
		return propagationProbality;
	}

	public void setPropagationProbality(float propagationProbality) {
		this.propagationProbality = propagationProbality;
	}

	public List<List<Integer>> getInitialsFires() {
		return initialsFires;
	}

	public void setInitialsFires(List<List<Integer>> initialsFires) {
		this.initialsFires = initialsFires;
	}
	
}
