package tetris;


import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.*;

import javax.swing.*;

import tetris.Board.Owner;
import tetris.Main.*;

public class GameInterface {
	private Scanner s = new Scanner(System.in);
	private Tile[][] grid;
	private Board b;
	private Main main;
	private JFrame frame = new JFrame();
	private JPanel board = new JPanel();
	private JPanel info = new JPanel();
	private JPanel[][] squares = new JPanel[10][20];
	private JLabel gameInfo = new JLabel();
	private InputMap im;
	private ActionMap am;

	public GameInterface(Board b1, Main MAIN) {
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
		for (int j = 0; j < 20; j++) {
			for (int i = 0; i < 10; i++) {
				squares[i][j] = new JPanel();
				board.add(squares[i][j]);
				squares[i][j].setLocation(i * 25 + i, j * 25 + j);
				squares[i][j].setBackground(java.awt.Color.WHITE);
				squares[i][j].setSize(new Dimension(25, 25));
			}
		}

		JButton restart = new JButton();
		restart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				main.reset();
				grid=b.showBoard();
				display();
			}
		});

		JPanel GameINFO = new JPanel();
		info.add(restart);
		info.add(GameINFO);
		info.setPreferredSize(new Dimension(150, 520));
		board.setPreferredSize(new Dimension(260, 520));
		frame.pack();
		GameINFO.add(gameInfo);
		gameInfo.setLocation(0, 0);
		gameInfo.setBackground(java.awt.Color.WHITE);
		gameInfo.setText(main.clines + " lines cleared.");
		gameInfo.setPreferredSize(new Dimension(150, 50));
		restart.setText("RESTART");
		restart.setPreferredSize(new Dimension(150, 50));

		im = board.getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW);
		am = board.getActionMap();
		addListeners(im,am);
		
	}
	public void addListeners(InputMap im, ActionMap am){
		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0), "right");
		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0), "left");
		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_W, 0), "up");
		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0), "down");
		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_Q, 0), "CounterClockwise");
		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_E, 0), "Clockwise");

		am.put("right", new ArrowAction("right"));
		am.put("left", new ArrowAction("left"));
		am.put("up", new ArrowAction("up"));
		am.put("down", new ArrowAction("down"));
		am.put("CounterClockwise", new ArrowAction("ccw"));
		am.put("Clockwise", new ArrowAction("cw"));
	}
	public void removeListeners(){
		im.clear();
		am.clear();
	}
	
	
	@SuppressWarnings("serial")
	private class ArrowAction extends AbstractAction {

		String direction;

		public ArrowAction(String d) {
			direction = d;
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			if (direction == "down")
				b.move(0, Direction.DOWN);
			else if (direction == "left")
				b.move(0, Direction.LEFT);
			else if (direction == "right")
				b.move(0, Direction.RIGHT);
			else if (direction == "up")
				b.move(0, Direction.UP);
			else if (direction == "cw")
				b.move(0, Direction.CW);
			else if (direction == "ccw")
				b.move(0, Direction.CCW);
			else
				b.move(0, Direction.NONE);

			display();
		}

	}

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

	}

	public void display() {
		for (int j = 0; j < 20; j++) {
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
					line += "X";

			}
			line += "|";

			System.out.println(line);
		}
		System.out.println("-------------");
		System.out.println();

		for (int j = 0; j < 20; j++) {
			for (int i = 0; i < 10; i++) {
				if (grid[i][j].owner == Owner.BLANK)
					squares[i][j].setBackground(java.awt.Color.WHITE);
				else if (grid[i][j].owner == Owner.GAME)
					squares[i][j].setBackground(java.awt.Color.LIGHT_GRAY);
				else if (grid[i][j].owner == Owner.P1)
					squares[i][j].setBackground(java.awt.Color.BLUE);
				else if (grid[i][j].owner == Owner.P2)
					squares[i][j].setBackground(java.awt.Color.RED);

			}
		}

		gameInfo.setText(main.clines + " lines cleared.");

		// board.setBackground(java.awt.Color.GREEN);
	}

	public void update(Board b2) {
		b = b2;
		display();
	}

}
