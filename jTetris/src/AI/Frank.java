package AI;

import tetris.Board.Owner;
import battleTetris.btBoard;
import battleTetris.btMain;
import battleTetris.btMain.Direction;

public class Frank {
	private btBoard board;

	public Frank(btMain main){
		System.out.println("frank's gonna mess you up");
		board=main.showboard();
		while(main.gameOn){
			board.move(Direction.DOWN, Owner.P2);

		}
	}
}
