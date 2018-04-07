package com.game;

public class RoomHandler implements Runnable{
	
	public RoomHandler() {
		if(Board.isFirstRoom) {
			Space[][] boardMaker = new Space[16][16];
			for(int r = 0;r<boardMaker.length;r++)
				for(int c = 0;c<boardMaker[r].length;c++) {
					
				}
					
			Board b = new Board(boardMaker);
		}
			
	}
	
	@Override
	public void run() {
		
		
	}

}
