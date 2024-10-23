package simulation;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.junit.jupiter.api.Test;

import affichage.ForestPanel;
import model.Foret;
import model.Parameters;
import simulation.service.SimulationService;

class SimulationServiceTest {

	@Test
	void testSimulateWithFires() throws InterruptedException {
		List<List<Integer>> initialsFires = Arrays.asList(
				Arrays.asList(0, 0)
				);
		Parameters parameters = new Parameters(3, 3, 1, initialsFires);
		Foret foret = new Foret(parameters);

		foret.initialiser();
		ForestPanel forestPanel = new ForestPanel(foret.getGrilleForet());
		
		CountDownLatch latch = new CountDownLatch(1);
		
		SimulationService.simulate(forestPanel, foret, latch);
		
		latch.await();
		
		for (int i = 0; i < foret.getHeight(); i++) {
			for (int j = 0; j < foret.getWidth(); j++) {
				assertEquals(-1, foret.getGrilleForet()[i][j]);
			}
		}
	}
	
	@Test
	void testSimulateWithoutFires() {
		List<List<Integer>> initialsFires = Arrays.asList(
				);
		Parameters parameters = new Parameters(3, 3, 1, initialsFires);
		Foret foret = new Foret(parameters);

		foret.initialiser();
		ForestPanel forestPanel = new ForestPanel(foret.getGrilleForet());
		
		CountDownLatch latch = new CountDownLatch(1);
		SimulationService.simulate(forestPanel, foret, latch);

		for (int i = 0; i < foret.getHeight(); i++) {
			for (int j = 0; j < foret.getWidth(); j++) {
				assertEquals(0, foret.getGrilleForet()[i][j]);
			}
		}
	}
}
