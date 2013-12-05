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

/**
 * @author Administrator
 *
 */
public abstract class Sprite extends Observable {
	int x;
	int y;
	int dx;
	int dy;
	
	/**
	 *  makes all necessary changes in the state of this sprite before the next time 
	 *  it is drawn. Among other things, this usually means updating x and y.
	 */
	abstract void update(int xUp, int xDown, Cast cast);
	
	/**
	 * draw the sprite
	 */
	abstract void draw(Graphics g);
}
