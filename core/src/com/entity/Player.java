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
	private int health, chargeLevel;
	private Item[] inventory;
	private boolean isMoving, isAttacking;
	
	private long inputDelay;

	private boolean spaceBar;
	
	public Player(int c, int r) {
		super(c,r);

		inventory = new Item[8];
		health = 3;
		chargeLevel = 0;
		isMoving = false;
		isAttacking = false;
		texture = AssetLoader.getInstance().getManager().get("player.png", Texture.class);
		inputDelay = 0;
		spaceBar = false;
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

		if(isAttacking) {
			if(x > xPos * 32)
				x -= 6;
			if(x < xPos * 32)
				x += 6;
			if(y > yPos * 32)
				y -= 6;
			if(y < yPos * 32)
				y += 6;
			if(y == yPos * 32 && x == xPos * 32)
				isAttacking = false;
		}

		GameState.getInstance().getCamera().position.set(x,y,0);
		GameState.getInstance().getCamera().update();

		checkInput();
	}

	public void checkInput() {

		if(Gdx.input.isKeyPressed(Input.Keys.SPACE))
			spaceBar = true;


		//Delay is normally 900. small for testing
		if(TimeUtils.timeSinceMillis(inputDelay) > 600L && !spaceBar &&!isAttacking) {

			if (Gdx.input.isKeyPressed(Input.Keys.W) && isValidMove(yPos + 1, xPos)) {
				yPos++;
				inputDelay = TimeUtils.millis();
				chargeLevel++;
			}
			if (Gdx.input.isKeyPressed(Input.Keys.S) && isValidMove(yPos - 1, xPos)) {
				yPos--;
				inputDelay = TimeUtils.millis();
				chargeLevel++;
			}
			if (Gdx.input.isKeyPressed(Input.Keys.D) && isValidMove(yPos, xPos + 1)) {
				xPos++;
				inputDelay = TimeUtils.millis();
				chargeLevel++;
			}
			if (Gdx.input.isKeyPressed(Input.Keys.A) && isValidMove(yPos, xPos - 1)) {
				xPos--;
				inputDelay = TimeUtils.millis();
				chargeLevel++;
			}
		}



		if(x == xPos * 32 && y == yPos * 32) {
		    if(!Gdx.input.isKeyPressed(Input.Keys.SPACE) && !GameState.getInstance().isMenu() && spaceBar) {
				spaceBar = false;
		    	if(chargeLevel >= 10)
		    		attack();
			}
        }


	}

	public void attack() {
		chargeLevel = 0;
		isAttacking = true;


		if (Gdx.input.isKeyPressed(Input.Keys.W) && isValidMove(yPos + 1, xPos)) {
			yPos = MathUtils.clamp(yPos + 5, yPos, 15);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.S) && isValidMove(yPos - 1, xPos)) {
			yPos = MathUtils.clamp(yPos - 5, 0, yPos);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.D) && isValidMove(yPos, xPos + 1)) {
			xPos = MathUtils.clamp(xPos + 5, xPos, 15);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.A) && isValidMove(yPos, xPos - 1)) {
			xPos = MathUtils.clamp(xPos - 5, 0, xPos);
		}



		/*

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

        */

    }
	
}
