package battleTetris;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.BoxLayout;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import tetris.Board.Owner;
import battleTetris.btMain.Direction;

public class btGameInterface {
	private btTile[][] grid;
	private btBoard b;
	private btMain main;
	private JFrame frame = new JFrame();
	private JPanel board = new JPanel();
	private JPanel board2 = new JPanel();

	private JPanel info = new JPanel();
	private JPanel[][] squares = new JPanel[10][40];
	private JLabel p1Lines = new JLabel();
	private JLabel p2Lines = new JLabel();
	private ActionMap am;
	private InputMap im;
	private int mask = 2 ^ 0;
	private Direction[] directions = Direction.values();
	private boolean[] p1movement = new boolean[directions.length];
	private boolean[] p2movement = new boolean[directions.length];

	public btGameInterface(btBoard b1, btMain MAIN) {
		for (int i = 0; i < 8; i++)
			System.out.println(i + " " + directions[i].toString());
		main = MAIN;
		b = b1;
		grid = b.showBoard();
		frame.setVisible(true);
		frame.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		frame.setResizable(false);
		frame.add(board);
		frame.add(info);
		frame.add(board2);
		frame.setTitle("BATTLE TETRIS");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		info.setBackground(java.awt.Color.DARK_GRAY);
		board.setLayout(null);
		board2.setLayout(null);
		board.setBackground(java.awt.Color.GRAY);
		board2.setBackground(java.awt.Color.GRAY);

		for (btTile[] r : grid) {
			for (btTile t : r) {
				squares[t.x][t.y] = new JPanel();
				JPanel copy = new JPanel();
				board.add(squares[t.x][t.y]);
				board2.add(copy);
				squares[t.x][t.y].setLocation(t.x * 15 + t.x, t.y * 15 + t.y);
				squares[t.x][t.y].setBackground(java.awt.Color.WHITE);
				squares[t.x][t.y].setSize(new Dimension(15, 15));
				copy.setLocation(t.x * 15 + t.x, t.y * 15 + t.y);
				copy.setBackground(java.awt.Color.WHITE);
				copy.setSize(new Dimension(15, 15));
			}
		}

		JButton restart = new JButton();
		restart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				main.reset();
				grid = b.showBoard();
				p1movement = new boolean[directions.length];
				p2movement = new boolean[directions.length];
				display();
			}
		});

		JPanel gameInfo = new JPanel();
		info.add(restart);
		info.add(gameInfo);
		info.setPreferredSize(new Dimension(150, 640));
		board.setPreferredSize(new Dimension(160, 640));
		board2.setPreferredSize(new Dimension(160, 640));
		gameInfo.setPreferredSize(new Dimension(150, 50));

		gameInfo.setLayout(new FlowLayout());
		gameInfo.add(p1Lines);
		gameInfo.add(p2Lines);
	//	p1Lines.setLocation(0, 0);
		p1Lines.setBackground(java.awt.Color.WHITE);
		p1Lines.setText("Player 1 lines cleared: "+main.clines[0]);
		p1Lines.setSize(new Dimension(140, 30));
		//p2Lines.setLocation(0, 25);
		p2Lines.setBackground(java.awt.Color.WHITE);
		p2Lines.setText("Player 2 lines cleared: "+main.clines[1]);
		p2Lines.setSize(new Dimension(140, 30));
		
		restart.setText("RESTART");
		restart.setPreferredSize(new Dimension(150, 50));

		im = board.getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW);
		am = board.getActionMap();

		addListeners();
		frame.pack();

	}

	public void addListeners() {
		// Player 1 key inputs
		im.put(KeyStroke.getKeyStroke("pressed D"), "d");
		im.put(KeyStroke.getKeyStroke("pressed A"), "a");
		im.put(KeyStroke.getKeyStroke("pressed S"), "s");
		im.put(KeyStroke.getKeyStroke("pressed Q"), "q");
		im.put(KeyStroke.getKeyStroke("pressed E"), "e");
		im.put(KeyStroke.getKeyStroke("pressed W"), "w");
		im.put(KeyStroke.getKeyStroke("released D"), "rd");
		im.put(KeyStroke.getKeyStroke("released A"), "ra");
		im.put(KeyStroke.getKeyStroke("released S"), "rs");
		im.put(KeyStroke.getKeyStroke("released Q"), "rq");
		im.put(KeyStroke.getKeyStroke("released E"), "re");
		im.put(KeyStroke.getKeyStroke("released W"), "rw");

		// Player 2 key inputs
		im.put(KeyStroke.getKeyStroke("pressed J"), "j");
		im.put(KeyStroke.getKeyStroke("pressed K"), "k");
		im.put(KeyStroke.getKeyStroke("pressed L"), "l");
		im.put(KeyStroke.getKeyStroke("pressed O"), "o");
		im.put(KeyStroke.getKeyStroke("pressed U"), "u");
		im.put(KeyStroke.getKeyStroke("pressed I"), "i");
		im.put(KeyStroke.getKeyStroke("released I"), "ri");
		im.put(KeyStroke.getKeyStroke("released J"), "rj");
		im.put(KeyStroke.getKeyStroke("released K"), "rk");
		im.put(KeyStroke.getKeyStroke("released L"), "rl");
		im.put(KeyStroke.getKeyStroke("released O"), "ro");
		im.put(KeyStroke.getKeyStroke("released U"), "ru");

		am.put("d", new ArrowAction(3, Owner.P1,true));
		am.put("a", new ArrowAction(2, Owner.P1,true));
		am.put("s", new ArrowAction(1, Owner.P1,true));
		am.put("q", new ArrowAction(5, Owner.P1,true));
		am.put("e", new ArrowAction(4, Owner.P1,true));
		am.put("w", new ArrowAction(6, Owner.P1,true));


		am.put("l", new ArrowAction(3, Owner.P2,true));
		am.put("j", new ArrowAction(2, Owner.P2,true));
		am.put("k", new ArrowAction(0, Owner.P2,true));
		am.put("u", new ArrowAction(4, Owner.P2,true));
		am.put("o", new ArrowAction(5, Owner.P2,true));
		am.put("i", new ArrowAction(6, Owner.P2,true));

		
		am.put("rd", new ArrowAction(3, Owner.P1,false));
		am.put("ra", new ArrowAction(2, Owner.P1,false));
		am.put("rs", new ArrowAction(1, Owner.P1,false));
		am.put("rq", new ArrowAction(5, Owner.P1,false));
		am.put("re", new ArrowAction(4, Owner.P1,false));
		am.put("rw", new ArrowAction(6 , Owner.P1,false));

		am.put("ri", new ArrowAction(6, Owner.P2,false));
		am.put("rl", new ArrowAction(3, Owner.P2,false));
		am.put("rj", new ArrowAction(2, Owner.P2,false));
		am.put("rk", new ArrowAction(0, Owner.P2,false));
		am.put("ru", new ArrowAction(4, Owner.P2,false));
		am.put("ro", new ArrowAction(5, Owner.P2,false));
	}

	public void removeListeners() {
		im.clear();
		am.clear();
	}

	@SuppressWarnings("serial")
	private class ArrowAction extends AbstractAction {
		boolean on;
		int direction;
		Owner player;

		public ArrowAction(int i, Owner p, boolean b) {
			direction = i;
			player = p;
			on=b;
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			if(player==Owner.P1)
				p1movement[direction]=on;
			else
				p2movement[direction]=on;
			
			for (int i = 0; i < directions.length; i++) {
				if (p1movement[i])
					b.move(directions[i], Owner.P1);
				if (p2movement[i])
					b.move(directions[i], Owner.P2);
			}
			display();
		}

	}

	public void display() {
		System.out.println((Integer.toBinaryString(mask)));

		for (int j = 0; j < 40; j++) {
			String line = "|";
			for (int i = 0; i < 10; i++) {
				if (grid[i][j] == null)
					line += "X";
				else if (grid[i][j].owner == Owner.BLANK)
					line += " ";
				else if (grid[i][j].owner == Owner.GAME)
					line += "O";
				else if (grid[i][j].owner == Owner.P1)
					line += "@";
				else if (grid[i][j].owner == Owner.P2)
					line += "#";
				else if (grid[i][j].owner == Owner.P1STOP)
					line += "&";
				else if (grid[i][j].owner == Owner.P2STOP)
					line += "X";
			}
			line += "|";

			System.out.println(line);
		}
		System.out.println("-------------");
		System.out.println();

		for (int j = 0; j < 40; j++) {
			for (int i = 0; i < 10; i++) {
				if (grid[i][j].owner == Owner.BLANK) {
					squares[i][j].setBackground(java.awt.Color.WHITE);
					board2.getComponentAt(squares[i][j].getX(),
							squares[i][39 - j].getY()).setBackground(
							java.awt.Color.WHITE);
				} else if (grid[i][j].owner == Owner.GAME) {
					squares[i][j].setBackground(java.awt.Color.LIGHT_GRAY);
					board2.getComponentAt(squares[i][j].getX(),
							squares[i][39 - j].getY()).setBackground(
							java.awt.Color.LIGHT_GRAY);
				} else if (grid[i][j].owner == Owner.P1) {
					squares[i][j].setBackground(java.awt.Color.BLUE);
					board2.getComponentAt(squares[i][j].getX(),
							squares[i][39 - j].getY()).setBackground(
							java.awt.Color.BLUE);
				} else if (grid[i][j].owner == Owner.P2) {
					squares[i][j].setBackground(java.awt.Color.RED);
					board2.getComponentAt(squares[i][j].getX(),
							squares[i][39 - j].getY()).setBackground(
							java.awt.Color.RED);
				} else if (grid[i][j].owner == Owner.P1STOP) {
					squares[i][j].setBackground(java.awt.Color.CYAN);
					board2.getComponentAt(squares[i][j].getX(),
							squares[i][39 - j].getY()).setBackground(
							java.awt.Color.CYAN);
				} else if (grid[i][j].owner == Owner.P2STOP) {
					squares[i][j].setBackground(java.awt.Color.PINK);
					board2.getComponentAt(squares[i][j].getX(),
							squares[i][39 - j].getY()).setBackground(
							java.awt.Color.PINK);
				} else {
					squares[i][j].setBackground(java.awt.Color.BLACK);
					board2.getComponentAt(squares[i][j].getX(),
							squares[i][39 - j].getY()).setBackground(
							java.awt.Color.BLACK);
				}

			}
		}

		p1Lines.setText("Player 1 lines cleared: "+main.clines[0]);
		p2Lines.setText("Player 2 lines cleared: "+main.clines[1]);

		// board.setBackground(java.awt.Color.GREEN);
	}

	public void update(btBoard b2) {
		b = b2;
		display();
	}

}
