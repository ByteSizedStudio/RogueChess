
package com.game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.TimeUtils;
import com.entity.*;
import com.entity.enemies.Enemy;
import com.framework.AssetLoader;

public class Space {

	//private static final int WHITE = 0, BLACK = 1, WALL = 2, GRAY = 3, GATE = 4, EXITGATE = 5, SIDEWALL = 6, LEFTWALL = 7, RIGHTWALL = 8, WALLTOP = 9, WALLBOTTOM = 10;
	private Sprite sprite;

	private long attackedTime;

	public enum State {
		CLEAR,
		FLOOR,
		WALL
	}

	public enum WallState {
		NULL,
		LEFT,
		RIGHT,
		TOP,
		BOTTOM;
		enum CORNER {
			TOP_LEFT,
			TOP_RIGHT,
			BOTTOM_LEFT,
			BOTTOM_RIGHT;
		}
	}
	
	private boolean entrance, exit, attacked;
	private Interactables entity;
	private State status;
	private WallState wallState;
	private int textureType;

	public Space(Interactables n, State s, int x, int y) {
		entity = n;
		status = s;
		switch (s) {
			case CLEAR: sprite = AssetLoader.getInstance().getAtlas().createSprite("StoneBrickWallEmpty"); break;
			case FLOOR:
				if(x % 2 == y % 2)
					sprite = AssetLoader.getInstance().getAtlas().createSprite("WhiteFloorTile");
				else
					sprite = AssetLoader.getInstance().getAtlas().createSprite("GrayFloorTile");
				break;
			 default: sprite = AssetLoader.getInstance().getAtlas().createSprite("StoneBrickWallBack"); break;
		}
		sprite.setPosition(x * 32, y * 32);
	}

	public Space(Interactables n, WallState wallState, int x, int y) {
		entity = n;
		this.wallState = wallState;
		status = State.WALL;
		switch (wallState) {
			case TOP: sprite = AssetLoader.getInstance().getAtlas().createSprite("StoneBrickWallBack"); break;
			case LEFT: sprite = AssetLoader.getInstance().getAtlas().createSprite("StoneWallEast"); break;
			case BOTTOM: sprite = AssetLoader.getInstance().getAtlas().createSprite("StoneBrickWallNorth"); break;
			case RIGHT: sprite = AssetLoader.getInstance().getAtlas().createSprite("StoneWallWest"); break;
		}
		sprite.setPosition(x * 32, y * 32);
	}

    public Space(Interactables n, WallState.CORNER wallState, int x, int y) {
        entity = n;
        this.wallState = WallState.NULL;
        status = State.WALL;
        switch (wallState) {
            case TOP_LEFT: sprite = AssetLoader.getInstance().getAtlas().createSprite("StoneBrickWallCornerNW"); break;
            case TOP_RIGHT: sprite = AssetLoader.getInstance().getAtlas().createSprite("StoneBrickWallCornerNE"); break;
            case BOTTOM_LEFT: sprite = AssetLoader.getInstance().getAtlas().createSprite("StoneBrickWallCornerSW"); break;
            case BOTTOM_RIGHT: sprite = AssetLoader.getInstance().getAtlas().createSprite("StoneBrickWallCornerSE"); break;
        }
        sprite.setPosition(x * 32, y * 32);
    }

	public void setSprite(Sprite sprite) {
		this.sprite = sprite;
	}

	public void render(SpriteBatch batch) {
	    sprite.draw(batch);
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

    public Sprite getSprite() {
        return sprite;
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

	public boolean isFloor() {
	    return status == State.FLOOR;
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

	public void setState(State s) {
		status = s;
	}

	public void setState(WallState wallState) {
		this.wallState = wallState;
		status = State.WALL;
	}

}


