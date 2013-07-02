package game;

//import javax.swing.*;
import java.awt.event.*;

import javax.swing.Timer;
//import java.util.Timer;
//import java.util.TimerTask;

public class Main {
	private int clines = 0;
	private Timer timer;
	private boolean gameOn = true;
	private boolean unstacked = true;

	private Board b = new Board(gameOn, unstacked);
	private GameInterface gi = new GameInterface(b);

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
		/*
		 * timer=new Timer(); timer.schedule(new TimerTask(){
		 * 
		 * @Override public void run() { System.out.println("LOOK DOWN");
		 * //unstacked=b.move(gi.input()); unstacked = b.move(Direction.DOWN);
		 * if(!unstacked) cycle(); gi.display(); }}, 1000,1000-10*clines);
		 */

		cycle();
		gi.display();
		timer.start();
		System.out.println("wut");

		unstacked = true;

	}

	private void endgame() {
		System.out.println("YOU LOSE");
		// timer.cancel();
		timer.stop();

	}

	public enum Direction {
		UP, DOWN, LEFT, RIGHT, CW, CCW, NONE
	}

	public void cycle() {
		System.out.println("Cycling");

		 timer.setDelay(1000 - (clines / 10) * 50);
		b.clear();
		gameOn = b.endcheck();
		if (gameOn)
			b.spawn();
		else
			endgame();

	}

}
