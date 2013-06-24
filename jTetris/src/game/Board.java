package game;

import game.Main.Direction;

public class Board {
	private boolean gameOn, unstacked;
	private Tetromino cTetromino;
	private Tetromino nextTetromino;
	private Tile[][] grid = new Tile[10][20];

	public Board(boolean GameOn, boolean Unstacked) {
		gameOn = GameOn;
		unstacked = Unstacked;
		nextTetromino = new Tetromino(grid, Shape.random(), Owner.P1);

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

	public Tile[][] showBoard(){
		return grid;
	}
	
	public void spawn() {
		cTetromino = nextTetromino;
		nextTetromino = new Tetromino(grid, Shape.random(), Owner.P1);

	}

	public void move(Direction d) {
		switch (d) {
		case UP:
			// displays next tile somewhere
			break;
		case DOWN:
			if (movecheck(0, -1)) {
				for (Tile t : cTetromino.tiles)
					t.y -= 1;
				cTetromino.y -= 1;
			}

			else {
				stopTetromino();
				unstacked = false;
			}
			break;
		case LEFT:
			if (movecheck(-1, 0)) {
				for (Tile t : cTetromino.tiles) {
					grid[t.x][t.y] = null;
					t.x += -1;
					grid[t.x][t.y] = t;
				}
				cTetromino.x -= 1;
			}
			break;
		case RIGHT:
			if (movecheck(1, 0)) {
				for (Tile t : cTetromino.tiles) {
					grid[t.x][t.y] = null;
					t.x += 1;
					cTetromino.x += 1;
					grid[t.x][t.y] = t;
				}
				cTetromino.x -= 1;
			}
			break;
		case CW:
			if(cTetromino.shape==Shape.SQUARE)
				break;
			rotate(1, -1);
			break;
		case CCW:
			if(cTetromino.shape==Shape.SQUARE)
				break;
			rotate(-1, 1);
			break;
		case NONE:
			//
			break;

		}

	}

	private void rotate(int i, int j) {
		int xOrigin = cTetromino.x + 1;
		int yOrigin = cTetromino.y + 1;
		for (Tile t : cTetromino.tiles) {
			int xRotate = t.x - xOrigin;
			int yRotate = t.y - yOrigin;
			if (grid[xOrigin + j * yRotate][yOrigin + i * xRotate].owner == Owner.GAME) {
				break;
			}
			if (xOrigin + j * yRotate >= 10 || yOrigin + i * xRotate >= 20
					|| xOrigin - j * yRotate <= 0 || yOrigin + i * xRotate <= 0)
				break;
		}
		for (Tile t : cTetromino.tiles) {
			int xRotate = t.x - xOrigin;
			int yRotate = t.y - yOrigin;
			grid[xOrigin + j * yRotate][yOrigin + i * xRotate] = t;
			grid[t.x][t.y] = null;
			t.x = xOrigin + j * yRotate;
			t.y = yOrigin + i * xRotate;
		}
	}

	private void stopTetromino() {
		for (Tile t : cTetromino.tiles)
			t.owner = Owner.GAME;
	}

	public boolean movecheck(int x, int y) {
		for (Tile t : cTetromino.tiles) {
			if (x + t.x >= 10 || y + t.y >= 20 || x + t.x <= 0 || y + t.y <= 0)
				return false;
			if (grid[x + t.x][y + t.y].owner == Owner.GAME)
				return false;
		}
		return true;

	}

	public void clear() {
		for (int j = 0; j >= 0; j++) {
			boolean full = true;
			for (int i = 0; i < 10; i++) {
				if (grid[i][j] == null)
					full = false;
			}
			if (full) {
				for (int i = 0; i < 10; i++) {
					grid[i][j] = null;
				}
				for (int k = j; k <20; k++) {
					for (int i = 0; i < 10; i++) {
						if(k==19)
							grid[i][j] = null;
						else{
						grid[i][j] = grid[i][j-1];
						grid[i][j].y-=1;
						}
					}
				}
				
			}
		}

	}
	public boolean endcheck(){
		for (int i = 0; i < 10; i++) {
			if(grid[i][19] != null)
				return true;
		}
		return false;
	}

}
