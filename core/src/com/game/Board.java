
package com.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.framework.GameState;

public class Board {
	public static volatile boolean exit = false;
	public static boolean isFirstRoom;
	private Space[][] spaces;
	
	private GameState gameState;
	
	private SpriteBatch batch;
	
	
	public Board() {
		spaces = new Space[16][16];
		isFirstRoom = false;
		gameState = GameState.getInstance();
		//batch = gameState.getScreen().getSpriteBatch();
	}
	public Board(Space[][] room) {
		spaces = room;
		isFirstRoom = false;
	}
	
    public Space[][] getSpaces() {
    	return spaces;
    }
	
	public void render(float delta) {
		for(int r = 0; r < spaces.length; r++) {
			for(int c = 0; c < spaces[r].length; c++) {
				if(c%2 == r%2) {
					
				}
			}
		}
	}
	
	public void upate(float delta) {
		
	}
	
	
}

