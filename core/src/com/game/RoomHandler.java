package com.game;

import com.battlechess.BattleChess;
import com.entity.Player;

public class RoomHandler implements Runnable{
	public RoomHandler() {
		Space[][] boardMaker = new Space[16][16];
		if(!Board.isFirstRoom) {
			
			for(int r = 0;r<boardMaker.length;r++)
				for(int c = 0;c<boardMaker[r].length;c++) {
					if(r<2 || r > 13 || c < 2)
						boardMaker[r][c] = new Space(null,Space.State.CLEAR);
					else if(r == 2 || r == 13 || c == 2)
						boardMaker[r][c] = new Space(null,Space.State.WALL);
					else if(r >= 2 && r <= 13 && c == 15) {
						boardMaker[r][c] = new Space(null,Space.State.WALL);
						if(r == 12 && c == 15)
							boardMaker[r][c].setExit(true);
					}
					else {
						if(r == 3 && c == 3) {
							Player.getPlayer().setPos(r,c);
							boardMaker[r][c] = new Space(Player.getPlayer(),Space.State.FLOOR);
						}else
							boardMaker[r][c] = new Space(null,Space.State.FLOOR);
					}
				}
			Board.getBoard().newBoard(boardMaker);
			
		}else
			Board.getBoard().newBoard(genBoard(boardMaker));
			
	}
	
	private Space[][] genBoard(Space[][] board) {
		int ranStartMinR = (int)(Math.random() * 9);
		int ranStartMaxR = ranStartMinR + 5 + (int)(Math.random() * (11 - ranStartMinR));
		if(ranStartMaxR >= 16)
			ranStartMaxR = 15;
		int ranStartGate = ranStartMinR + (int)(Math.random() * (ranStartMaxR - ranStartMinR));
		if(ranStartGate >= ranStartMaxR)
			ranStartGate = ranStartMaxR - 2;
		if(ranStartGate <= ranStartMinR)
			ranStartGate = ranStartMinR + 2;
		System.out.println(ranStartMinR + "," + ranStartMaxR + "," + ranStartGate);
		for(int r = 0;r<board.length;r++) {
			if(r >= ranStartMinR && r <= ranStartMaxR) {
				board[r][0] = new Space(null,Space.State.WALL);
				if(r == ranStartGate)
					board[r][0].setEntrance(true);
				if(r == ranStartMinR || r == ranStartMaxR)
					board[r][1] = new Space(null,Space.State.WALL);
			}
			else
				board[r][0] = new Space(null,Space.State.CLEAR);
			
		}
		for(int r = 0;r<board.length;r++)
			for(int c = 1;c<board[r].length;c++) 
				if(r < ranStartMaxR && r > ranStartMinR && c > 0 && c < 5)
					board[r][c] = new Space(null,Space.State.FLOOR);
				else if(r == ranStartMaxR && r == ranStartMinR && c > 0 && c < 5)
					board[r][c] = new Space(null,Space.State.FLOOR);
				else if(board[r][c] ==  null)
					board[r][c] = new Space(null,Space.State.CLEAR);
		
		
		
		
		return board;
	}
	
	@Override
	public void run() {
		while(!Board.exit) {
			//System.out.println("Checking for new Board!");
		}
		Board.exit = false;
		(new Thread(new RoomHandler())).start();
	}
	
	

}
