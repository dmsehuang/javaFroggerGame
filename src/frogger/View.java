/**
 * 
 */
package frogger;

/**
 * @author Xitan Qiang & Huijing Huang
 *
 */
import java.awt.Graphics;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

/**
 * @author Administrator
 *
 */

public class View extends JPanel implements Observer {
    
    Cast cast;
    final int canvasX = 1000;
    final int canvasY = 400;
    /**
     * Constructor 
     * @param cast
     */
	public View(Cast cast) {
		this.cast = cast;
	}
	
	
	/**
	 * update gui when notified
	 */
	@Override
    public void update(Observable arg0, Object arg1) {
        repaint();
    }
	
	/**
	 * Paint background and sprites
	 */
	public void paint(Graphics g) {
		new Background(g, canvasX, canvasY).drawBackground();
		for(Sprite sprite: cast.sprites){
			sprite.draw(g);
		}
    }
}
