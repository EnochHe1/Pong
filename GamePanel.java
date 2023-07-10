/* GamePanel class acts as the main "game loop" - continuously runs the game and calls whatever needs to be called

Child of JPanel because JPanel contains methods for drawing to the screen

Implements KeyListener interface to listen for keyboard input

Implements Runnable interface to use "threading" - let the game do two things at once

*/ 
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GamePanel extends JPanel implements Runnable, KeyListener{

  //dimensions of window
  public static final int GAME_WIDTH = 500;
  public static final int GAME_HEIGHT = 500;
  
  public Thread gameThread;
  public Image image;
  public Graphics graphics;
  public Ball ball;
  public Score1 s1;
  public Score2 s2;
  public PlayerPaddle p1;
  public Player2Paddle p2;
  public BeginScreen begin;
  public EndScreen end;
  public static String song1 = "./Bounce.wav";
  public static String song2 = "./Win.wav";
  
  public static Music bounce = new Music();
  public static Music win = new Music();


  public GamePanel(){
    ball = new Ball(GAME_WIDTH/2-Ball.BALL_DIAMETER, GAME_HEIGHT/2 + Ball.BALL_DIAMETER); //create a player controlled ball, set start location to middle of screen
    s1 = new Score1(GAME_WIDTH, GAME_HEIGHT); //start counting the score1
    s2 = new Score2(GAME_WIDTH, GAME_HEIGHT);
    p1 = new PlayerPaddle(GAME_WIDTH-3, GAME_HEIGHT/2);
    p2 = new Player2Paddle(0, GAME_HEIGHT/2);
    begin = new BeginScreen(GAME_WIDTH, GAME_HEIGHT);
    end = new EndScreen(GAME_WIDTH, GAME_HEIGHT);
    this.setFocusable(true); //make everything in this class appear on the screen
    this.addKeyListener(this); //start listening for keyboard input

    this.setPreferredSize(new Dimension(GAME_WIDTH, GAME_HEIGHT));

    //make this class run at the same time as other classes (without this each class would "pause" while another class runs). By using threading we can remove lag, and also allows us to do features like display timers in real time!
    gameThread = new Thread(this); 
    gameThread.start();
  }

  //paint is a method in java.awt library that we are overriding. It is a special method - it is called automatically in the background in order to update what appears in the window. You NEVER call paint() yourself
  public void paint(Graphics g){
    //we are using "double buffering" here - if we draw images directly onto the screen, it takes time and the human eye can actually notice flashes of lag as each pixel on the screen is drawn one at a time. Instead, we are going to draw images OFF the screen (outside dimensions of the frame), then simply move the image on screen as needed. 
    image = createImage(GAME_WIDTH, GAME_HEIGHT); //draw off screen
    graphics = image.getGraphics();
    draw(graphics); //update the positions of everything on the screen 
    g.drawImage(image, 0, 0, this); //redraw everything on the screen

  }

  //call the draw methods in each class to update positions as things move
  public void draw(Graphics g){
	begin.draw(g);
    if(BeginScreen.check == true) { // checks  if title has been displayed yet
    	 begin.undraw(g);
    	 ball.draw(g);
    	 s1.draw(g);
    	 s2.draw(g);
    	 p1.draw(g);
    	 p2.draw(g);
    }
    if(EndScreen.check == true) { // checks if game has ended
    	end.draw(g);
    }
  }

  //call the move methods in other classes to update positions
  //this method is constantly called from run(). By doing this, movements appear fluid and natural. If we take this out the movements appear sluggish and laggy
  public void move(){
	if (BeginScreen.check == true){
		ball.move();
	    p1.move();
	    p2.move();
	}
  }

  //handles all collision detection and responds accordingly
  public void checkPaddleCollision(){

    //force player to remain on screen
    if(p1.y<= 0){
      p1.y = 0;
    }
    if(p1.y >= GAME_HEIGHT - p1.height){
      p1.y = GAME_HEIGHT-p1.height;
    }
    if(p1.x <= 0){
      p1.x = 0;
    }
    if(p1.x + p1.width >= GAME_WIDTH){
      p1.x = GAME_WIDTH-p1.width;
    }
    
  //force player to remain on screen
    if(p2.y<= 0){
      p2.y = 0;
    }
    if(p2.y >= GAME_HEIGHT - p2.height){
      p2.y = GAME_HEIGHT-p2.height;
    }
    if(p2.x <= 0){
      p2.x = 0;
    }
    if(p2.x + p2.width >= GAME_WIDTH){
      p2.x = GAME_WIDTH-p2.width;
    }
    
  }
  
  public void checkBallCollision() {
	  if(ball.intersects(p1)) {
		  bounce.musicCreate(song1);
		  bounce.play();
		  Ball.xVelocity = -1*(Ball.xVelocity + (Ball.xVelocity+p1.yVelocity)/8);
		  
	  }
	  if(ball.intersects(p2)) {
		 bounce.musicCreate(song1);
		 bounce.play();
		 Ball.xVelocity = -1*(Ball.xVelocity + (Ball.xVelocity+p1.yVelocity)/8);
	  }
	  if (ball.y >= GAME_HEIGHT - Ball.BALL_DIAMETER && ball.x>0 && ball.x < GAME_WIDTH) {
		  bounce.musicCreate(song1);
		  bounce.play();
		  Ball.yVelocity *= -1;
	  }
	  if (ball.y <= 0 && ball.x>0 && ball.x < GAME_WIDTH) {
		  bounce.musicCreate(song1);
		  bounce.play();
		  Ball.yVelocity *= -1;
	  }
	  //add/subtract 100 to each boundary to give some delay before the ball is reset
	  if (ball.x >= GAME_WIDTH + 100) {
		  Ball.xVelocity = 3;
		  ball.x = GAME_WIDTH/2;
		  ball.y = GAME_HEIGHT/2;
		  Score2.score++;
		  if (Score2.score == 10) {
			  win.musicCreate(song2);
			  win.play();
	    	  Ball.yVelocity = 0;
	    	  Ball.xVelocity = 0;
	    	  EndScreen.winner = 1; // indicates which player won
	    	  EndScreen.check = true;
		  } 
	  }
	  if (ball.x <= -100) {
		  Ball.xVelocity = -3;
		  ball.x = GAME_WIDTH/2;
		  ball.y = GAME_HEIGHT/2;
		  Score1.score++;
		  if (Score1.score == 10) {
			  win.musicCreate(song2);
			  win.play();
	    	  Ball.yVelocity = 0;
	    	  Ball.xVelocity = 0;
	    	  EndScreen.winner = 2; // indicates which player won
	    	  EndScreen.check = true;
		  }  
	  }
  }

  

  //run() method is what makes the game continue running without end. It calls other methods to move objects,  check for collision, and update the screen
  public void run(){
    //the CPU runs our game code too quickly - we need to slow it down! The following lines of code "force" the computer to get stuck in a loop for short intervals between calling other methods to update the screen. 
    long lastTime = System.nanoTime();
    double amountOfTicks = 60;
    double ns = 1000000000/amountOfTicks;
    double delta = 0;
    long now;

    while(true){ //this is the infinite game loop
      now = System.nanoTime();
      delta = delta + (now-lastTime)/ns;
      lastTime = now;

      //only move objects around and update screen if enough time has passed
      if(delta >= 1){
        move();
        checkPaddleCollision();
        checkBallCollision();
        repaint();
        delta--;
      }
    }
  }

  //if a key is pressed, we'll send it over to other classes for processing
  public void keyPressed(KeyEvent e){
    p1.keyPressed(e);
    p2.keyPressed(e);
    if(BeginScreen.check == false) { // checks if title has been displayed yet or not
    	begin.keyPressed(e);
    	Score1.score =0;
    	Score2.score = 0;
    }
    if(EndScreen.check == true) { // checks if game has ended yet
    	end.keyPressed(e);
    }
  }

  //if a key is released, we'll send it over to the PlayerBall class for processing
  public void keyReleased(KeyEvent e){
    p1.keyReleased(e);
    p2.keyReleased(e);
  }

  //left empty because we don't need it; must be here because it is required to be overridded by the KeyListener interface
  public void keyTyped(KeyEvent e){

  }

}