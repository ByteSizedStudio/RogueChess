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
		Player.getPlayer().setPos(1,ranStartGate);

		for(int r = 0;r<board.length;r++) {
			if(r >= ranStartMinR && r <= ranStartMaxR) {
				board[r][0] = new Space(null,Space.State.WALL);
				if(r == ranStartGate)
					board[r][0].setEntrance(true);
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
		addSection(0,board,randomSectionStart,5,0);
		
		
		
		
		return board;
	}
	
	public void addSection(int id,Space[][] board,int boardR, int boardC,int direction) {
		boardC++;
		Space[][] section = new Space[5][5];
		if(id == 0) {
		  Space[][] sectionType = 
			{{new Space(null,Space.State.FLOOR),new Space(null,Space.State.FLOOR),new Space(null,Space.State.FLOOR),new Space(null,Space.State.FLOOR),new Space(null,Space.State.FLOOR)},
		     {new Space(null,Space.State.FLOOR),new Space(null,Space.State.FLOOR),new Space(null,Space.State.FLOOR),new Space(null,Space.State.FLOOR),new Space(null,Space.State.FLOOR)},
			 {new Space(null,Space.State.FLOOR),new Space(null,Space.State.FLOOR),new Space(null,Space.State.FLOOR),new Space(null,Space.State.FLOOR),new Space(null,Space.State.FLOOR)},
			 {new Space(null,Space.State.FLOOR),new Space(null,Space.State.FLOOR),new Space(null,Space.State.FLOOR),new Space(null,Space.State.FLOOR),new Space(null,Space.State.FLOOR)},
			 {new Space(null,Space.State.FLOOR),new Space(null,Space.State.FLOOR),new Space(null,Space.State.FLOOR),new Space(null,Space.State.FLOOR),new Space(null,Space.State.FLOOR)}
			};
			System.out.println(sectionType.length);
			section = sectionType;
		}
		for(int r = 0;r<section.length;r++) {
			for(int c = 0;c<section.length;c++) {
				System.out.println(r + " " + c);
				board[boardR][boardC] = section[r][c];
				boardC++;
			}
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
