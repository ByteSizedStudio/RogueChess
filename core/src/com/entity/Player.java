package com.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.TimeUtils;
import com.framework.AssetLoader;
import com.framework.GameState;
import com.game.Board;


import java.util.ArrayList;

import com.entity.items.Item;

public class Player extends Interactables{
	private static Player player;
	private int health;
	private Item[] inventory;
	private boolean isMoving;
	private SpriteBatch batch;
	private Texture texture;
	private long inputDelay;
	
	public Player(int r, int c) {
		super(r,c);

		inventory = new Item[8];
		health = 3;
		isMoving = false;
		//batch = GameState.getInstance().getBatch();
		texture = AssetLoader.getInstance().getManager().get("player.png", Texture.class);
		inputDelay = 0;
	}
	
	public static Player getPlayer() {
		if(player == null)
			player = new Player(0,0);
		return player;
	}
	public Item[] getInventory() {	
		return inventory;
	}
	
	public void setPos(int r, int c) {
		if(!Board.isFirstRoom)
			Board.getBoard().getSpaces()[xPos][yPos].setEntity(null);
		xPos = r;
		yPos = c;
		if(!Board.isFirstRoom)
			Board.getBoard().getSpaces()[xPos][yPos].setEntity(this);
		x = r * 32;
		y = c * 32;
	}
	
	public int getHealth() {
		return health;
	}

	public void sethealth(int h) {
		health = h;
	}
	
	public boolean isValidMove(int newR, int newC) {
		if(Board.getBoard().getSpaces()[newR][newC].isExit())
			Board.exit = true;
		return !Board.getBoard().getSpaces()[newR][newC].isWall();
	}

	public void render(SpriteBatch batch, float delta) {
		update(delta);
		batch.draw(texture, x, y);
	}

	public void update(float delta) {
		if(x > xPos * 32)
			x -= 2;
		if(x < xPos * 32)
			x += 2;
		if(y > yPos * 32)
			y -= 2;
		if(y < yPos * 32)
			y += 2;

		GameState.getInstance().getCamera().position.set(x,y,0);
		GameState.getInstance().getCamera().update();

		checkInput();
	}

	public void checkInput() {
		//Delay is normally 900. small for testing
		if(TimeUtils.timeSinceMillis(inputDelay) > 700L) {

			if (Gdx.input.isKeyPressed(Input.Keys.W) && isValidMove(yPos + 1, xPos)) {
				yPos++;
				inputDelay = TimeUtils.millis();
			}
			if (Gdx.input.isKeyPressed(Input.Keys.S) && isValidMove(yPos - 1, xPos)) {
				yPos--;
				inputDelay = TimeUtils.millis();
			}
			if (Gdx.input.isKeyPressed(Input.Keys.D) && isValidMove(yPos, xPos + 1)) {
				xPos++;
				inputDelay = TimeUtils.millis();
			}
			if (Gdx.input.isKeyPressed(Input.Keys.A) && isValidMove(yPos, xPos - 1)) {
				xPos--;
				inputDelay = TimeUtils.millis();
			}
		}

	}
	
}
