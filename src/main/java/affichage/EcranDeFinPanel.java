package affichage;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * Class pour gerer l'affichage du panneau de fin dans un JPanel de la simulation 
 */
public class EcranDeFinPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Defiinit les caracteristiques du panel de fin
	 */
	public EcranDeFinPanel() {
		//Partie texte
		JLabel label = new JLabel("Simulation terminée");
		label.setFont(new Font("Arial", Font.BOLD, 30));
		
		//PArtie positionnement
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setVerticalAlignment(SwingConstants.CENTER);
		this.setLayout(new BorderLayout());
		
		this.add(label, BorderLayout.CENTER);
	}

	/**
	 * Remplace le panel de la foret par le panel ecran de fin
	 * @param frame
	 */
	public static void defineEndingScreen(JFrame frame) {
		frame.getContentPane().removeAll();
		frame.getContentPane().add(new EcranDeFinPanel());
		frame.revalidate();
		frame.repaint();
	}


}
