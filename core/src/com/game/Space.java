
package com.game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.TimeUtils;
import com.entity.*;
import com.framework.AssetLoader;

public class Space {

	private static final int WHITE = 0, BLACK = 1, BROWN = 2, GRAY = 3, GATE = 4, EXITGATE = 5, SIDEWALL = 6;
	private Texture white, black, brown, gray, gate, exitgate, sideWall;

	private long attackedTime;

	public static enum State {
		WALL,CLEAR,FLOOR;

		}
	
	private boolean entrance, exit, attacked;
	private Interactables entity;
	private State status;
	
	public Space(Interactables n,State s) {
		entity = n;
		status = s;
		white = AssetLoader.getInstance().getManager().get("whiteSpace.png", Texture.class);
		black = AssetLoader.getInstance().getManager().get("blackSpace.png", Texture.class);
		brown = AssetLoader.getInstance().getManager().get("StoneBrickWallBack.png", Texture.class);
		gray = AssetLoader.getInstance().getManager().get("emptySpace.png", Texture.class);
		gate = AssetLoader.getInstance().getManager().get("wallEntrance.png", Texture.class);
		exitgate = AssetLoader.getInstance().getManager().get("wallExit.png", Texture.class);
		sideWall = AssetLoader.getInstance().getManager().get("StoneBrickWallEW.png", Texture.class);
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
		if(TimeUtils.timeSinceMillis(attackedTime) > 2000L) {
			attackedTime = 0;
			attacked = false;
		}
	}

	public Texture getTexture(int texture) {
		if(texture == WHITE)
			return white;
		if(texture == BROWN)
			return brown;
		if(texture == GRAY)
			return gray;
		if(texture == GATE)
			return gate;
		if(texture == EXITGATE) {
			exit = true;
			return exitgate;
		}
		if(texture == SIDEWALL)
			return sideWall;
		return black;
	}
	
}


