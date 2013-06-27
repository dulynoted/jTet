package game;

import game.Board.Owner;
import game.Board.Shape;
import game.Main.Direction;

public class Tetromino {
	public Tile[] tiles=new Tile[4];
	public int x,y;
	public Shape shape;
	public Tetromino(Tile[][] grid, Shape shape, Owner p) {
		x=3;
		y=0;
		this.shape=shape;
		switch(shape){
		case SQUARE:
			tiles[0]=new Tile(p, x+1, y+1);
			tiles[1]=new Tile(p, x+2, y+1);
			tiles[2]=new Tile(p, x+1, y+0);
			tiles[3]=new Tile(p, x+2, y+0);
			break;
		case LINE:
			tiles[0]=new Tile(p, x+0, y+0);
			tiles[1]=new Tile(p, x+1, y+0);
			tiles[2]=new Tile(p, x+0, y+1);
			tiles[3]=new Tile(p, x+1, y+1);
			break;
		case T:
			tiles[0]=new Tile(p, x+0, y+0);
			tiles[1]=new Tile(p, x+1, y+0);
			tiles[2]=new Tile(p, x+0, y+1);
			tiles[3]=new Tile(p, x+1, y+1);
			break;
		case RS:
			tiles[0]=new Tile(p, x+0, y+0);
			tiles[1]=new Tile(p, x+1, y+0);
			tiles[2]=new Tile(p, x+0, y+1);
			tiles[3]=new Tile(p, x+1, y+1);
			break;	
		case LS:
			tiles[0]=new Tile(p, x+0, y+0);
			tiles[1]=new Tile(p, x+1, y+0);
			tiles[2]=new Tile(p, x+0, y+1);
			tiles[3]=new Tile(p, x+1, y+1);
			break;
		case RL:
			tiles[0]=new Tile(p, x+0, y+0);
			tiles[1]=new Tile(p, x+1, y+0);
			tiles[2]=new Tile(p, x+0, y+1);
			tiles[3]=new Tile(p, x+1, y+1);
			break;
		case LL:
			tiles[0]=new Tile(p, x+0, y+0);
			tiles[1]=new Tile(p, x+1, y+0);
			tiles[2]=new Tile(p, x+0, y+1);
			tiles[3]=new Tile(p, x+1, y+1);
			break;
		}
	
	
		for(Tile t:tiles)
			grid[t.x][t.y]=t;
	}
}
