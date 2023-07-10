import java.awt.*;
import java.awt.event.KeyEvent;

public class BeginScreen{

  public static int GAME_WIDTH;//width of the window
  public static int GAME_HEIGHT;//height of the window
  public static boolean check = false; // checks if BeginScreen has been displayed yet

  //constructor establishes dimensions of game window
  public BeginScreen(int w, int h){
    BeginScreen.GAME_WIDTH = w;
    BeginScreen.GAME_HEIGHT = h;
  }
  
  public void keyPressed(KeyEvent e){
	 if(e.getKeyChar() == 'r'){ // checks if g key is pressed to continue
	   check = true; // indicates BeginScreen has been displayed
	   Ball.yVelocity = -1; // starts the game by causing ball to move
	   Ball.xVelocity = 3;
	 }
  }
  
  //the screen you see in the beginning is displayed
  public void draw(Graphics g){
     g.setColor(Color.black);
     g.setFont(new Font("Consolas", Font.PLAIN, 40));
     g.drawString("PONG!", (int)(GAME_WIDTH*0.38), (int)(GAME_HEIGHT*0.35));
    
     g.setFont(new Font("Consolas", Font.PLAIN, 15));
     g.drawString("Use \'w\' and \'s\' & the up and down arrow keys to play", (int)(GAME_WIDTH*0.14), (int)(GAME_HEIGHT*0.55));
     
     g.setFont(new Font("Consolas", Font.PLAIN, 15));
     g.drawString("Player 1 \t\t\t\t\t\t\t\t\t\t\t\t\t\t\t Player 2", (int)(GAME_WIDTH*0.30), (int)(GAME_HEIGHT*0.65));
    
     g.setFont(new Font("Consolas", Font.PLAIN, 15));
     g.drawString("Press r to start, first to 10 wins is the Winner", (int)(GAME_WIDTH*0.18), (int)(GAME_HEIGHT*0.75));
  }
  
  //removes the previously drawn begin screen
  public void undraw(Graphics g) {
	  g.clearRect(0,0,GAME_WIDTH, GAME_HEIGHT);
  }
  
}