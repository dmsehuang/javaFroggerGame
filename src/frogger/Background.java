package frogger;

/**
 * @author Xitan Qiang & Huijing Huang
 *
 */
import java.awt.Color;
import java.awt.Graphics;


public class Background {
	private Graphics g;
	int width;
	int height;
	final int margin = 30;
	final int yellow = 20;
	final int road = 100;
	
	/**
	 * Constructor
	 * @param view
	 * @param width
	 * @param height
	 */
	public Background(Graphics view, int width, int height) {
		// TODO Auto-generated constructor stub
		g = view;
		this.width = width;
		this.height = height;
	}
	
	/**
	 * Draw the back ground only (road, white bar and yellow line)
	 */
	void drawBackground(){
		g.setColor(Color.white);
		g.fillRect(0, 0, width, margin);
		g.setColor(Color.gray);
		g.fillRect(0, margin, width, road);
		g.setColor(Color.yellow);
		g.fillRect(0, margin+road, width, yellow);
		g.setColor(Color.gray);
		g.fillRect(0, margin + yellow + road, width, road);
		g.setColor(Color.white);
		g.fillRect(0, margin + 2*road + yellow, width, height);

	}
}
