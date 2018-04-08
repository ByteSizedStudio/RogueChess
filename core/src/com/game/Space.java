
package com.game;
import com.badlogic.gdx.graphics.Texture;
import com.entity.*;
import com.framework.AssetLoader;

public class Space {

	private final int WHITE = 0, BLACK = 1, BROWN = 2, GRAY = 3;
	private Texture white, black, brown, gray;

	public static enum State {
		WALL,CLEAR,USED;

		}
	
	private boolean isEntrance;
	private Interactables entity;
	private State status;
	
	public Space(Interactables n,State s) {
		entity = n;
		status = s;
		white = AssetLoader.getInstance().getManager().get("whiteSpace.png", Texture.class);
		black = AssetLoader.getInstance().getManager().get("blackSpace.png", Texture.class);
		brown = AssetLoader.getInstance().getManager().get("wallSpace.png", Texture.class);
		gray = AssetLoader.getInstance().getManager().get("emptySpace.png", Texture.class);
	}
	public State getStatus(){
		return status;
	}
	
	public boolean isFilled() {
		if(entity != null)
			return true;
		return false;
	}

	public Texture getTexture(int color) {
		if(color == WHITE)
			return white;
		if(color == BROWN)
			return brown;
		if(color == GRAY)
			return gray;
		return black;
	}
	
}


