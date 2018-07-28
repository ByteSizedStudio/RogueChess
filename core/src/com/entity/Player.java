package com.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.controllers.mappings.Xbox;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.TimeUtils;
import com.framework.AssetLoader;
import com.framework.GameState;
import com.game.Board;

import com.entity.items.Item;

public class Player extends Interactables{

	private static Player player;
	private int health, chargeLevel, targetXPos, targetYPos, attackIndex, prevAttackIndex;
	private Item[] inventory;
	private boolean isMoving, isAttacking, attackingNorth, attackingSouth, attackingEast, attackingWest;
	
	
	private long inputDelay, keyPressed;

	private boolean spaceBar;
	
	public Player(int c, int r) {
		super(c,r);
		targetXPos = xPos;
		targetYPos = yPos;
		attackIndex = prevAttackIndex = 3;
		inventory = new Item[8];
		health = 3;	
		chargeLevel = 0;
		isMoving = false;
		isAttacking = false;
		texture = AssetLoader.getInstance().getManager().get("player.png", Texture.class);
		inputDelay = keyPressed = 0;
		spaceBar = false;
		attackingNorth = attackingEast = attackingSouth = attackingWest = false;
	}
	
	public static Player getPlayer() {
		if(player == null)
			player = new Player(3,3);
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
		checkInput();

		if(attackIndex >= 3)
			attackingNorth = attackingEast = attackingSouth = attackingWest = false;
		if(x > xPos * 32)
			x -= 2;
		if(x < xPos * 32)
			x += 2;
		if(y > yPos * 32)
			y -= 2;
		if(y < yPos * 32)
			y += 2;


		if(attackIndex < 3) {
			prevAttackIndex = attackIndex;
			isAttacking = true;
			if(attackingNorth && y == yPos * 32) {
				targetYPos++;
				attackIndex++;
				if(Board.getBoard().getSpaces()[targetYPos][xPos].isWall()) {
					attackingNorth = false;
					attackingSouth = true;
					targetYPos--;
					attackIndex--;
				}
			} if(attackingSouth && y == yPos * 32) {
				targetYPos--;
				attackIndex++;
				if(Board.getBoard().getSpaces()[targetYPos][xPos].isWall()) {
					attackingSouth = false;
					attackingNorth = true;
					targetYPos+=2;
				}
			}
			if(attackingEast && x == xPos * 32) {
				targetXPos++;
				if(attackIndex == prevAttackIndex)
					attackIndex++;
				if(Board.getBoard().getSpaces()[yPos][targetXPos].isWall()) {
					attackingEast = false;
					attackingWest = true;
					targetXPos--;
				}
			} if(attackingWest && x == xPos * 32) {
				targetXPos--;
				if(attackIndex == prevAttackIndex)
					attackIndex++;
				if(Board.getBoard().getSpaces()[yPos][targetXPos].isWall()) {
					attackingWest = false;
					attackingEast = true;
					targetXPos+=2;
				}
			}
			//System.out.println("AttackIndex: " + attackIndex + " Target X: " + targetXPos + " Target Y: " + targetYPos);
			//System.out.println("AttackingNorth: " + attackingNorth + " AttackingEast: " + attackingEast + " AttackingSouth: " + attackingSouth + " AttackingWest: " + attackingWest);
			Board.getBoard().getSpaces()[targetYPos][targetXPos].setAttacked(true);
			inputDelay = TimeUtils.millis();
			yPos = targetYPos;
			xPos = targetXPos;
			if(attackIndex > prevAttackIndex)
				prevAttackIndex = attackIndex;
			if(x > xPos * 32)
				x -= 2;
			if(x < xPos * 32)
				x += 2;
			if(y > yPos * 32)
				y -= 2;
			if(y < yPos * 32)
				y += 2;
			if(attackIndex >= 3) {
				attackIndex = 3;
				prevAttackIndex = 3;
				isAttacking = false;
				targetXPos = xPos;
				targetYPos = yPos;
			}
		} else {
			targetXPos = xPos;
			targetYPos = yPos;
		}

		GameState.getInstance().getCamera().position.set(x,y,0);
		GameState.getInstance().getCamera().update();
	}

	public void checkInput() {
		targetXPos = xPos;
		targetYPos = yPos;

		int prevLevel = chargeLevel;

		spaceBar = Gdx.input.isKeyPressed(Input.Keys.SPACE);

		if(Gdx.input.isKeyJustPressed(Input.Keys.ANY_KEY))
			keyPressed = TimeUtils.millis();

		//Delay is normally 800. small for testing
		if(TimeUtils.timeSinceMillis(keyPressed) > 100L && TimeUtils.timeSinceMillis(inputDelay) > 800L && !spaceBar && !isAttacking) {

			if (Gdx.input.isKeyPressed(Input.Keys.W) && isValidMove(yPos + 1, xPos) && y == yPos * 32) {
				yPos++;
				if (TimeUtils.timeSinceMillis(inputDelay) > 250L)
					inputDelay = TimeUtils.millis();
				chargeLevel++;
			}
			if (Gdx.input.isKeyPressed(Input.Keys.S) && isValidMove(yPos - 1, xPos) && y == yPos * 32) {
				yPos--;
				if (TimeUtils.timeSinceMillis(inputDelay) > 250L)
					inputDelay = TimeUtils.millis();
				chargeLevel++;
			}
			if (Gdx.input.isKeyPressed(Input.Keys.D) && isValidMove(yPos, xPos + 1) && x == xPos * 32) {
				xPos++;
				if (TimeUtils.timeSinceMillis(inputDelay) > 250L)
					inputDelay = TimeUtils.millis();
				chargeLevel++;
			}
			if (Gdx.input.isKeyPressed(Input.Keys.A) && isValidMove(yPos, xPos - 1) && x == xPos * 32) {
				xPos--;
				if (TimeUtils.timeSinceMillis(inputDelay) > 250L)
					inputDelay = TimeUtils.millis();
				chargeLevel++;
			}
		}

		if(chargeLevel > prevLevel + 1)
			chargeLevel = prevLevel + 1;

		if(x == xPos * 32 && y == yPos * 32 && spaceBar && !isAttacking) {
			spaceBar = false;
			if(chargeLevel >= 3)
				attack();
        }


	}

	public void attack(){

		if (Gdx.input.isKeyPressed(Input.Keys.W)) {
			attackingNorth = true;
			attackingSouth = false;
			attackIndex = prevAttackIndex = 0;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.S)) {
			attackingSouth = true;
			attackingNorth = false;
			attackIndex = prevAttackIndex = 0;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.D)) {
			attackingEast = true;
			attackingWest = false;
			attackIndex = prevAttackIndex = 0;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.A)) {
			attackingWest = true;
			attackingEast = false;
			attackIndex = prevAttackIndex = 0;
		}

		if(attackIndex == 0) {
			chargeLevel = 0;
			isAttacking = true;
		}
    }
	
}
