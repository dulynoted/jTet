package tetris;

import java.awt.event.*;
import javax.swing.Timer;

public class Main {
	private int version = 0;
	public int clines[]={0,0};

	private Timer timer;
	private Timer timer2;

	private boolean gameOn = true;
	private boolean unstacked = true;

	private Board b;
	private GameInterface gi;

	public Main(int VERSION) {
		version = VERSION;
		b = new Board(gameOn, unstacked, version);
		b.spawn(0);
		gi = new GameInterface(b, this);
		gi.display();
		timer = new Timer(1000, new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				System.out.println("LOOK DOWN");
				unstacked = b.move(0, Direction.DOWN);
				if (!unstacked)
					cycle(1);
				gi.display();
			};
		});
		if (version == 2) {
			timer2 = new Timer(1000, new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					System.out.println("LOOK DOWN");
					unstacked = b.move(1, Direction.DOWN);
					if (!unstacked)
						cycle(2);
					gi.display();
				};
			});
			timer2.start();

		}
		timer.start();
		unstacked = true;

	}

	private void endgame() {
		System.out.println("YOU LOSE");
		timer.stop();
		if (version == 2)
			timer2.stop();
	}

	public enum Direction {
		UP, DOWN, LEFT, RIGHT, CW, CCW, NONE
	}

	public void reset() {
		endgame();
		clines[1] = 0;
		
		gameOn = true;
		unstacked = true;
		b = new Board(gameOn, unstacked, version);
		gi.update(b);

		timer.start();
		if (version == 2) {
			clines[2]=0;
			timer2.start();
		}
		cycle(1);
		if (version == 2)
			cycle(2);

		System.out.println("blehrgds");
	}

	public void cycle(int player) {
		System.out.println("Cycling");

		clines[player]+= b.clear(clines[player]);
		timer.setDelay(1000 - (clines[player] / 10) * 50);

		gameOn = b.endcheck();
		if (gameOn)
			b.spawn(player);
		else
			endgame();

	}

}
