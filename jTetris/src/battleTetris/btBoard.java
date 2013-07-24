package battleTetris;

import tetris.Board.Owner;
import battleTetris.btMain.Direction;

public class btBoard {
	private btTetromino p1cTetromino;
	private btTetromino p2cTetromino;
	private Shape nextTetromino;
	private btTile[][] grid = new btTile[10][40];
	private btMain main;
	private int p1highest = 39;
	private int p2lowest = 0;
	private long lastdrop1=System.currentTimeMillis();
	private long lastdrop2=System.currentTimeMillis();

	public btBoard(btMain btMain) {
		main = btMain;
		for (int j = 0; j < 40; j++) {
			for (int i = 0; i < 10; i++) {
				grid[i][j] = new btTile(Owner.BLANK, i, j);
			}
		}
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 10; j++)
				if ((j + i) % 2 == 0)
					grid[j][i + 19].owner = Owner.GAME;
		}

		nextTetromino = Shape.random();
	}

	public enum Shape {
		SQUARE, LINE, T, LS, RS, LL, RL;
		// wooo stack overflow and java's stupid enumerators
		public static Shape random() {
			 //return LINE;
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
		case DROP:
			long l=System.currentTimeMillis();
			long lastdrop=player == Owner.P1 ? lastdrop1:lastdrop2;
			System.out.println(l-lastdrop);
			if(l-lastdrop>200){
			Direction i = player == Owner.P1 ? Direction.DOWN : Direction.UP;
			boolean loop = true;
			while (loop&&controlcheck(player, cTetromino)) {
				loop = move(i, player);
			}
			if(player==Owner.P1)
				lastdrop1=System.currentTimeMillis();
			else
				lastdrop2=System.currentTimeMillis();
//			lastdrop1=player == Owner.P1 ? System.currentTimeMillis():lastdrop2;
//			lastdrop2=player == Owner.P1 ? lastdrop2:System.currentTimeMillis();

			}
			else{
				System.out.println("less than two seconds have passed");
			}
			
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
			if (xOrigin + j * yRotate >= 10 || yOrigin + i * xRotate >= 40
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
	private boolean controlcheck(Owner player, btTetromino cTetromino){
		for (btTile t : cTetromino.tiles) {
		if (player == Owner.P1 && t.y > p1highest) {
			System.out.println("Player 1 control fails: "+p1highest);

			return false;
		}
		if (player == Owner.P2 && t.y < p2lowest) {
			System.out.println("Player 2 control fails: "+p2lowest);
			return false;
		}
		}
		return true;
	}
	private boolean movecheck(int x, int y, Owner player, btTetromino cTetromino) {
		System.out.println("Movecheck");
		// int lowest=player==Owner.P1?p1lowest:p2lowest;
		for (btTile t : cTetromino.tiles) {
			if (x + t.x >= 10 || y + t.y >= 40 || x + t.x < 0 || (y + t.y) < 0) {
				System.out.println("Bounds fail: x=" + (x + t.x) + " y="
						+ (y + t.y));

				return false;
			}


			if (grid[x + t.x][y + t.y].owner != Owner.BLANK
					&& grid[x + t.x][y + t.y].owner != player) {
				System.out.println("Space " + (x + t.x) + "," + (y + t.y)
						+ " occupied: " + grid[x + t.x][y + t.y].owner
						+ " is not " + player.toString());
				return false;
			}
		}
		System.out.println("Movecheck passed");

		return true;

	}

	private void stopTetromino(btTetromino cTetromino) {
		Owner newOwner = cTetromino.owner == Owner.P1 ? Owner.P1STOP
				: Owner.P2STOP;
		for (btTile t : cTetromino.tiles)
			grid[t.x][t.y].owner = newOwner;
		System.out.println("Stopping Tetromino");
		Owner loser = endcheck();
		if (loser != Owner.BLANK) {
			System.out.println("loser: " + loser.toString());
			main.gameOn = false;
		}
		boolean top = true;

		for (int j = 0; j < 40; j++) {
			for (int i = 0; i < 10; i++) {
				if (grid[i][j].owner == Owner.P1STOP&&newOwner==Owner.P1STOP)
					p1highest = j;
				if (grid[i][j].owner == Owner.P2STOP&&newOwner==Owner.P2STOP && top) {
					p2lowest = j;
					System.out.println("p2lowest changed");
					top = false;
				}
			}
		}
		System.out.println("p1highest: "+p1highest+" p2lowest: "+p2lowest);
		main.cycle(cTetromino.owner);

	}

	private Owner endcheck() {

		for (int i = 0; i < 10; i++) {
			if (grid[i][0].owner != Owner.BLANK && grid[i][0].owner != Owner.P1) {
				return Owner.P1;
			}
		}
		for (int i = 0; i < 10; i++) {
			if (grid[i][39].owner != Owner.BLANK
					&& grid[i][39].owner != Owner.P2) {
				return Owner.P2;
			}
		}
		return Owner.BLANK;
	}

	public int clear(Owner p) {
		int lines = 0;
		int a = p == Owner.P1 ? -1 : 1;
		for (int j = 0; j < 40; j++) {
			boolean full = true;
			for (int i = 0; i < 10; i++) {
				if (grid[i][j].owner == Owner.BLANK)
					full = false;
			}
			if (full) {
				lines++;
				for (int k = j; k >= 0 && k < 40; k += a) {
					for (int i = 0; i < 10; i++) {
						if (k == 0 || k == 39)
							grid[i][k].owner = Owner.BLANK;
						else {
							System.out.println(k + a);
							grid[i][k].owner = grid[i][k + a].owner;
						}
					}
				}
			}
		}
		System.out.println(lines + " lines cleared");
		return lines;

	}

}
