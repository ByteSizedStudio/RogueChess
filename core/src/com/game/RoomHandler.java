package com.game;

import com.entity.enemies.*;
import com.entity.items.*;
import com.badlogic.gdx.utils.TimeUtils;
import com.battlechess.BattleChess;
import com.entity.Player;
import com.framework.AssetLoader;
import com.framework.GameState;

import java.sql.Time;

public class RoomHandler implements Runnable{
	private static int ranStartGate = 0;
	private static int roomCount = 0;
	//The R/C values of the newly added section
	private static int addedR = 0;
	private static int addedC = 0;
	//											up	  right down  left
	private static final boolean[] DIRECTION = {false,false,false,false};
	
	public RoomHandler() {
		Space[][] boardMaker = new Space[17][17];
		if(Board.isFirstRoom) {
		//if(false) {
			for(int r = 0;r<boardMaker.length;r++)
				for(int c = 0;c<boardMaker[r].length;c++) {
					if(r<2 || r > 13 || c < 2)
						boardMaker[r][c] = new Space(null, Space.State.CLEAR, c, r);
					else if(r == 2 || r == 13 || c == 2)
						boardMaker[r][c] = new Space(null, Space.WallState.LEFT, c, r);
					else if(r >= 2 && r <= 13 && c == 16) {
						boardMaker[r][c] = new Space(null, Space.WallState.RIGHT, c, r);
						if(r == 12 && c == 16) {
							boardMaker[r][c].setExit(true);
							boardMaker[r][c].setEntity(new Rook(15,r));
						}
					}
					else {
						if(r == 3 && c == 3) {
							Player.getPlayer().setPos(r,c);
							boardMaker[r][c] = new Space(Player.getPlayer(),Space.State.FLOOR, c, r);
						}else
							boardMaker[r][c] = new Space(null,Space.State.FLOOR, c, r);
					}
				}
			fillWalls(boardMaker);
			Board.getBoard().newBoard(boardMaker);
			Board.getBoard().getSpaces()[3][3].setEntity(Player.getPlayer());
		}else
			Board.getBoard().newBoard(genBoard(boardMaker));
			
	}
	
	public static Space[][] genBoard(Space[][] board) {
		roomCount++;
		int twistCount = 0;
		int fillCount = 0;
		int rewardCount = 0;
		int coverCount = 0;
		int ranStartMinR = 0;
		int ranStartMaxR = 0;
		ranStartMinR = (int)(Math.random() * 3) * 5;
		while((ranStartMaxR - ranStartMinR) % 5 != 1) {
		 
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
				board[r][0] = new Space(null,Space.State.WALL, 0, r);
				if(r == ranStartGate) {
					board[r][0].setEntrance(true);
					Player.getPlayer().setPos(1,ranStartGate);
				}
				if((r == ranStartMinR && r == 0) || (r == ranStartMaxR && r == 16))
					board[r][1] = new Space(null,Space.State.WALL, 1, r);
			}
			else
				board[r][0] = new Space(null,Space.State.CLEAR, 0, r);
			
		}
		for(int r = 0;r<board.length;r++)
			for(int c = 1;c<board[r].length;c++) 
				if(r < ranStartMaxR && r > ranStartMinR && c > 0 && c < 6)
					board[r][c] = new Space(null,Space.State.FLOOR, c, r);
				else if(r == ranStartMaxR && r == ranStartMinR && c > 0 && c < 6)
					board[r][c] = new Space(null,Space.State.FLOOR, c, r);
				else if(board[r][c] ==  null)
					board[r][c] = new Space(null,Space.State.CLEAR, c, r);
		int randomSectionStart = 1;
		while(randomSectionStart % 5 != 0)
			randomSectionStart = (int)(Math.random() * (ranStartMaxR - ranStartMinR)) + ranStartMinR + 1;
		if(randomSectionStart > 17)
			randomSectionStart = 15;
		if(randomSectionStart <= 0)
			randomSectionStart = 5;
		addSection(0,board,randomSectionStart,5,1);
		addSection(0,board,addedR,addedC,getDirection(board));

