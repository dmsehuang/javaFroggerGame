package frogger;

/**
 * @author Xitan Qiang & Huijing Huang
 *
 */
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Frogger extends JFrame {
	
    JPanel buttonPanel = new JPanel();
    JPanel labelPanel = new JPanel();
    JButton resumeButton = new JButton("Resume");
    JButton pauseButton = new JButton("Pause");
    JButton startButton = new JButton("Start");
    int score = 0;
    int level = 1;
    int lives = 5;
    int frogCount = 0;
    JLabel countLabel = new JLabel("Count smart frogs: "+frogCount);
    JLabel scoreLabel = new JLabel("Score: "+score);
    JLabel levelLabel = new JLabel("Level: "+level);
    JLabel livesLabel = new JLabel("Lives remains: "+lives);
    JLabel msgLabel = new JLabel("Message Box");
	final int margin = 30;
    int period = 150;

    Timer timer;
    boolean runFlag = false;
    final int canvasX = 1000;
    final int canvasY = 400;
    Cast cast = new Cast(canvasX, canvasY);

    
//    JTextArea textArea = new JTextArea();
    View view = new View(cast);
    
    public static void main(String[] args) {
        Frogger frogger = new Frogger();
        frogger.init();
        frogger.setSize(frogger.canvasX, frogger.canvasY);
        frogger.setBackground(Color.white);
        frogger.setVisible(true);
//        frogger.pack();
        frogger.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    
    /**
     * Initialize the game
     */
    public void init() {
    	setFocusable(true);
    	startButton.setFocusable(false);
    	pauseButton.setFocusable(false);
    	resumeButton.setFocusable(false);
        layOutComponents();
        cast.createCast();
        attachListenersToComponents();
        // Connect model and view, add observer
        for(Sprite sprite: cast.sprites){
        	sprite.addObserver(view);
        }
    }
    
    /**
     * Layout all the component
     */
    public void layOutComponents(){
    	msgLabel.setFont(new Font("Bradley Hand ITC", Font.ITALIC, 17));
    	msgLabel.setBorder(BorderFactory.createLineBorder(Color.blue));
    	scoreLabel.setBorder(BorderFactory.createLineBorder(Color.red));
    	livesLabel.setBorder(BorderFactory.createLineBorder(Color.red));
    	levelLabel.setBorder(BorderFactory.createLineBorder(Color.red));
    	countLabel.setBorder(BorderFactory.createLineBorder(Color.red));
        setLayout(new BorderLayout());
        this.add(BorderLayout.SOUTH, buttonPanel);
        buttonPanel.add(resumeButton);
        buttonPanel.add(pauseButton);
        buttonPanel.add(startButton);
        pauseButton.setEnabled(false);
        resumeButton.setEnabled(false);
        this.add(BorderLayout.NORTH, labelPanel);
        labelPanel.add(levelLabel);
        labelPanel.add(scoreLabel);
        labelPanel.add(livesLabel);
        labelPanel.add(countLabel);
        labelPanel.add(msgLabel);
        this.add(BorderLayout.CENTER, view);
//        pack();
        
        
    }
   
    /**
     * Attach click listener and key listner to component
     */
    private void attachListenersToComponents() {
        resumeButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent event) {
                		runFlag = true;
                        resumeButton.setEnabled(false);
                        pauseButton.setEnabled(true);
                        timer = new Timer(true);
                        timer.schedule(new carRun(), 0, period); //task, delay, duration (milliseconds)
                }
        });
        pauseButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent event) {
                	 	runFlag = false;
                        resumeButton.setEnabled(true);
                        pauseButton.setEnabled(false);
                        timer.cancel();
                }
        });
        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
            		startButton.setText("Restart");
            		runFlag = true;
                    startButton.setEnabled(true);
                    pauseButton.setEnabled(true);
//                    resumeButton.setEnabled(false);
                    // upon restart, reset all labels
                    period = 150;
                    score = 0;
                    level = 1;
                    lives = 5; 
                    frogCount = 0;
                    scoreLabel.setText("Score: "+ score);
            		levelLabel.setText("Level: "+ level);
            		livesLabel.setText("Lives remains: "+ lives);
            		countLabel.setText("Count smart frogs: "+ frogCount);
            		// deal with timer
                    // First start, timer is null
                    if(timer!=null){
                    	timer.cancel();
                        cast.createCast();
//                        attachListenersToComponents();
                    }
                    // Connect model and view, add observer
                    for(Sprite sprite: cast.sprites){
                    	sprite.addObserver(view);
                    }
                    timer = new Timer(true);
                    timer.schedule(new carRun(), 0, period); //task, delay, duration (milliseconds)
            }
        });
        
        /**
         * add key listener 
         */
        addKeyListener(new KeyListener() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_UP && runFlag) {
                	// update level label & score
                	Frog frog = ((Frog)cast.sprites.get(0));
                	if(frog.y < margin){
                		frogCount++;
                		score += level*2;
                		level++;
                		if(period > 30){
                			period -= 30;
                		}
                		timer.cancel();
                		timer = new Timer(true);
                		timer.schedule(new carRun(), 0, period); //task, delay, duration (milliseconds)
                		scoreLabel.setText("Score: "+ score);
                		levelLabel.setText("Level: "+ level);
                		countLabel.setText("Count smart frogs: "+frogCount);
                	}
                	cast.updateFrog("up");
                }
                if(e.getKeyCode() == KeyEvent.VK_DOWN && runFlag) {
                	cast.updateFrog("down");
                }
                if(e.getKeyCode() == KeyEvent.VK_LEFT && runFlag) {
                	cast.updateFrog("left");
                }
                if(e.getKeyCode() == KeyEvent.VK_RIGHT && runFlag) {
                	cast.updateFrog("right");
                }
                if(e.getKeyCode() == KeyEvent.VK_ENTER && runFlag) {
                	// update lives
                	if(lives>0 ){
                		if(!((Frog)cast.sprites.get(0)).alive){
                			lives--;
                		}
                	}else{
                		timer.cancel();
                		startButton.setEnabled(true);
                		pauseButton.setEnabled(false);
                		resumeButton.setEnabled(false);
                		msgLabel.setText("Click Start Button to Restart!");
                	}
                	livesLabel.setText("Lives remains: "+ lives);
                	cast.updateFrog("enter");
                }
            }
            
            @Override
            public void keyReleased(KeyEvent e) {
            }

            @Override
            public void keyTyped(KeyEvent e) {
            }
        });
    }
    
    /**
     * this is a task to be scheduled by the timer
     */
	private class carRun extends TimerTask {
		public void run() {
        	cast.update();
        	// update msg label when frog is hit
        	Frog frog = ((Frog)cast.sprites.get(0));
        	if(!frog.alive){
        		msgLabel.setText("Press ENTER key to reborn!");
        	}else{
        		msgLabel.setText("Message Box");
        	}
        }
	}
	
}
