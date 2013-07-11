package battleTetris;

import tetris.Board.Owner;
import battleTetris.btMain.Direction;

public class btBoard {
	private btTetromino p1cTetromino;
	private btTetromino p2cTetromino;
	private Shape nextTetromino;
	private btTile[][] grid = new btTile[10][41];

	public btBoard(boolean GameOn) {
		for (int j = 0; j < 41; j++) {
			for (int i = 0; i < 10; i++) {
				grid[i][j] = new btTile(Owner.BLANK, i, j);
			}
		}
		nextTetromino = Shape.random();
	}

	public enum Shape {
		SQUARE, LINE, T, LS, RS, LL, RL;
		// wooo stack overflow and java's stupid enumerators
		public static Shape random() {
			// return LINE;
			return values()[(int) (Math.random() * values().length)];
		}
	}

	public btTile[][] showBoard() {
		return grid;
	}

	public void spawn(Owner player) {
		if (player == Owner.P1)
			p1cTetromino = new btTetromino(grid, nextTetromino, player);
		else
			p2cTetromino = new btTetromino(grid, nextTetromino, player);
		System.out.println(nextTetromino.toString());

		nextTetromino = Shape.random();
	}

	public boolean move(Direction d, Owner player) {
		btTetromino cTetromino = player == Owner.P1 ? p1cTetromino
				: p2cTetromino;
		System.out.println(cTetromino.owner + " is the owner");
		switch (d) {
		case UP:
			if (movecheck(0, -1, player, cTetromino)) {
				for (btTile t : cTetromino.tiles)
					grid[t.x][t.y].owner = Owner.BLANK;
				for (btTile t : cTetromino.tiles) {
					grid[t.x][t.y - 1].owner = player;
					t.y -= 1;
				}
				cTetromino.y -= 1;
			}

			else {
				stopTetromino(cTetromino);
				return false;
			}
			break;
		case DOWN:
			if (movecheck(0, 1, player, cTetromino)) {
				for (btTile t : cTetromino.tiles)
					grid[t.x][t.y].owner = Owner.BLANK;
				for (btTile t : cTetromino.tiles) {
					grid[t.x][t.y + 1].owner = player;
					t.y += 1;
				}
				cTetromino.y += 1;
			}

			else {
				stopTetromino(cTetromino);
				return false;
			}
			break;
		case LEFT:
			if (movecheck(-1, 0, player, cTetromino)) {
				for (btTile t : cTetromino.tiles)
					grid[t.x][t.y].owner = Owner.BLANK;
				for (btTile t : cTetromino.tiles) {
					t.x += -1;
					grid[t.x][t.y].owner = player;
				}
				cTetromino.x -= 1;
			}
			break;
		case RIGHT:
			if (movecheck(1, 0, player, cTetromino)) {
				for (btTile t : cTetromino.tiles)
					grid[t.x][t.y].owner = Owner.BLANK;
				for (btTile t : cTetromino.tiles) {
					t.x += 1;
					grid[t.x][t.y].owner = player;
				}
				cTetromino.x += 1;
			}
			break;
		case CW:
			if (cTetromino.shape == Shape.SQUARE)
				break;
			rotate(1, -1, true, player);

			break;
		case CCW:
			if (cTetromino.shape == Shape.SQUARE)
				break;
			rotate(-1, 1, true, player);

			break;
		case NONE:
			// Oh Danny boy, the piiiiiipes, the piiiiipes are callin'...
			break;

		}
		return true;
	}

	private void rotate(int i, int j, boolean undeep, Owner player) {
		btTetromino cTetromino = player == Owner.P1 ? p1cTetromino
				: p2cTetromino;

		int xOrigin = cTetromino.x;
		int yOrigin = cTetromino.y;
		boolean pass = true;
		for (btTile t : cTetromino.tiles) {
			int xRotate = t.x - xOrigin;
			int yRotate = t.y - yOrigin;

			// System.out.println("xOrigin:" + xOrigin + " yRotate: " + j*
			// yRotate);
			if (xOrigin + j * yRotate >= 10 || yOrigin + i * xRotate >= 20
					|| xOrigin + j * yRotate < 0 || yOrigin + i * xRotate < 0)
				pass = false;

			else if (grid[xOrigin + j * yRotate][yOrigin + i * xRotate].owner == Owner.GAME) {
				pass = false;
			}

		}
		if (pass) {
			for (btTile t : cTetromino.tiles)
				grid[t.x][t.y].owner = Owner.BLANK;
			for (btTile t : cTetromino.tiles) {
				int xRotate = t.x - xOrigin;
				int yRotate = t.y - yOrigin;
				grid[xOrigin + j * yRotate][yOrigin + i * xRotate].owner = player;
				t.x = xOrigin + j * yRotate;
				t.y = yOrigin + i * xRotate;
			}
		} else if (undeep) {
			if (cTetromino.shape == Shape.LINE) {
				rotate(-1 * i, -1 * j, false, player);
			}
		}
	}

	private void stopTetromino(btTetromino cTetromino) {
		Owner newOwner = cTetromino.owner == Owner.P1 ? Owner.P1STOP
				: Owner.P2STOP;
		for (btTile t : cTetromino.tiles)
			grid[t.x][t.y].owner = newOwner;
		System.out.println("Stopping Tetromino");
	}

	public boolean movecheck(int x, int y, Owner player, btTetromino cTetromino) {
		System.out.println("Movecheck");

		for (btTile t : cTetromino.tiles) {
			if (x + t.x >= 10 || y + t.y >= 41 || x + t.x < 0 || (y + t.y) < 0) {
				System.out.println("Bounds fail: " + y + t.y);

				return false;
			}
			if (grid[x + t.x][y + t.y].owner != Owner.BLANK
					&& grid[x + t.x][y + t.y].owner != player) {
				System.out.println("Space occupied: "
						+ grid[x + t.x][y + t.y].owner + " is not "
						+ player.toString()
						+ (grid[x + t.x][y + t.y].owner != player));

				return false;
			}
		}
		System.out.println("Movecheck passed");

		return true;

	}

	public int clear(Owner p) {
		int lines = 0;
		for (int j = 0; j < 20; j++) {
			boolean full = true;
			for (int i = 0; i < 10; i++) {
				if (grid[i][j].owner == Owner.BLANK)
					full = false;
			}
			if (full) {
				lines++;
				/*
				 * for (int i = 0; i < 10; i++) { grid[i][j].owner = Owner.P2; }
				 */
				for (int k = j; k >= 0; k--) {
					for (int i = 0; i < 10; i++) {
						if (k == 0)
							grid[i][k].owner = Owner.BLANK;
						else {
							grid[i][k].owner = grid[i][k - 1].owner;
						}
					}
				}
			}
		}
		System.out.println(lines);
		return lines;

	}

	public boolean endcheck() {
		for (int i = 0; i < 10; i++) {
			if (grid[i][0].owner != Owner.BLANK)
				return false;
		}
		return true;
	}

}
