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
	//	b.spawn();
	/*	timer=new Timer(1000,new ActionListener() {
		      public void actionPerformed(ActionEvent evt) {
		  		//System.out.println("LOOK DOWN");

		    	 b.move(Direction.DOWN);
		    	 gi.display();
		      };}  );
		*/
		
		while(gameOn){
//	timer.setDelay(1000-(clines/10)*50);
		 cycle();
		 unstacked=true;
			System.out.println("wut");

		}
		System.out.println("YOU LOSE");
		//Endgame
		
	}
	public enum Direction{
		UP,DOWN,LEFT,RIGHT,CW,CCW,NONE
	}
	
	public void cycle(){
		System.out.println("cycle");
		System.out.println("gameon:"+gameOn+" unstacked:"+unstacked);


		b.spawn();
	//	timer.start();
			long ghettoTimer=System.currentTimeMillis();
			while(gameOn&&unstacked){
			System.out.println("Still in the cycle loop");
			System.out.println(System.currentTimeMillis()-ghettoTimer+" and "+(1000-(clines/10)*50));
			if((System.currentTimeMillis()-ghettoTimer)>(1000-(clines/10)*50)){
				unstacked=b.move(Direction.DOWN);
		  	//	System.out.println("LOOK DOWN");
				ghettoTimer=System.currentTimeMillis();
			}
			Direction input=gi.input();
			unstacked=b.move(input);
			gi.display();
			}
			System.out.println("cleanup");

		//timer.stop();
		b.clear();
		gameOn=b.endcheck();
		}
		
		
}
