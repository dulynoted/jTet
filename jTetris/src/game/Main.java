package game;
import javax.swing.*;
import java.awt.event.*;
public class Main {
	private int points=0;
	private int clines=0;
	private Timer timer;
	private boolean gameOn=true;
	private boolean unstacked=true;

	private Board b=new Board(gameOn,unstacked);
	private GameInterface gi=new GameInterface(b.showBoard());
	
	public Main(){
		
		gi.display();
		b.spawn();
		timer=new Timer(1000,new ActionListener() {
		      public void actionPerformed(ActionEvent evt) {
		    	  b.move(Direction.DOWN);
		    	  gi.display();
		      };}  );
		
		
		while(gameOn){
		timer.setDelay(1000-(clines/10)*50);
		 cycle();
		 unstacked=true;
		 
		}
		System.out.println("YOU LOSE");
		//Endgame
		
	}
	public enum Direction{
		UP,DOWN,LEFT,RIGHT,CW,CCW,NONE
	}
	
	public void cycle(){
		b.spawn();
		timer.start();
			while(gameOn&&unstacked){
			Direction input=gi.input();
			b.move(input);
			gi.display();
			}
		timer.stop();
		b.clear();
		gameOn=b.endcheck();
		}
		
		
}
