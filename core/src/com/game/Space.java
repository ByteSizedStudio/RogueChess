
package com.game;
import com.badlogic.gdx.graphics.Texture;
import com.entity.*;
import com.framework.AssetLoader;

public class Space {

	private final int WHITE = 0, BLACK = 1, BROWN = 2, GRAY = 3, GATE = 4, EXITGATE = 5;
	private Texture white, black, brown, gray, gate, exitgate;

	public static enum State {
		WALL,CLEAR,FLOOR;

		}
	
	private boolean entrance, exit;
	private Interactables entity;
	private State status;
	
	public Space(Interactables n,State s) {
		entity = n;
		status = s;
		white = AssetLoader.getInstance().getManager().get("whiteSpace.png", Texture.class);
		black = AssetLoader.getInstance().getManager().get("blackSpace.png", Texture.class);
		brown = AssetLoader.getInstance().getManager().get("wallSpace.png", Texture.class);
		gray = AssetLoader.getInstance().getManager().get("emptySpace.png", Texture.class);
		gate = AssetLoader.getInstance().getManager().get("wallEntrance.png", Texture.class);
		exitgate = AssetLoader.getInstance().getManager().get("wallExit.png", Texture.class);
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

	public Texture getTexture(int texture) {
		if(texture == WHITE)
			return white;
		if(texture == BROWN)
			return brown;
		if(texture == GRAY)
			return gray;
		if(texture == GATE)
			return gate;
		if(texture == EXITGATE)
			return exitgate;
		return black;
	}
	
}


