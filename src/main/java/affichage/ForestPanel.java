package affichage;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Class pour gerer l'affichage de la foret dans un JPanel de la simulation 
 */
public class ForestPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	
	private int[][] grid; // 0 = cendre, 1 = feu, 2 = arbre

	private int ratioHauteur;
	
	private int ratioLargeur;
	
    public ForestPanel(int[][] grid) {
        this.grid = grid;
        this.ratioHauteur = 600/this.getGridHeight();
        this.ratioLargeur = 1500/this.getGridWidth();

    }
    
    public int getGridHeight() {
    	return grid.length;
    }
    
    public int getGridWidth() {
    	return grid[0].length;
    }
    
    /**
     * Méthode qui a partir de la grille correspondant à l'état de la forêt crée le panel correspondante 
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        int heightGrid = this.getGridHeight();
        int widthGrid = this.getGridWidth();
        
        int cellHeight = 1 * ratioHauteur;
        int cellWidth = 1 * ratioLargeur;
        
        for (int i = 0; i < heightGrid; i++) {
            for (int j = 0; j < widthGrid; j++) {
                Color color;
                switch (grid[i][j]) {
                    case -1: // Cendre
                        color = Color.GRAY;
                        break;
                    case 1: // Feu
                        color = Color.RED;
                        break;
                    case 0: // Végétation
                        color = Color.GREEN;
                        break;
                    default:
                        color = Color.BLACK;
                }
                g.setColor(color);
                g.fillRect( j * cellWidth, i * cellHeight, cellWidth, cellHeight); // Dessiner la case
            }
        }
    }
    
    /**
     * Méthode qui permet la création de la frame et d'ajouter le panel dedans
     * @param foret
     * @return
     */
    public JFrame defineFrame() {
		JFrame frame = new JFrame("Simulation de Feu de Forêt");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		
		//on utilise preferred size pour ensuite pack la frame pour que le panel 
		//remplisse toujours l'intégralité de la frame
		this.setPreferredSize(new Dimension(this.getGridWidth() * ratioLargeur, 
				this.getGridHeight() * ratioHauteur));
		
		frame.add(this);
		frame.pack(); 
		frame.setLocationRelativeTo(null); //pour centrer la fenetre 
		frame.setVisible(true);
		
		return frame;
	}
    
}
