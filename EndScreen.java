import java.awt.*;
import java.awt.event.KeyEvent;

public class EndScreen{

  public static int GAME_WIDTH;//width of the window
  public static int GAME_HEIGHT;//height of the window
  public static boolean check = false; // checks if game has ended
  public static int winner; // variable to indicate which player won(1 is left, 2 is right)

  //constructor establishes dimensions of game window
  public EndScreen(int w, int h){
    EndScreen.GAME_WIDTH = w;
    EndScreen.GAME_HEIGHT = h;
  }
  
  public void keyPressed(KeyEvent e){
	 if(e.getKeyChar() == 'r'){ 
	   BeginScreen.check = false; // sets title check to false in order to display it again
	   check = false; 
	 }
  }
  
  //tells which player has won
  public void draw(Graphics g){
	 g.setColor(Color.black);
	 g.setFont(new Font("Consolas", Font.PLAIN, 40));
     g.drawString("CONGRATULATIONS", (int)(GAME_WIDTH*0.11), (int)(GAME_HEIGHT*0.35));
     
     g.setFont(new Font("Consolas", Font.PLAIN, 20));
     g.drawString("PLAYER " + winner + " HAS WON!!", (int)(GAME_WIDTH*0.28), (int)(GAME_HEIGHT*0.6));
    
     g.setFont(new Font("Consolas", Font.PLAIN, 15));
     g.drawString("Press r to play again", (int)(GAME_WIDTH*0.35), (int)(GAME_HEIGHT*0.75));
  }
}