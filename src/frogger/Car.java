package frogger;

/**
 * @author Xitan Qiang & Huijing Huang
 *
 */
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

/** Car class */
public class Car extends Sprite {
    
	final int middlePos = 120;
	int delta = 10;
	final int carLength = 90;
	int xLimit;
	String color;
	
    /** Constructor for Car */
    public Car(int x, int y, int canvasX, int canvasY){
        // Cars are randomly placed outside the visible window
        this.x = x; 
        this.y = y;
        // direction with respect to y 
        if(y<=middlePos){
        	dx = -delta;
        	xLimit = -carLength;
        }else{
        	dx = delta;
        	xLimit = canvasX + carLength;
        }
        randomColor();
    }
    
    /** 
     * Update the location of a car 
     */
    void update(int xUp, int xDown, Cast cast) {
        // update car position
    	Random rand = new Random();
        this.x += this.dx;
        this.y += this.dy;
        if(y <= middlePos){
        	if(x < xLimit){
        		int randNum = 100 + rand.nextInt(300);
        		x = xUp + randNum;
        		xUp += randNum;
        		cast.xUp = xUp;
        		randomColor();
        	}
        }else{
        	if(x > xLimit){
        		int randNum = 100 + rand.nextInt(300);
        		x = xDown - randNum;
        		xDown -= randNum;
        		cast.xDown = xDown;
        		randomColor();
        	}
        }
        // notify the observers
        setChanged();
        notifyObservers();
    }

    /**
     * Draw the car 
     */
    @Override
    void draw(Graphics g) {
        Image img = null;
        img = loadImage(color);
        // TODO observer class is not directly used
        g.drawImage(img, this.x, this.y, null);
    }
    
    /**
     * Randomly pick up a color
     * @return
     */
    void randomColor() {
        String[] carColor = {"yellow","blue","aqua","white","red","green"};
        final int minimum = 0;
        final int maximum = 5;
        int index = minimum + (int)(Math.random()*maximum);
        String carToLoad = "Images/" + carColor[index] + "-car-";
        if(this.y<=middlePos){
        	carToLoad += "left.png";
        }else{
        	carToLoad += "right.png";
        }
        this.color = carToLoad;
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
