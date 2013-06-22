package game;

import game.Board.Owner;
import game.Board.Shape;
import game.Main.Direction;

public class Tetromino {
	public Tile[][] rotationMatrix= new Tile[4][4];
	public Tile[] tiles=new Tile[4];
	public int tetromino_x,tetromino_y;
	public Tetromino(Shape random, Owner p1) {
		tetromino_x=3;
		tetromino_y=0;
				
		// TODO Auto-generated constructor stub
	}

	public void rotate(Direction d){
		
	}
}
