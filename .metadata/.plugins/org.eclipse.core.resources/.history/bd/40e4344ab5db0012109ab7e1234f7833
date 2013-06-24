package game;

import game.Main.Direction;

public class Board {
	private boolean gameOn, unstacked;
	private Tetromino currentTetromino;
	private Tetromino nextTetromino;
	private Tile[][] grid = new Tile[10][20];

	public Board(boolean GameOn, boolean Unstacked) {
		gameOn = GameOn;
		unstacked = Unstacked;

		nextTetromino = new Tetromino(Shape.random(), Owner.P1);

	}

	public enum Shape {
		SQUARE, LINE, T, LS, RS, LL, RL;
		// wooo stack overflow and java's stupid enumerators
		public static Shape random() {
			return values()[(int) (Math.random() * values().length)];
		}
	}

	public enum Owner {
		P1, P2, GAME
	}

	public void spawn() {
		currentTetromino = nextTetromino;
		nextTetromino = new Tetromino(Shape.random(), Owner.P1);

	}

	public void move(Direction d) {
		switch (d) {
		case UP:
			// displays next tile somewhere
			break;
		case DOWN:
			if (movecheck(0, -1))
				for (Tile t : currentTetromino.tiles)
					t.y -= 1;
			else {
				stopTetromino();
				unstacked = false;
			}
			break;
		case LEFT:
			if (movecheck(-1, 0))
				for (Tile t : currentTetromino.tiles) {
					grid[t.x][t.y] = null;
					t.x += -1;
					grid[t.x][t.y] = t;
				}
			break;
		case RIGHT:
			if (movecheck(1, 0))
				for (Tile t : currentTetromino.tiles) {
					grid[t.x][t.y] = null;
					t.x += 1;
					grid[t.x][t.y] = t;
				}
			break;
		case CW:

			break;
		case CCW:

			break;
		case NONE:

			break;

		}

	}

	private void stopTetromino() {
		for (Tile t : currentTetromino.tiles)
			t.owner = Owner.GAME;
	}

	public boolean movecheck(int x, int y) {
		for (Tile t : currentTetromino.tiles)
			if (grid[x + t.x][y + t.y].owner == Owner.GAME)
				return false;
		return true;

	}

}
