package com.game;

import com.entity.Player;

public class RoomHandler implements Runnable{
	Player p;
	RoomHandler room;
	public RoomHandler() {
		if(Board.isFirstRoom) {
			Space[][] boardMaker = new Space[16][16];
			for(int r = 0;r<boardMaker.length;r++)
				for(int c = 0;c<boardMaker[r].length;c++) {
					if(r<2 || r > 13 || c < 2)
						boardMaker[r][c] = new Space(null,Space.State.CLEAR);
					else if(r == 2 || r == 13 || c == 2)
						boardMaker[r][c] = new Space(null,Space.State.WALL);
					else if(r == 4 && c == 0) {
						p = new Player(r,c);
						boardMaker[r][c] = new Space(p,Space.State.WALL);
					}
					else
						boardMaker[r][c] = new Space(null,Space.State.USED);
				}
			Board b = new Board(boardMaker);
		}
			
	}
	
	@Override
	public void run() {
		while(!Board.exit) {}
		Board.exit = true;
		room = new RoomHandler();
		(new Thread(room)).start();
	}

}
