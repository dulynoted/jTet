package game;
import game.Main.Direction;

import java.io.*;
import java.util.*;
public class GameInterface {

	static Scanner s=new Scanner(System.in);
	
	public GameInterface(){
	}
	public Direction input() {	
		//currently for textline input, will implement keylistener to directly send enum
		String input=s.nextLine();
		Direction d=Direction.valueOf(input.toUpperCase());
		return d;
		
				
	}

}
