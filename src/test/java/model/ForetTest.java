package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

class ForetTest {
	
	 List<List<Integer>> initialsFire = Arrays.asList(
	            Arrays.asList(1, 2)
	        );
	
	private Parameters parameters = new Parameters(5, 5, 0.7, initialsFire);
	
	private Foret foret = new Foret(parameters);
	
	@Test
	void testSetCase() {
		foret.setCase(0, 0, 1);
		assertEquals(foret.getGrilleForet()[0][0], 1);
	}
	
	@Test
	void testInitialiser() {
		foret.initialiser();
		int fire = foret.getGrilleForet()[1][2];
		assertEquals(fire, 1);		
	}

	@Test
	void testHasFire() {
		boolean withoutFire = foret.hasFire();
		foret.initialiser();
		boolean withFire = foret.hasFire();
		
		assertTrue(withFire);
		assertFalse(withoutFire);
	}
}
