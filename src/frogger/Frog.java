package frogger;

/**
 * @author Xitan Qiang & Huijing Huang
 *
 */
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/** Frog class */
public class Frog extends Sprite {
    enum Orientation { UP, RIGHT, DOWN, LEFT }
    Orientation orientation;
    boolean alive;
    final int startX = 500;
    final int startY = 250;
    final int step = 50;
    
    /** Constructor for Frog */
    public Frog(){
        // Frog appears at the center bottom
        this.x = startX;
        this.y = startY;
        // Frog has no dx and dy 
        this.dx = 0;
        this.dy = 0;
        // Frog is UP and alive when created
        this.orientation = Orientation.UP;
        this.alive = true;
    }
    
    /**
     * This is empty, since cars and frogs are treated differently
     */
    @Override
    void update(int xUp, int xDown, Cast cast) {
        // TODO Auto-generated method stub

    }
    
    /**
     * Update the frog position info, based on key pressed and orientation
     * @param keyVal
     * @param canvasX
     * @param canvasY
     */
    void updateFrog(String keyVal, int canvasX, int canvasY) {
    	switch(keyVal){
		case "up": 
			if(alive){
				switch(orientation){
					case UP:
						y -= step; 
						if(y < 0){
							x = startX;
							y = startY;
						}
						break;
					case DOWN:
						if(y > startY - step){
							// do not update frog
						}else{
							y += step;
						}
						break;
					case LEFT:
						if(x < step){
							// do not update frog
						}else{
							x -= step;
						}
						break;
					case RIGHT:
						if(x > canvasX - step*2){
							// do not update frog
						}else{
							x += step;
						}
						break;
				}
			}
			break;
		case "down":
			switch(orientation){
			case UP:
				orientation = Orientation.DOWN; break;
			case DOWN:
				orientation = Orientation.UP; break;
			case LEFT:
				orientation = Orientation.RIGHT; break;
			case RIGHT:
				orientation = Orientation.LEFT; break;
			}
			break;
		case "left":
			switch(orientation){
			case UP:
				orientation = Orientation.LEFT; break;
			case DOWN:
				orientation = Orientation.RIGHT; break;
			case LEFT:
				orientation = Orientation.DOWN; break;
			case RIGHT:
				orientation = Orientation.UP; break;
			}
			break;
		case "right": 
			switch(orientation){
			case UP:
				orientation = Orientation.RIGHT; break;
			case DOWN:
				orientation = Orientation.LEFT; break;
			case LEFT:
				orientation = Orientation.UP; break;
			case RIGHT:
				orientation = Orientation.DOWN; break;
			}
			break;
		case "enter":
        	alive = true;
        	this.x = startX;
        	this.y = startY;
        	orientation = Orientation.UP;
        	break;
		default: break;
    	}
        // notify the observers
        setChanged();
        notifyObservers();
    }
    
    /** Draw the frog */
    @Override
    void draw(Graphics g) {
    	String frogToLoad = "";
        if(this.alive){
        	switch(orientation){
        	case UP: frogToLoad = "Images/frog-up.png";break;
        	case DOWN: frogToLoad = "Images/frog-down.png";break;
        	case RIGHT: frogToLoad = "Images/frog-right.png";break;
        	case LEFT: frogToLoad = "Images/frog-left.png";break;
        	}
        	Image img = loadImage(frogToLoad);
            // TODO observer class is not directly used
            g.drawImage(img, this.x, this.y, null);
        }else{
            // draw blood
        	Image img = loadImage("Images/splat.gif");
        	g.drawImage(img, this.x, this.y, null);
        }
    }
    
    /**
     * Load a image from file
     * @param fileName
     * @return Image
     */
	private Image loadImage(String fileName) {
        Image img = null;
        try {
            img = ImageIO.read(new File(fileName));
        } catch (IOException exc) {
            System.out.println("Can't load image.");
        }
        return img;
	}
}
