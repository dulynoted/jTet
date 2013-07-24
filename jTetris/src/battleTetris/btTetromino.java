package battleTetris;

import tetris.Board.Owner;
import battleTetris.btBoard.Shape;

public class btTetromino {
	public btTile[] tiles=new btTile[4];
	public int x,y;
	public Shape shape;
	public Owner owner;
	public btTetromino(btTile[][] grid, Shape shape, Owner p) {
		owner=p;
		x=4;
		y=Owner.P1==p ? 0: 39;
		int i=Owner.P1==p ? 1:-1;
		this.shape=shape;
		System.out.println("SHAPE: "+ shape.toString());
		switch(shape){
		case SQUARE:
			tiles[0]=new btTile(p, x+0, y+0*i);
			tiles[1]=new btTile(p, x+1, y+1*i);
			tiles[2]=new btTile(p, x+1, y+0*i);
			tiles[3]=new btTile(p, x+0, y+1*i);
			break;
		case LINE:
			
			tiles[0]=new btTile(p, x-1, y+0*i);
			tiles[1]=new btTile(p, x+0, y+0*i);
			tiles[2]=new btTile(p, x+1, y+0*i);
			tiles[3]=new btTile(p, x+2, y+0*i);
			break;
		case T:
			tiles[0]=new btTile(p, x+0, y+0*i);
			tiles[1]=new btTile(p, x-1, y+0*i);
			tiles[2]=new btTile(p, x+1, y+0*i);
			tiles[3]=new btTile(p, x+0, y+1*i);
			break;
		case RS:
			x=5;
			tiles[0]=new btTile(p, x+0, y+0*i);
			tiles[1]=new btTile(p, x-1, y+0*i);
			tiles[2]=new btTile(p, x+0, y+1*i);
			tiles[3]=new btTile(p, x+1, y+1*i);
			break;	
		case LS:
			tiles[0]=new btTile(p, x+0, y+0*i);
			tiles[1]=new btTile(p, x+1, y+0*i);
			tiles[2]=new btTile(p, x+0, y+1*i);
			tiles[3]=new btTile(p, x-1, y+1*i);
			break;
		case RL:
			x=5;
			tiles[0]=new btTile(p, x+0, y+0*i);
			tiles[1]=new btTile(p, x+0, y+1*i);
			tiles[2]=new btTile(p, x-1, y+0*i);
			tiles[3]=new btTile(p, x-2, y+0*i);
			break;
		case LL:
			tiles[0]=new btTile(p, x+0, y+0*i);
			tiles[1]=new btTile(p, x+0, y+1*i);
			tiles[2]=new btTile(p, x+1, y+0*i);
			tiles[3]=new btTile(p, x+2, y+0*i);
			break;
		}
	
	
		for(btTile t:tiles)
			grid[t.x][t.y]=t;
	}
}
