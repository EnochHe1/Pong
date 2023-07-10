import java.awt.*;
import java.awt.event.*;

public class Player2Paddle extends Rectangle {
	
	public int yVelocity;
	public final int SPEED = 5;
	public static final int w = 3;
	public static final int h = 90;
	
	public Player2Paddle(int x, int y) {
		super(x, y, w, h);
	}
	
	//called from GamePanel when any keyboard input is detected
	  //updates the direction of the ball based on user input
	  //if the keyboard input isn't any of the options (d, a, w, s), then nothing happens
	  public void keyPressed(KeyEvent e){
	
	    if(e.getKeyChar() == 'w'){
	      setYDirection(SPEED*-1);
	      move();
	    }
	
	    if(e.getKeyChar() == 's'){
	      setYDirection(SPEED);
	      move();
	    }
	  }
	  
	//called from GamePanel when any key is released (no longer being pressed down)
	  //Makes the ball stop moving in that direction
	  public void keyReleased(KeyEvent e){
	
	    if(e.getKeyChar() == 'w'){
	      setYDirection(0);
	      move();
	    }
	
	    if(e.getKeyChar() == 's'){
	      setYDirection(0);
	      move();
	    }
	  }
	
	  //called whenever the movement of the ball changes in the y-direction (up/down)
	  public void setYDirection(int yDirection){
	    yVelocity = yDirection;
	  }
	
	  //updates the current location of the ball
	  public void move(){
	    y = y + yVelocity;
	  }
  
  public void draw(Graphics g){
    g.setColor(Color.black);
    g.fillRect(x, y, w, h);

  }
  
}
