package simulation.service;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

import javax.swing.Timer;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import affichage.ForestPanel;
import model.Foret;
import model.Parameters;
import simulation.Simulation;

/**
 * Service utilisé par la simulation 
 */
public class SimulationService {

	/**
	 * Méthode privée pour lancer la simulation et l'afficher dans le panel
	 * @param forestPanel
	 * @param foret
	 */
	public static void simulate(ForestPanel forestPanel, Foret foret, CountDownLatch latch) {
		// Créer un Timer qui se déclenche toutes les 500 ms
	    Timer timer = new Timer(500, new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	//S'il y a des feux, on update la foret
	            if (foret.hasFire()) {
	            	SimulationService.updateForest(foret); 
	                forestPanel.repaint();
	            } else { //s'il n'y a plus on stoppe l'event 
	                ((Timer) e.getSource()).stop(); 
	                latch.countDown(); //latch pour determiner quand la tâche est finie, pour afficher l'ecran de fin
	            }
	        }
	    });

	    timer.start();
	}
	
	/**
	 * Méthode pour charger les paramètres depuis l'application.properties et les mettre 
	 * dans la class modele parametre 
	 * @return
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public static Parameters load() throws IOException, FileNotFoundException, IllegalArgumentException {		
		Properties properties = new Properties();
		InputStream input = Simulation.class.getClassLoader().getResourceAsStream("application.properties");

		properties.load(input);

		int height = Integer.parseInt(properties.getProperty("height"));
		int width = Integer.parseInt(properties.getProperty("width"));
		double propagationProbability = Double.parseDouble(properties.getProperty("propagationProbability"));

		String intialsFiresString = properties.getProperty("initialsFires");
		Gson gson = new Gson();	        
		Type listType = new TypeToken<List<List<Integer>>>() {}.getType();
		List<List<Integer>> intialsFiresList = gson.fromJson(intialsFiresString, listType);
		
		handlingException(height, width, propagationProbability, intialsFiresList);

		Parameters parameters = new Parameters(height, width, propagationProbability, intialsFiresList);

		return parameters;
	}
	
	/**
	 * Méthode qui propage les feux sur les quatre cases adjacentes 
	 * @param foret
	 * @param x
	 * @param y
	 * @return
	 */
	private static List<int[]> firePropagation(Foret foret, int x, int y) {
		List<int[]> result = new ArrayList<int[]>();

		//Liste des cases alentours ou le feu peut se propager
		int[][] newPropagationCases = {{x - 1, y}, {x + 1, y}, {x, y - 1}, {x, y + 1}};

		//On crée le random auquel on va comparer la proba pour savoir si oui ou non on propage le feu
		Random rand = new Random();

		for (int[] newCase : newPropagationCases) {
			int nouveauX = newCase[0];
			int nouveauY = newCase[1];

			if (nouveauX >= 0 && nouveauX < foret.getHeight() && nouveauY >= 0 && nouveauY < foret.getWidth()) {
				if (foret.getGrilleForet()[nouveauX][nouveauY] == 0 && rand.nextDouble() < foret.getProbabilityOfPropagation()) {
					result.add(new int[]{nouveauX, nouveauY});
				}
			}
		}	

		return result;
	}

	/**
	 * Méthode pour mettre à jour la forêt à chaque itération
	 * @param foret
	 */
	private static void updateForest(Foret foret) {
		int[][] grilleForet = foret.getGrilleForet();
		
		//liste des nouveaux feux à chaque itération, qu'on stock séparément pour pas que les nouveaux feux sur les 
		//sur les lignes suivantes se propagent dans la même itération
		List<int[]> newFire = new ArrayList<int[]>();

		for (int i = 0; i < foret.getHeight(); i++) {
			for (int j = 0; j < foret.getWidth(); j++) {
				if (grilleForet[i][j] == 1) {
					//on change la valeur de la case en cendre
					foret.setCase(i, j, -1);	
					//on ajoute les nouveaux feux potentiels sur les cases adjacentes
					newFire.addAll(firePropagation(foret, i, j));
				}
			}
		}

		//on rajoute les nouveaux feux dans la forêt
		for (int[] tuple : newFire) {
			foret.setCase(tuple[0],tuple[1], 1); 
		}


	}
	
	/**
	 * Méthode pour gérer les exceptions lié aux erreurs potentiels dans les entrées utilisateurs
	 * @param height
	 * @param width
	 * @param propagationProbability
	 * @param intialsFiresList
	 * @throws IllegalArgumentException
	 */
	private static void handlingException(int height, int width, double propagationProbability,
			List<List<Integer>> intialsFiresList) throws IOException {
		if (height == 0 || width == 0) {
			IllegalArgumentException e = new IllegalArgumentException("Une taille de la grille est nulle");
			throw e;
		}
		if (height > 600 || width > 1500) {
			IllegalArgumentException e = new IllegalArgumentException("Une taille de la grille est trop grande");
			throw e;
		}
		if (propagationProbability > 1 || propagationProbability < 0) {
			IllegalArgumentException e = new IllegalArgumentException("La probabilité de propagation n'est pas correct");
			throw e;
		}
		for (List<Integer> position : intialsFiresList) {
			if (position.size() != 2) {
				IllegalArgumentException e = new IllegalArgumentException("Un des feux est à plus ou moins de deux dimensions");
				throw e;
			} else {
				if (position.get(0) > height - 1 || position.get(1) > width - 1) {
					IllegalArgumentException e = new IllegalArgumentException("Un des feux est situé hors de la grille");
					throw e;
				}
			}
		}
	}

}
