package battleTetris;


import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import tetris.Board.Owner;

import battleTetris.*;
import battleTetris.btMain.Direction;



public class btGameInterface {
	private btTile[][] grid;
	private btBoard b;
	private btMain main;
	private JFrame frame = new JFrame();
	private JPanel board = new JPanel();
	private JPanel info = new JPanel();
	private JPanel[][] squares = new JPanel[10][41];
	private JLabel p1Lines = new JLabel();
	private JLabel p2Lines = new JLabel();

	public btGameInterface(btBoard b1, btMain MAIN) {
		main = MAIN;
		b = b1;
		grid = b.showBoard();
		frame.setVisible(true);
		frame.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		frame.setResizable(false);
		frame.add(board);
		frame.add(info);
		frame.setTitle("Tetris");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		board.setBackground(java.awt.Color.WHITE);
		info.setBackground(java.awt.Color.DARK_GRAY);
		board.setLayout(null);
		board.setBackground(java.awt.Color.GRAY);
		for (btTile[] r: grid) {
			for (btTile t: r) {
				squares[t.x][t.y] = new JPanel();
				board.add(squares[t.x][t.y]);
				squares[t.x][t.y].setLocation(t.x * 15 + t.x, t.y * 15 + t.y);
				squares[t.x][t.y].setBackground(java.awt.Color.WHITE);
				squares[t.x][t.y].setSize(new Dimension(15, 15));
			}
		}

		JButton restart = new JButton();
		restart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				main.reset();
				grid = b.showBoard();
				display();
			}
		});

		JPanel gameInfo = new JPanel();
		JLabel p2Lines = new JLabel();
		info.add(restart);
		info.add(gameInfo);
		info.setPreferredSize(new Dimension(150, 620));
		board.setPreferredSize(new Dimension(260, 655));
		frame.pack();
		gameInfo.add(p1Lines);
		gameInfo.add(p2Lines);
		p1Lines.setLocation(0, 0);
		p1Lines.setBackground(java.awt.Color.WHITE);
		p1Lines.setText(main.clines[0] + " lines cleared.");
		p1Lines.setPreferredSize(new Dimension(150, 30));
		restart.setText("RESTART");
		restart.setPreferredSize(new Dimension(150, 50));

		InputMap im = board.getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW);
		ActionMap am = board.getActionMap();

		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0), "d");
		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0), "a");
		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_W, 0), "w");
		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0), "s");
		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_Q, 0), "q");
		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_E, 0), "e");

		am.put("d", new ArrowAction("right", Owner.P1));
		am.put("a", new ArrowAction("left", Owner.P1));
		am.put("w", new ArrowAction("up",Owner.P1));
		am.put("s", new ArrowAction("down",Owner.P1));
		am.put("q", new ArrowAction("ccw",Owner.P1));
		am.put("e", new ArrowAction("cw",Owner.P1));

	}

	@SuppressWarnings("serial")
	private class ArrowAction extends AbstractAction {

		String direction;
		Owner player;
		public ArrowAction(String d, Owner p) {
			direction = d;
			player=p;
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			if (direction == "down")
				b.move(Direction.DOWN, player);
			else if (direction == "left")
				b.move(Direction.LEFT, player);
			else if (direction == "right")
				b.move(Direction.RIGHT, player);
			else if (direction == "up")
				b.move(Direction.UP, player);
			else if (direction == "cw")
				b.move(Direction.CW, player);
			else if (direction == "ccw")
				b.move(Direction.CCW, player);
			else
				b.move(Direction.NONE, player);

			display();
		}

	}
/*
	public Direction input() {
		// currently for textline input, will implement keylistener to directly
		// send enum
		String input = "";
		input = s.nextLine();
		try {
			return Direction.valueOf(input);
		} catch (IllegalArgumentException e) {
			System.out.println("Shit's done gone fucked up.");
			return Direction.NONE;
		}

	}*/

	public void display() {
		for (btTile[] r: grid) {
			String line = "|";

			for (btTile t: r) {
				if (grid[t.x][t.x].owner == Owner.BLANK)
					line += " ";
				else if (grid[t.x][t.y].owner == Owner.GAME)
					line += "O";
				else if (grid[t.x][t.y].owner == Owner.P1)
					line += "@";
				else if (grid[t.x][t.y].owner == Owner.P2)
					line += "X";

			}
			line += "|";

			System.out.println(line);
		}
		System.out.println("-------------");
		System.out.println();

		for (btTile[] r: grid) {
			for (btTile t: r) {
				if (grid[t.x][t.y].owner == Owner.BLANK)
					squares[t.x][t.y].setBackground(java.awt.Color.WHITE);
				else if (grid[t.x][t.y].owner == Owner.GAME)
					squares[t.x][t.y].setBackground(java.awt.Color.LIGHT_GRAY);
				else if (grid[t.x][t.y].owner == Owner.P1)
					squares[t.x][t.y].setBackground(java.awt.Color.BLUE);
				else if (grid[t.x][t.y].owner == Owner.P2)
					squares[t.x][t.y].setBackground(java.awt.Color.RED);

			}
		}

		p1Lines.setText(main.clines[0] + " lines cleared.");
		p2Lines.setText(main.clines[1] + " lines cleared.");

		// board.setBackground(java.awt.Color.GREEN);
	}

	public void update(btBoard b2) {
		b = b2;
		display();
	}

}
