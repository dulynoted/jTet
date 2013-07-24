package tetris;

import tetris.Board.Owner;
import tetris.Board.Shape;

public class Tile {
	//public Shape shape;
	public Owner owner;
	public int x;
	public int y;
	public Tile(Owner creator, int X, int Y) {
			//shape=random;
			owner=creator;
			x=X;
			y=Y;
			}

}
