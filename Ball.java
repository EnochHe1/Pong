/* PlayerBall class defines behaviours for the player-controlled ball  

child of Rectangle because that makes it easy to draw and check for collision

In 2D GUI, basically everything is a rectangle even if it doesn't look like it!
*/ 
import java.awt.*;

public class Ball extends Rectangle{

  public static int yVelocity;
  public static int xVelocity;
  public static final int BALL_DIAMETER = 10; //size of ball

  //constructor creates ball at given location with given dimensions
  public Ball(int x, int y){
    super(x, y, BALL_DIAMETER, BALL_DIAMETER);
    yVelocity = 2;
    xVelocity = 3;
  }

  //called frequently from both PlayerBall class and GamePanel class
  //updates the current location of the ball
  public void move(){
    y = y + yVelocity;
    x = x + xVelocity;
  }

  //called frequently from the GamePanel class
  //draws the current location of the ball to the screen
  public void draw(Graphics g){
    g.setColor(Color.black);
    g.fillOval(x, y, BALL_DIAMETER, BALL_DIAMETER);

  }
  
}