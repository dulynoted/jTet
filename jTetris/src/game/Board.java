package game;

import game.Main.Direction;

public class Board {
	private Tetromino cTetromino;
	private Shape nextTetromino;
	private Tile[][] grid = new Tile[10][20];

	public Board(boolean GameOn, boolean Unstacked) {
		for (int j = 0; j < 20; j++) {
			for (int i = 0; i < 10; i++) {
				grid[i][j] = new Tile(Owner.BLANK, i, j);
			}
		}
		nextTetromino = Shape.random();

	}

	public enum Shape {
		SQUARE, LINE, T, LS, RS, LL, RL;
		// wooo stack overflow and java's stupid enumerators
		public static Shape random() {
			//return SQUARE;
			return values()[(int) (Math.random() * values().length)];
		}
	}

	public enum Owner {
		P1, P2, GAME, BLANK
	}

	public Tile[][] showBoard() {
		return grid;
	}

	public void spawn() {
		cTetromino = new Tetromino(grid, nextTetromino, Owner.P1);
		nextTetromino = Shape.random();
		System.out.println(cTetromino.shape.toString());
	}

	public boolean move(Direction d) {
		switch (d) {
		case UP:
			// displays next tile somewhere
			break;
		case DOWN:
			if (movecheck(0, 1)) {
				for (Tile t : cTetromino.tiles)
					grid[t.x][t.y].owner = Owner.BLANK;
				for (Tile t : cTetromino.tiles) {
					grid[t.x][t.y + 1].owner = Owner.P1;
					t.y += 1;
				}
				cTetromino.y += 1;
			}

			else {
				stopTetromino();
				return false;
			}
			break;
		case LEFT:
			if (movecheck(-1, 0)) {
				for (Tile t : cTetromino.tiles)
					grid[t.x][t.y].owner = Owner.BLANK;
				for (Tile t : cTetromino.tiles) {
					t.x += -1;
					grid[t.x][t.y].owner = Owner.P1;
				}
				cTetromino.x -= 1;
			}
			break;
		case RIGHT:
			if (movecheck(1, 0)) {
				for (Tile t : cTetromino.tiles)
					grid[t.x][t.y].owner = Owner.BLANK;
				for (Tile t : cTetromino.tiles) {
					t.x += 1;
					grid[t.x][t.y].owner = Owner.P1;
				}
				cTetromino.x += 1;
			}
			break;
		case CW:
			if (cTetromino.shape == Shape.SQUARE)
				break;
			rotate(-1, 1);
			break;
		case CCW:
			if (cTetromino.shape == Shape.SQUARE)
				break;
			rotate(1, -1);
			break;
		case NONE:
			// Oh Dhanny bhoy, the piiiiiipes, the piiiiipes are callin'...
			break;

		}
		return true;
	}

	private void rotate(int i, int j) {
		int xOrigin = cTetromino.x + 1;
		int yOrigin = cTetromino.y + 1;
		boolean pass = true;
		for (Tile t : cTetromino.tiles) {
			int xRotate = t.x - xOrigin;
			int yRotate = t.y - yOrigin;
			System.out.println("xOrigin:" + xOrigin + " yRotate: " + j
					* yRotate);
			if (grid[xOrigin + j * yRotate][yOrigin + i * xRotate].owner == Owner.GAME) {
				pass = false;
			}
			if (xOrigin + j * yRotate >= 10 || yOrigin + i * xRotate >= 20
					|| xOrigin - j * yRotate <= 0 || yOrigin + i * xRotate <= 0)
				pass = false;
		}

		if (pass) {
			for (Tile t : cTetromino.tiles)
				grid[t.x][t.y].owner = Owner.BLANK;
			for (Tile t : cTetromino.tiles) {
				int xRotate = t.x - xOrigin;
				int yRotate = t.y - yOrigin;
				grid[xOrigin + j * yRotate][yOrigin + i * xRotate].owner = Owner.P1;
				t.x = xOrigin + j * yRotate;
				t.y = yOrigin + i * xRotate;
			}
		}
	}

	private void stopTetromino() {
		for (Tile t : cTetromino.tiles)
			grid[t.x][t.y].owner=Owner.GAME;
			
			System.out.println("HYYYAAAAALAlalalaala");
			
		// t=null;
	}

	public boolean movecheck(int x, int y) {
		System.out.println("Movecheck");

		for (Tile t : cTetromino.tiles) {
			if (x + t.x >= 10 || y + t.y >= 20 || x + t.x < 0 || (y + t.y) <= 0)
				return false;
			if (grid[x + t.x][y + t.y].owner == Owner.GAME)
				return false;
		}
		System.out.println("Movecheck passed");

		return true;

	}

	public void clear() {
		for (int j = 0; j < 20; j++) {
			boolean full = true;
			for (int i = 0; i < 10; i++) {
				if (grid[i][j].owner == Owner.BLANK)
					full = false;
			}
			if (full) {
				for (int i = 0; i < 10; i++) {
					grid[i][j].owner = Owner.BLANK;
				}
				for (int k = j; k < 20; k++) {
					for (int i = 0; i < 10; i++) {
						if (k == 19)
							grid[i][j].owner = Owner.BLANK;
						else {
							grid[i][j] = grid[i][j - 1];
							grid[i][j].y += 1;
						}
					}
				}

			}
		}

	}

	public boolean endcheck() {
		for (int i = 0; i < 10; i++) {
			if (grid[i][0].owner != Owner.BLANK)
				return false;
		}
		return true;
	}

}
