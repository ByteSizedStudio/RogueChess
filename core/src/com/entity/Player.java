package com.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
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
	
	private long inputDelay;
	
	public Player(int c, int r) {
		super(c,r);

		inventory = new Item[8];
		health = 3;
		isMoving = false;
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
	
	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}
	
	public boolean isAlive() {
		if(health > 0)
			return true;
		return false;
	}
	
	public boolean isValidMove(int newR, int newC) {
		if(Board.getBoard().getSpaces()[newR][newC].isExit())
			Board.exit = true;
		return !Board.getBoard().getSpaces()[newR][newC].isWall();
	}
	
	@Override
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
		if(TimeUtils.timeSinceMillis(inputDelay) > 300L) {

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

		if(x == xPos * 32 && y == yPos * 32) {
		    if(Gdx.input.isKeyPressed(Input.Keys.SPACE) && !GameState.getInstance().isMenu())
		        attack();
        }

	}

	public void attack() {
	    //Attack Up + Down
	    Board.getBoard().getSpaces()[MathUtils.clamp(yPos + 1, yPos, 15)][xPos].setAttacked(true);
        Board.getBoard().getSpaces()[MathUtils.clamp(yPos - 1, 0, yPos)][xPos].setAttacked(true);
        //Attack Diagonally
        Board.getBoard().getSpaces()[MathUtils.clamp(yPos + 1, yPos, 15)][MathUtils.clamp(xPos + 1, xPos, 15)].setAttacked(true);
        Board.getBoard().getSpaces()[MathUtils.clamp(yPos + 1, yPos, 15)][MathUtils.clamp(xPos - 1, 0, xPos)].setAttacked(true);
        Board.getBoard().getSpaces()[MathUtils.clamp(yPos - 1, 0, yPos)][MathUtils.clamp(xPos + 1, xPos, 15)].setAttacked(true);
        Board.getBoard().getSpaces()[MathUtils.clamp(yPos - 1, 0, yPos)][MathUtils.clamp(xPos - 1, 0, xPos)].setAttacked(true);
        //Attack Left + Right
        Board.getBoard().getSpaces()[yPos][MathUtils.clamp(xPos + 1, xPos, 15)].setAttacked(true);
        Board.getBoard().getSpaces()[yPos][MathUtils.clamp(xPos - 1, 0, xPos)].setAttacked(true);

        if(Board.getBoard().getSpaces()[yPos][xPos].isAttacked())
            Board.getBoard().getSpaces()[yPos][xPos].setAttacked(false);

    }
	
}
