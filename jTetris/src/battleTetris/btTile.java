package battleTetris;

import tetris.Board.Owner;
import battleTetris.btBoard.Shape;

public class btTile {
	//public Shape shape;
	public Owner owner;
	public int x;
	public int y;
	public btTile(Owner creator, int X, int Y) {
			//shape=random;
			owner=creator;
			x=X;
			y=Y;
			}

}
