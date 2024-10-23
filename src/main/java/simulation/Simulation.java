package simulation;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import javax.swing.JFrame;

import affichage.EcranDeFinPanel;
import affichage.ForestPanel;
import model.Foret;
import model.Parameters;
import simulation.service.SimulationService;

/**
 * Class principale qui fait tourner la simulation 
 */
public class Simulation {

	public static void main(String[] args) throws FileNotFoundException, IOException, InterruptedException {
		Parameters parameters = SimulationService.load();
		
		Foret foret = new Foret(parameters);
		foret.initialiser();

		ForestPanel forestPanel = new ForestPanel(foret.getGrilleForet());
		JFrame frame = forestPanel.defineFrame();

		CountDownLatch latch = new CountDownLatch(1);
		SimulationService.simulate(forestPanel, foret, latch);
		
		latch.await();
		
		EcranDeFinPanel.defineEndingScreen(frame);
	}

}
