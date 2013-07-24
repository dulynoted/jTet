package tetris;

import AI.Frank;
import battleTetris.btMain;

public class Driver {

	public static void main(String[] args) {
		//new Main(0);
		btMain main =new btMain();
		
		new Frank(main);

	}

}