		while(!foundExit(board)) {
			addSection(0,board,addedR,addedC,getDirection(board));
		}
		fillWalls(board);
		fillWalls(board);
		safetyNet(board);
		for(int r = 0; r < board.length; r++) {
		    for(int c = 0; c < board[r].length; c++) {
		        if(board[r][c].getStatus() == Space.State.FLOOR) {
		        	if(r % 2 == c % 2)
		        		board[r][c].setSprite(AssetLoader.getInstance().getAtlas().createSprite("WhiteFloorTile"));
					else
						board[r][c].setSprite(AssetLoader.getInstance().getAtlas().createSprite("GrayFloorTile"));
				}
                board[r][c].getSprite().setPosition(c * 32, r * 32);
            }
        }
		return board;
	}
	
	private static void fillWalls(Space[][] board) {
		for(int r = 0;r<board.length;r++) {
			for(int c = 0;c<board[r].length;c++) {
				if(board[r][c].getStatus() != Space.State.FLOOR && !board[r][c].isExit() && !board[r][c].isEntrance()) {
					if ((r == 0 && board[r + 1][c].isFloor()))
					    board[r][c] = new Space(null, Space.WallState.BOTTOM, c, r);
					if (r == 16 && board[r - 1][c].isFloor())
					    board[r][c] = new Space(null, Space.WallState.TOP, c, r);
					if (c == 0 && (board[r][c + 1].isFloor() || board[r][c+1].isWall()))
					    board[r][c] = new Space(null, Space.WallState.LEFT, c, r);
					if (c == 16 && (board[r][c - 1].isFloor() || board[r][c-1].isWall()))
					    board[r][c] = new Space(null, Space.WallState.RIGHT, c, r);
					if ((r < 16 && board[r + 1][c].isFloor()) && (r > 0 && board[r - 1][c].isClear()))
					    board[r][c] = new Space(null, Space.WallState.BOTTOM, c, r);
					if (r > 0 && board[r - 1][c].isFloor())
					    board[r][c] = new Space(null, Space.WallState.TOP, c, r);
					if ((c < 16 && (board[r][c + 1].isFloor() || (r < 16 && board[r][c+1].isWall() && board[r+1][c].isWallSide()))) && (c > 0 && board[r][c - 1].isClear()))
					    board[r][c] = new Space(null, Space.WallState.LEFT, c, r);
					if (c > 0 && (board[r][c - 1].isFloor() || (r > 0 && board[r][c-1].isWall() && board[r-1][c].isWallSide())) && (c < 16 && board[r][c + 1].isClear()))
					    board[r][c] = new Space(null, Space.WallState.RIGHT, c, r);

				}
			}
		}
		for(int r = 0;r<board.length;r++) {
			for(int c = 0;c<board[r].length;c++) {
                if(!board[r][c].isFloor()) {
                    if (c < 16 && r < 16 && board[r][c + 1].isWall() && board[r + 1][c].isWall() && board[r+1][c+1].isFloor())
                        board[r][c] = new Space(null, Space.WallState.CORNER.BOTTOM_LEFT, c, r);
                    if (c > 0 && r < 16 && board[r][c - 1].isWall() && board[r+1][c].isWall() && board[r+1][c-1].isFloor())
                        board[r][c] = new Space(null, Space.WallState.CORNER.BOTTOM_RIGHT, c, r);
                    if(c < 16 && r < 16 && c > 0 && r > 0 && board[r][c-1].isWall() && board[r-1][c].isWall() && board[r+1][c+1].isFloor())
                    	board[r][c] = new Space(null, Space.WallState.CORNER.WALL_NORTH_EAST, c, r);
                    if(r < 16 && r > 0 && c > 0 && board[r][c-1].isFloor() && board[r-1][c].isWallTop() && board[r+1][c].isWallSide())
                        board[r][c] = new Space(null, Space.WallState.CORNER.WALL_SOUTH_WEST, c, r);

                }

			}
		}
			
	}
	
	private static boolean foundExit(Space[][] board) {
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
					board[exitLoc][16] = new Space(null,Space.State.WALL, 16, exitLoc);
					board[exitLoc][16].setExit(true);					
					return true;
				}
		}
		return false;
	}
	
	private static int getDirection(Space[][] board) {
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
	private static void addSection(int id, Space[][] board, int boardR, int boardC, int direction) {
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
				if(section[r][c] == null) {
					section[r][c] = new Space(null, Space.State.FLOOR, c * (boardC + 1), r * (boardR + 1));
				}
				board[boardR][boardC] = section[r][c];
				boardC++;
			}
			addedC = boardC-1;
			boardC -= 5;
			boardR--;
		}
		
	}
	
	private static void safetyNet(Space[][] board) {
		Board.exit = false;
		for(int r = 0;r<board.length;r++)
			for(int c = 0;c<board[r].length;c++) {
				if(board[r][c].isEntrance()) {
					Player.getPlayer().setPos(c+1, r);
				}
				if(board[r][c].isExit()) {
					board[r][c].setExit(true);
				}
						
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
