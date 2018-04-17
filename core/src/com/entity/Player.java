package com.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.TimeUtils;
import com.framework.AssetLoader;
import com.framework.GameState;
import com.game.Board;

import com.entity.items.Item;

public class Player extends Interactables{

	private static final int attackLength = 5;

	private static Player player;
	private int health, chargeLevel, targetXPos, targetYPos, attackIndex, prevAttackIndex;
	private Item[] inventory;
	private boolean isMoving, isAttacking, attackingNorth, attackingSouth, attackingEast, attackingWest;
	
	
	private long inputDelay;

	private boolean spaceBar;
	
	public Player(int c, int r) {
		super(c,r);
		targetXPos = xPos;
		targetYPos = yPos;
		attackIndex = prevAttackIndex = 5;
		inventory = new Item[8];
		health = 3;
		chargeLevel = 0;
		isMoving = false;
		isAttacking = false;
		texture = AssetLoader.getInstance().getManager().get("player.png", Texture.class);
		inputDelay = 0;
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
		System.out.println("AttackIndex: " + attackIndex + " PrevAttackIndex: " + prevAttackIndex);
		checkInput();

		if(attackIndex >= 5)
			attackingNorth = attackingEast = attackingSouth = attackingWest = false;
		if(x > xPos * 32)
			x -= 2;
		if(x < xPos * 32)
			x += 2;
		if(y > yPos * 32)
			y -= 2;
		if(y < yPos * 32)
			y += 2;


		if(attackIndex < 5) {
			if(attackingNorth && y == yPos * 32) {
				targetYPos++;
				attackIndex++;
				if(Board.getBoard().getSpaces()[targetYPos][xPos].isWall()) {
					attackingNorth = false;
					attackingSouth = true;
					targetYPos--;
				}
			} else if(attackingSouth && y == yPos * 32) {
				targetYPos--;
				attackIndex++;
				if(Board.getBoard().getSpaces()[targetYPos][xPos].isWall()) {
					attackingSouth = false;
					attackingNorth = true;
					targetYPos++;
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
			} else if(attackingWest && x == xPos * 32) {
				targetXPos--;
				if(attackIndex == prevAttackIndex)
					attackIndex++;
				if(Board.getBoard().getSpaces()[yPos][targetXPos].isWall()) {
					attackingWest = false;
					attackingEast = true;
					targetXPos++;
				}
			}
			Board.getBoard().getSpaces()[yPos][xPos].setAttacked(true);
			if(yPos < targetYPos)
				yPos++;
			if(yPos > targetYPos)
				yPos--;
			if(xPos < targetXPos)
				xPos++;
			if(xPos > targetXPos)
				xPos--;
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
			if(attackIndex >= 5) {
				attackIndex = 5;
				prevAttackIndex = 5;
				isAttacking = false;
				targetXPos = xPos;
				targetYPos = yPos;
				System.out.println("Hey");
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

		//Delay is normally 900. small for testing
		if(TimeUtils.timeSinceMillis(inputDelay) > 500L && !spaceBar && !isAttacking) {

			if (Gdx.input.isKeyPressed(Input.Keys.W) && isValidMove(yPos + 1, xPos)) {
				System.out.println("is Valid Move");
				yPos++;
				inputDelay = TimeUtils.millis();
				chargeLevel++;
				//System.out.println("Y: " + yPos);
			}
			if (Gdx.input.isKeyPressed(Input.Keys.S) && isValidMove(yPos - 1, xPos)) {
				System.out.println("is Valid Move");
				yPos--;
				inputDelay = TimeUtils.millis();
				chargeLevel++;
				//System.out.println("Y: " + yPos);
			}
			if (Gdx.input.isKeyPressed(Input.Keys.D) && isValidMove(yPos, xPos + 1)) {
				System.out.println("is Valid Move");
				xPos++;
				inputDelay = TimeUtils.millis();
				chargeLevel++;
				//System.out.println("X: " + xPos);
			}
			if (Gdx.input.isKeyPressed(Input.Keys.A) && isValidMove(yPos, xPos - 1)) {
				System.out.println("is Valid Move");
				xPos--;
				inputDelay = TimeUtils.millis();
				chargeLevel++;
				//System.out.println("X: " + xPos);
			}
		}

		if(chargeLevel > prevLevel + 1)
			chargeLevel = prevLevel + 1;

		if(x == xPos * 32 && y == yPos * 32) {
			//System.out.println("Stable");
		    if(spaceBar && !isAttacking) {
		    	System.out.println("SpaceBar");
				spaceBar = false;
		    	if(chargeLevel >= 3)
		    		attack();
			}
        }


	}

	public void attack() {
		System.out.println("Attack");

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

		/*
		if (Gdx.input.isKeyPressed(Input.Keys.W) && isValidMove(yPos + 1, xPos)) {
			targetYPos = MathUtils.clamp(yPos + 5, yPos, 15);
			attackIndex = 0;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.S) && isValidMove(yPos - 1, xPos)) {
			targetYPos = MathUtils.clamp(yPos - 5, 0, yPos);
			attackIndex = 0;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.D) && isValidMove(yPos, xPos + 1)) {
			targetXPos = MathUtils.clamp(xPos + 5, xPos, 15);
			attackIndex = 0;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.A) && isValidMove(yPos, xPos - 1)) {
			targetXPos = MathUtils.clamp(xPos - 5, 0, xPos);
			attackIndex = 0;
		}

		if(attackIndex == 0) {
			chargeLevel = 0;
			isAttacking = true;
		}
		*/

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
