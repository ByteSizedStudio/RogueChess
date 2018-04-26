
package com.game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.TimeUtils;
import com.entity.*;
import com.framework.AssetLoader;

public class Space {

	private static final int WHITE = 0, BLACK = 1, WALL = 2, GRAY = 3, GATE = 4, EXITGATE = 5, SIDEWALL = 6, LEFTWALL = 7, RIGHTWALL = 8, WALLTOP = 9, WALLBOTTOM = 10;
	private Texture white, black, wall, gray, gate, exitgate, sideWall, leftWall, rightWall, wallTop, wallBottom;

	private long attackedTime;

	public static enum State {
		WALL,CLEAR,FLOOR;
		}
	
	private boolean entrance, exit, attacked;
	private Interactables entity;
	private State status;
	private int textureType;

	public Space(Interactables n,State s) {
		entity = n;
		status = s;
		white = AssetLoader.getInstance().getManager().get("whiteSpace.png", Texture.class);
		black = AssetLoader.getInstance().getManager().get("blackSpace.png", Texture.class);
		wall = AssetLoader.getInstance().getManager().get("StoneBrickWallBack.png", Texture.class);
		gray = AssetLoader.getInstance().getManager().get("emptySpace.png", Texture.class);
		gate = AssetLoader.getInstance().getManager().get("wallEntrance.png", Texture.class);
		exitgate = AssetLoader.getInstance().getManager().get("wallExit.png", Texture.class);
		sideWall = AssetLoader.getInstance().getManager().get("StoneBrickWallEW.png", Texture.class);
		leftWall = AssetLoader.getInstance().getManager().get("StoneWallEast.png", Texture.class);
		rightWall = AssetLoader.getInstance().getManager().get("StoneWallWest.png", Texture.class);
		wallTop = AssetLoader.getInstance().getManager().get("StoneBrickWallNorth.png", Texture.class);
		wallBottom = AssetLoader.getInstance().getManager().get("StoneBrickWallSouth.png", Texture.class);
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
		return status == State.WALL;
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
		if(TimeUtils.timeSinceMillis(attackedTime) > 400L) {
			attackedTime = 0;
			attacked = false;
		}
	}

	public Texture getTexture(int texture) {
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


