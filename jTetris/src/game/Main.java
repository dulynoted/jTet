package game;

import java.awt.event.*;
import javax.swing.Timer;

public class Main {
	public int clines = 0;
	private Timer timer;
	private boolean gameOn = true;
	private boolean unstacked = true;

	private Board b = new Board(gameOn, unstacked);
	private GameInterface gi = new GameInterface(b, this);

	public Main() {
		timer = new Timer(1000, new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				System.out.println("LOOK DOWN");
				unstacked = b.move(Direction.DOWN);
				if (!unstacked)
					cycle();
				gi.display();
			};
		});
				

		cycle();
		gi.display();
		timer.start();
		System.out.println("wut");

		unstacked = true;

	}
	
	
	private void endgame() {
		System.out.println("YOU LOSE");
		timer.stop();
	}

	public enum Direction {
		UP, DOWN, LEFT, RIGHT, CW, CCW, NONE
	}
	
	
	public void reset(){
		endgame();
		clines=0;
		gameOn=true; 
		unstacked=true;
		b=new Board(gameOn, unstacked);
		gi.update(b);

		timer.start();
		cycle();
		
		System.out.println("blehrgds");
	}
	
	public void cycle() {
		System.out.println("Cycling");
		
		clines+=b.clear();
		timer.setDelay(1000 - (clines / 10) * 50);

		gameOn = b.endcheck();
		if (gameOn)
			b.spawn();
		else
			endgame();

	}

}
