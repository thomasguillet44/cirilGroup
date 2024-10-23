package model;

import java.util.List;

/**
 * Modèle de données répresentant notre forêt 
 * Possède des parametres, qui sont les parametres d'netrée (taille, nombre de feu, proba de propagation)
 * et une grille ou on va venir insérer les valeurs correspondant aux feux, aux cendre et aux arbres
 */
public class Foret {

	private Parameters parameters;

	private int[][] grilleForet;

	public int[][] getGrilleForet() {
		return grilleForet;
	}

	public Foret(Parameters parameters) {
		super();
		this.parameters = parameters;
		this.grilleForet = new int[parameters.getHeight()][parameters.getWidth()];
	} 

	/**
	 * Méthode pour mettre à jour la valeur d'une case de la grille
	 * @param x
	 * @param y
	 * @param etat
	 */
	public void setCase(int x, int y, int etat) {
		grilleForet[x][y] = etat;
	}

	/**
	 * Methode initilisant notre foret avec les feux de départ
	 */
	public void initialiser() {
		for (List<Integer> pos : this.parameters.getInitialsFires()) {
			this.setCase(pos.get(0),pos.get(1),1); // Case en feu
		}
	}

	/**
	 * Methode determinant s'il y a encore des feux pour savoir si on doit 
	 * continuer la simulation ou non
	 * @return
	 */
	public boolean hasFire() {
		for (int i = 0; i < parameters.getHeight(); i++) {
			for (int j = 0; j < parameters.getWidth(); j++) {
				if (grilleForet[i][j] == 1) {
					return true;
				}
			}
		}
		return false;
	}

	public int getHeight() {
		return this.parameters.getHeight();
	}

	public int getWidth() {
		return this.parameters.getWidth();
	}

	public double getProbabilityOfPropagation() {
		return this.parameters.getPropagationProbality();
	}

}
