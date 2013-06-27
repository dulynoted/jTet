package game;

import game.Board.Owner;
import game.Main.Direction;

import java.io.*;
import java.util.*;

public class GameInterface {

	static Scanner s = new Scanner(System.in);
	private Tile[][] grid;
	
	public GameInterface(Tile[][] g) {
		grid=g;
		
	}

	public Direction input() {
		// currently for textline input, will implement keylistener to directly
		// send enum
		String input = s.nextLine();
		Direction d = Direction.valueOf(input.toUpperCase());
		System.out.println(d);
		return d;

	}

	public void display() {
		for (int j = 0; j <20; j++) {
			String line = "|";
			for (int i = 0; i < 10; i++) {
				if(grid[i][j]==null)
					line+="X";
			else if(grid[i][j].owner==Owner.BLANK)
					line+=" ";
				else if(grid[i][j].owner==Owner.GAME)
					line+="O";
				else if(grid[i][j].owner==Owner.P1)
					line+="@";
				else if(grid[i][j].owner==Owner.P2)
					line+="X";
				
				}
			line+="|";

			System.out.println(line);
		}
		System.out.println("-------------");
		System.out.println();
	}

}
