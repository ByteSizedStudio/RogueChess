
package com.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.framework.GameScreen;
import com.framework.GameState;

public class Board {

    private final int xBuffer = 0;

	private static Board board;
	public static volatile boolean exit = false;
	public static boolean isFirstRoom = true;
	private Space[][] spaces;
	private GameState gameState;
	
	public Board() {
		spaces = new Space[16][16];
		isFirstRoom = false;
		gameState = GameState.getInstance();
	}
	public Board(Space[][] room) {
		spaces = room;
		isFirstRoom = false;
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
	
	public void render(SpriteBatch batch, float delta) {
		for(int r = 0; r < spaces.length; r++) {
			for(int c = 0; c < spaces[r].length; c++) {
				if(spaces[r][c].getStatus() == Space.State.WALL)
					if(spaces[r][c].isEntrance())
						batch.draw(spaces[r][c].getTexture(4), c*32, r*32);
					else if(spaces[r][c].isExit())
						batch.draw(spaces[r][c].getTexture(5), c*32, r*32);
					else
						batch.draw(spaces[r][c].getTexture(2), c*32, r*32);
				else if(spaces[r][c].getStatus() == Space.State.CLEAR)
				batch.draw(spaces[r][c].getTexture(3), c*32, r*32);
				else if(r % 2 == c % 2)
					batch.draw(spaces[r][c].getTexture(0), c*32, r*32);
				else
					batch.draw(spaces[r][c].getTexture(1), c*32, r*32);


			}
		}
	}

	public void renderAttack(ShapeRenderer shapeRenderer) {
		for(int r = 0; r < spaces.length; r++) {
			for(int c = 0; c < spaces[r].length; c++) {
				if(spaces[r][c].isAttacked()) {
					shapeRenderer.setProjectionMatrix(GameState.getInstance().getCamera().combined);
					shapeRenderer.setColor(new Color(1, 0, 0, 0.5f));
					shapeRenderer.rect(c * 32, r * 32, 32, 32);
				}
			}
		}
	}
	
	public void update(float delta) {
		for(int r = 0; r < spaces.length; r++) {
			for(int c = 0; c < spaces[r].length; c++) {
				spaces[r][c].update(delta);
			}
		}
	}
	
	
}

