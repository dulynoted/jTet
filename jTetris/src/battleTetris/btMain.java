package battleTetris;

import java.awt.event.*;
import javax.swing.Timer;

import tetris.Board.Owner;

public class btMain {
	public int version = 0;
	public int clines[] = { 0, 0 };

	private Timer timer;
	private Timer timer2;
	private boolean[] unstacked= new boolean[2];

	public boolean gameOn = true;

	private btBoard b;
	private btGameInterface gi;

	public enum Direction {
		UP, DOWN, LEFT, RIGHT, CW, CCW, DROP, NONE
	}
	
	public btMain() {
		b = new btBoard(this);
		gi = new btGameInterface(b, this);
		b.spawn(Owner.P1);
		b.spawn(Owner.P2);
		unstacked[0]=true;
		unstacked[1]=true;
		gi.display();
		timer = new Timer(1000, new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				b.move(Direction.DOWN, Owner.P1);
				gi.display();
			};
		});
		timer2 = new Timer(1000, new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				b.move(Direction.UP, Owner.P2);
				gi.display();
			};
		});
		timer2.start();
		timer.start();

	}

	public void cycle(Owner p) {
		System.out.println("Cycling");
		int player;
		player = (p == Owner.P1) ? 0 : 1;
		clines[player] += b.clear(p);
		timer.setDelay(1000 - (clines[player] / 10) * 50);
		if (gameOn)
			b.spawn(p);
		else
			endgame(p);

	}
	
	private void endgame(Owner owner) {
		System.out.println("PLAYER "+owner.toString()+" LOSES");
		timer.stop();
		timer2.stop();
		gi.removeListeners();
	}

	public btBoard showboard(){
		return b;
	}

	public void reset() {
		endgame(Owner.GAME);
		clines[0] = 0;
		clines[1] = 0;

		gameOn = true;
		unstacked[0] = true;
		unstacked[1] = true;
		b = new btBoard(this);
		gi.update(b);
		gi.addListeners();

		timer.start();
		timer2.start();

		cycle(Owner.P1);
		cycle(Owner.P2);
	
		System.out.println("game reset");
	}

	

}
