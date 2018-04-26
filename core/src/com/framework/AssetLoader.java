package com.framework;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;

public class AssetLoader{
	
	public static AssetLoader instance;
	private AssetManager assetManager;
	
	public AssetLoader() {
		assetManager = new AssetManager();
		loadAssets();
	}
	
	public static AssetLoader getInstance() {
		if(instance == null) 
			instance = new AssetLoader();
		return instance;
	}
	
	public AssetManager getManager() {
		return assetManager;
	}

	public void loadAssets() {
		assetManager.load("whiteSpace.png", Texture.class);
		assetManager.load("blackSpace.png", Texture.class);
		assetManager.load("StoneBrickWallBack.png", Texture.class);
		assetManager.load("StoneBrickWallEW.png", Texture.class);
		assetManager.load("emptySpace.png", Texture.class);
		assetManager.load("wallEntrance.png", Texture.class);
		assetManager.load("wallExit.png", Texture.class);
		assetManager.load("player.png", Texture.class);
		assetManager.load("evilRook.png", Texture.class);
		assetManager.load("StoneWallEast.png", Texture.class);
		assetManager.load("StoneWallWest.png", Texture.class);
		assetManager.load("StoneBrickWallNorth.png", Texture.class);
		assetManager.load("StoneBrickWallSouth.png", Texture.class);
	}
	
	
}
