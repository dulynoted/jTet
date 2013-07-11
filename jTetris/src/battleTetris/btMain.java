package battleTetris;

import java.awt.event.*;
import javax.swing.Timer;

import tetris.Board.Owner;

public class btMain {
	public int version = 0;
	public int clines[] = { 0, 0 };

	private Timer timer;
	private Timer timer2;

	private boolean gameOn = true;
	private boolean p1unstacked = true;
	private boolean p2unstacked = true;

	private btBoard b;
	private btGameInterface gi;

	public btMain() {
		b = new btBoard(gameOn);
		gi = new btGameInterface(b, this);
		b.spawn(Owner.P1);
		b.spawn(Owner.P2);

		gi.display();
		timer = new Timer(1000, new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				System.out.println("LOOK DOWN");
				p1unstacked = b.move(Direction.DOWN, Owner.P1);
				if (!p1unstacked)
					cycle(Owner.P1);
				gi.display();
			};
		});
		timer2 = new Timer(1000, new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				System.out.println("LOOK DOWN");
				p2unstacked = b.move(Direction.UP, Owner.P2);
				if (!p2unstacked)
					cycle(Owner.P2);
				gi.display();
			};
		});
		timer2.start();

		timer.start();
		// p1unstacked = true;
		// p2unstacked = true;

	}

	private void endgame() {
		//System.out.println("YOU LOSE");
		timer.stop();
		timer2.stop();
	}

	public enum Direction {
		UP, DOWN, LEFT, RIGHT, CW, CCW, NONE
	}

	public void reset() {
		endgame();
		clines[0] = 0;
		clines[1] = 0;

		gameOn = true;
		p1unstacked = true;
		b = new btBoard(gameOn);
		gi.update(b);

		timer.start();
		timer2.start();

		cycle(Owner.P1);
		cycle(Owner.P2);

		System.out.println("game reset");
	}

	public void cycle(Owner p) {
		System.out.println("Cycling");
		int player;
		player = (p == Owner.P1) ? 0 : 1;
		clines[player] += b.clear(p);
		timer.setDelay(1000 - (clines[player] / 10) * 50);

		gameOn = b.endcheck();
		if (gameOn)
			b.spawn(p);
		else
			endgame();

	}

}
