
package com.game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.TimeUtils;
import com.entity.*;
import com.entity.enemies.Enemy;
import com.framework.AssetLoader;

public class Space {

	private static final int WHITE = 0, BLACK = 1, WALL = 2, GRAY = 3, GATE = 4, EXITGATE = 5, SIDEWALL = 6, LEFTWALL = 7, RIGHTWALL = 8, WALLTOP = 9, WALLBOTTOM = 10;
	private Sprite white, black, wall, gray, gate, exitgate, sideWall, leftWall, rightWall, wallTop, wallBottom;

	private long attackedTime;

	public enum State {
		CLEAR,
		FLOOR,
		WALL
	}
	
	private boolean entrance, exit, attacked;
	private Interactables entity;
	private State status;
	private int textureType;

	public Space(Interactables n,State s) {
		entity = n;
		status = s;
		white = AssetLoader.getInstance().getAtlas().createSprite("whiteSpace.png");
		black = AssetLoader.getInstance().getAtlas().createSprite("blackSpace.png");
		wall = AssetLoader.getInstance().getAtlas().createSprite("StoneBrickWallBack.png");
		gray = AssetLoader.getInstance().getAtlas().createSprite("emptySpace.png");
		gate = AssetLoader.getInstance().getAtlas().createSprite("wallEntrance.png");
		exitgate = AssetLoader.getInstance().getAtlas().createSprite("wallExit.png");
		sideWall = AssetLoader.getInstance().getAtlas().createSprite("StoneBrickWallEW.png");
		leftWall = AssetLoader.getInstance().getAtlas().createSprite("StoneWallEast.png");
		rightWall = AssetLoader.getInstance().getAtlas().createSprite("StoneWallWest.png");
		wallTop = AssetLoader.getInstance().getAtlas().createSprite("StoneBrickWallNorth.png");
		wallBottom = AssetLoader.getInstance().getAtlas().createSprite("StoneBrickWallSouth.png");
	}

	public void setEntity(Interactables e) {
		entity = e;
	}
	
	public Interactables getEntity() {
		return entity;
	}
	
	public State getStatus(){
		return status;
	}
	
	public void setEntrance(boolean t) {
		entrance = t;
	}
	
	public boolean isEntrance() {
		return entrance;
	}
	
	public void setExit(boolean t) {
		exit = t;
	}
	
	public boolean isExit() {
		return exit;
	}

	public boolean isWall() {
		return status != State.CLEAR && status != State.FLOOR;
	}

	public boolean isClear() {
		return status == State.CLEAR;
	}

	public boolean isFilled() {
		if(entity != null)
			return true;
		return false;
	}

	public boolean isAttacked() {
		return attacked;
	}

	public void setAttacked(boolean attacked) {
		this.attacked = attacked;
		attackedTime = TimeUtils.millis();
	}

	public void update(float delta) {
		if(TimeUtils.timeSinceMillis(attackedTime) > 100L) {
			attackedTime = 0;
			attacked = false;
		}
		
	}

	public Sprite getTexture(int texture) {
		switch (texture) {
			case WHITE: return white;
			case WALL: return wall;
			case GRAY: return gray;
			case GATE: return gate;
			case EXITGATE: exit = true; return exitgate;
			case SIDEWALL: return sideWall;
			case LEFTWALL: return leftWall;
			case RIGHTWALL: return rightWall;
			case WALLTOP: return wallTop;
			case WALLBOTTOM: return wallBottom;
			default: return black;
		}
	}
	
}


