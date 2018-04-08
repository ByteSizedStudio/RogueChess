
package com.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.framework.GameScreen;
import com.framework.GameState;

public class Board {
	private static Board board;
	public static volatile boolean exit = false;
	public static boolean isFirstRoom = true;
	private Space[][] spaces;
	private GameState gameState;
	private SpriteBatch batch;
	
	public Board() {
		spaces = new Space[16][16];
		isFirstRoom = false;
		gameState = GameState.getInstance();
		batch = GameState.getInstance().getScreen().getSpriteBatch();
	}
	public Board(Space[][] room) {
		spaces = room;
		isFirstRoom = false;
		batch = GameState.getInstance().getScreen().getSpriteBatch();
	}
	
	public static Board getBoard() {
		if(Board.board == null)
			board = new Board();
		return board;
	}
	
	public void newBoard(Space[][] b) {
		board = new Board(b);
	}
	
    public Space[][] getSpaces() {
    	return spaces;
    }
	
	public void render(float delta) {
		//if(batch == null)
			batch = GameState.getInstance().getScreen().getSpriteBatch();
		for(int r = 0; r < spaces.length; r++) {
			for(int c = 0; c < spaces[r].length; c++) {
				if(spaces[r][c].getStatus() == Space.State.WALL)
					if(spaces[r][c].isEntrance() == true)
						batch.draw(spaces[r][c].getTexture(4), c*32, r*32);
					else
						batch.draw(spaces[r][c].getTexture(2), c*32, r*32);
				else if(spaces[r][c].getStatus() == Space.State.CLEAR)
				batch.draw(spaces[r][c].getTexture(3), c*32, r*32);
				else
					batch.draw(spaces[r][c].getTexture(0), c*32, r*32);


			}
		}
	}
	
	public void update(float delta) {
		
	}
	
	
}

