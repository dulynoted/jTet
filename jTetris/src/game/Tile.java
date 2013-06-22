package game;

import game.Board.Owner;
import game.Board.Shape;

public class Tile {
	//public Shape shape;
	public Owner owner;
	public int x;
	public int y;
	public Tile(Shape random, Owner creator, int X, int Y) {
			//shape=random;
			owner=creator;
			x=X;
			y=Y;
			}

}
