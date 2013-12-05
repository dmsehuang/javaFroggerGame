package frogger;

/**
 * @author Xitan Qiang & Huijing Huang
 *
 */
import java.util.ArrayList;
import java.util.Observable;
import java.util.Random;

/** Cast class hold a list of sprite objects */
public class Cast extends Observable{
	int canvasX;
	int canvasY;
	int xUp;
	int xDown;
	final int middlePos = 120;
	final int margin = 70;
	final int Delta = 10;
	final int carLength = 90;
	final int safeDownMargin = 245;
	final int safeUpMargin = 20;
	// offset for car image
	final int offset = 30;
    ArrayList<Sprite> sprites = new ArrayList<Sprite>();
    
    /**
     * Constructor
     * @param canvasX
     * @param canvasY
     */
    public Cast(int canvasX, int canvasY){
    	this.canvasX = canvasX;
    	this.canvasY = canvasY;
    }
    
    /**
     * Create cast
     */
    public void createCast(){
    	// add from into the first cast
    	if(sprites!=null){
    		sprites.clear();
    	}
    	Frog frog = new Frog();
    	sprites.add(frog);
    	int carNum = 20;
    	xUp = canvasX + 10;
		xDown = -10;
    	for(int i=0; i < carNum; i++){
    		// random direction -- by y position
    		Car car;
    		int y;
    		Random rand = new Random();
    		int randFlag = rand.nextInt(2);
    		int randDelta = 100 + rand.nextInt(300);
            if(randFlag==0){
            	y = 55;
            	xUp += randDelta;
            	car = new Car(xUp, y, canvasX, canvasY);
            }else{
            	y = 175;
            	xDown -= randDelta;
            	car = new Car(xDown, y, canvasX, canvasY);
            }
    		sprites.add(car);
    	}
    }
    
    /**
     * update 
     */
    void update(){
    	xUp -= Delta;
    	xDown += Delta;
    	Frog frog = (Frog)sprites.get(0);
    	for(int i = 1; i< sprites.size(); i++){
    		sprites.get(i).update(xUp, xDown, this);
    		// not safe
    		if(frog.y < safeDownMargin && frog.y > safeUpMargin){
    			// frog on road (grey area)
    			if(frog.y < middlePos && sprites.get(i).y < middlePos){
        			if(Math.abs((frog.x - (sprites.get(i).x + offset))) < carLength/2){
        				// hit frog... WOW!
        				frog.alive = false;
        			}
    			}else if(frog.y >= middlePos && sprites.get(i).y >= middlePos){
        			if(Math.abs((frog.x - (sprites.get(i).x + offset) )) < carLength/2){
        				// hit frog... WOW!
        				frog.alive = false;
        			}
    			}
    		}
    	}
    }
    
    /**
     * Update frog
     */
    void updateFrog(String keyVal){
    	Frog frog = (Frog)sprites.get(0);
    	frog.updateFrog(keyVal, canvasX, canvasY);
    }
}
