package com.game;

import com.entity.enemies.*;
import com.entity.items.*;
import com.badlogic.gdx.utils.TimeUtils;
import com.battlechess.BattleChess;
import com.entity.Player;
import com.framework.GameState;

import java.sql.Time;

public class RoomHandler implements Runnable{
	private static int ranStartGate = 0;
	private static int roomCount = 0;
	//The R/C values of the newly added section
	private int addedR = 0;
	private int addedC = 0;
	//											up	  right down  left
	private static final boolean[] DIRECTION = {false,false,false,false};
	
	public RoomHandler() {
		Space[][] boardMaker = new Space[17][17];
		if(Board.isFirstRoom) {
			
			for(int r = 0;r<boardMaker.length;r++)
				for(int c = 0;c<boardMaker[r].length;c++) {
					if(r<2 || r > 13 || c < 2)
						boardMaker[r][c] = new Space(null,Space.State.CLEAR);
					else if(r == 2 || r == 13 || c == 2)
						boardMaker[r][c] = new Space(null,Space.State.WALL);
					else if(r >= 2 && r <= 13 && c == 16) {
						boardMaker[r][c] = new Space(null,Space.State.WALL);
						if(r == 12 && c == 16) {
							boardMaker[r][c].setExit(true);
							Rook rook = new Rook(15,r);
							boardMaker[r][c].setEntity(rook);
						}
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
			Board.getBoard().getSpaces()[3][3].setEntity(Player.getPlayer());
		}else
			Board.getBoard().newBoard(genBoard(boardMaker));
			
	}
	
	private Space[][] genBoard(Space[][] board) {
		roomCount++;
		int twistCount = 0;
		int fillCount = 0;
		int rewardCount = 0;
		int coverCount = 0;
		int ranStartMinR = 0;
		int ranStartMaxR = 0;
		while((ranStartMaxR - ranStartMinR) % 5 != 1) {
		 ranStartMinR = (int)(Math.random() * 3) * 5;
		 ranStartMaxR = ranStartMinR + 6 + (int)(Math.random() * (11 - ranStartMinR));
		
		if(ranStartMaxR >= 17)
			ranStartMaxR = 16;
		}
		  ranStartGate = ranStartMinR + (int)(Math.random() * (ranStartMaxR - ranStartMinR));

		if(ranStartGate >= ranStartMaxR)
			ranStartGate = ranStartMaxR - 1;
		if(ranStartGate <= ranStartMinR)
			ranStartGate = ranStartMinR + 1;
		GameState.getInstance().getScreen().setFading(1);
		long startTime = TimeUtils.millis();
		while(TimeUtils.timeSinceMillis(startTime) < 500) {}

		for(int r = 0;r<board.length;r++) {
			if(r >= ranStartMinR && r <= ranStartMaxR) {
				board[r][0] = new Space(null,Space.State.WALL);
				if(r == ranStartGate) {
					board[r][0].setEntrance(true);
					Player.getPlayer().setPos(1,ranStartGate);
				}
				if((r == ranStartMinR && r == 0) || (r == ranStartMaxR && r == 16))
					board[r][1] = new Space(null,Space.State.WALL);
			}
			else
				board[r][0] = new Space(null,Space.State.CLEAR);
			
		}
		for(int r = 0;r<board.length;r++)
			for(int c = 1;c<board[r].length;c++) 
				if(r < ranStartMaxR && r > ranStartMinR && c > 0 && c < 6)
					board[r][c] = new Space(null,Space.State.FLOOR);
				else if(r == ranStartMaxR && r == ranStartMinR && c > 0 && c < 6)
					board[r][c] = new Space(null,Space.State.FLOOR);
				else if(board[r][c] ==  null)
					board[r][c] = new Space(null,Space.State.CLEAR);
		int randomSectionStart = 1;
		while(randomSectionStart % 5 != 0)
			randomSectionStart = (int)(Math.random() * (ranStartMaxR - ranStartMinR)) + ranStartMinR + 1;
		if(randomSectionStart > 17)
			randomSectionStart = 15;
		if(randomSectionStart <= 0)
			randomSectionStart = 5;
		addSection(0,board,randomSectionStart,5,1);
		addSection(0,board,addedR,addedC,getDirection(board));

		while(foundExit(board) == false) {
			addSection(0,board,addedR,addedC,getDirection(board));
		}
		fillWalls(board);
		return board;
	}
	
	private void fillWalls(Space[][] board) {
		for(int r = 0;r<board.length;r++) {
			for(int c = 0;c<board[r].length;c++) {
				if( (r == 0 && board[r+1][c].getStatus() == Space.State.FLOOR) ||
				  (r == 16 && board[r-1][c].getStatus() == Space.State.FLOOR) ||
				  (c == 0 && board[r][c+1].getStatus() == Space.State.FLOOR) ||
				  (c == 16 && board[r][c-1].getStatus() == Space.State.FLOOR) ||
				  ((r < 16 && board[r+1][c].getStatus() == Space.State.FLOOR)  && (r > 0 && board[r-1][c].getStatus() == Space.State.CLEAR)) ^ 
				  ((r > 0 && board[r-1][c].getStatus() == Space.State.FLOOR)  && (r < 16 &&board[r+1][c].getStatus() == Space.State.CLEAR)) ^
				  ((c < 16 && board[r][c+1].getStatus() == Space.State.FLOOR)  && (c > 0 && board[r][c-1].getStatus() == Space.State.CLEAR)) ^
				  ((c > 0 && board[r][c-1].getStatus() == Space.State.FLOOR)  && (c < 16 && board[r][c+1].getStatus() == Space.State.CLEAR)))
					if(board[r][c].getStatus() != Space.State.FLOOR && board[r][c].isExit() != true && board[r][c].isEntrance() != true)
					board[r][c] = new Space(null,Space.State.WALL);	
					
			}
		}
		for(int r = 0;r<board.length;r++) {
			for(int c = 0;c<board[r].length;c++) {
				if(board[r][c].getStatus() == Space.State.CLEAR && (((r > 0 && board[r-1][c].getStatus() == Space.State.WALL) || (r < 16 && board[r+1][c].getStatus() == Space.State.WALL))
				&& ((c > 0 && board[r][c-1].getStatus() == Space.State.WALL) || (c < 16 && board[r][c+1].getStatus() == Space.State.WALL))))
					if(!((r > 0 && c < 16 && board[r-1][c+1].getStatus() == Space.State.WALL) || 
						 (r > 0 && c > 0 && board[r-1][c-1].getStatus() == Space.State.WALL) ||
						 (r < 16 && c > 0 && board[r+1][c-1].getStatus() == Space.State.WALL) ||
						 (r < 16 && c > 16 && board[r+1][c+1].getStatus() == Space.State.WALL)))
					board[r][c] = new Space(null,Space.State.WALL);	
				
			}
		}
			
	}
	
	private boolean foundExit(Space[][] board) {
		int min = 0;
		int max = 0;
		int exitLoc = 0;
		for(int r = 0;r<board.length;r++) {
				if(board[r][15].getStatus() == Space.State.FLOOR) {		
					min = r;
					for(int rr = min+1;rr < board.length;rr++) {
						if(board[rr][15].getStatus() != Space.State.FLOOR) {
							max = rr-1;
							break;
						}
							
					}
					exitLoc = (int)(Math.random() * (max-min)) + min;
					System.out.println("Exit Loc: " + exitLoc);
					board[exitLoc][16] = new Space(null,Space.State.WALL);
					board[exitLoc][16].setExit(true);					
					return true;
				}
		}
		return false;
	}
	
	private int getDirection(Space[][] board) {
		if(addedR + 5 < board.length && board[addedR + 5][addedC].getStatus() == Space.State.CLEAR)
			DIRECTION[0] = true;
			else DIRECTION[0] = false;
		if(addedC + 5 < board.length && board[addedR][addedC + 5].getStatus() == Space.State.CLEAR)
			DIRECTION[1] = true;
			else DIRECTION[1] = false;
		if(addedR - 5 > 0 && board[addedR - 5][addedC].getStatus() == Space.State.CLEAR)
			DIRECTION[2] = true;
			else DIRECTION[2] = false;
		if(addedC - 5 > 0 && board[addedR][addedC - 5].getStatus() == Space.State.CLEAR)
			DIRECTION[3] = true;
			else DIRECTION[3] = false;
		
		int direction = 0;
		
		do{
			direction = (int)(Math.random() * 4);
		}
			while(!DIRECTION[direction]);
		
		System.out.println(direction);
		return direction;
	}
	
	// 0 = up, 1 = right, 2 = down, 3 = left
	private void addSection(int id,Space[][] board,int boardR, int boardC,int direction) {
		if(direction == 0) {
			boardR += 5;
			boardC -= 4;
		}
		else if(direction == 1) {
			boardC++;
		}
		else if(direction == 2) {
			boardR -= 5;
			boardC -= 4;
		}
		else if(direction == 3) {
			boardC -= 9;
		}
		addedR = boardR;
		Space[][] section = new Space[5][5];
		if(id == 0) {
		  Space[][] sectionType = new Space [5][5];
			section = sectionType;
		}
		for(int r = 0;r<section.length;r++) {
			for(int c = 0;c<section.length;c++) {
				if(section[r][c] == null)
					section[r][c] = new Space(null,Space.State.FLOOR);
				board[boardR][boardC] = section[r][c];
				boardC++;
			}
			addedC = boardC-1;
			boardC -= 5;
			boardR--;
		}
		
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
