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
		//System.out.println("Hi");
		assetManager.load("whiteSpace.png", Texture.class);
		assetManager.load("blackSpace.png", Texture.class);
		assetManager.load("wallSpace.png", Texture.class);
		assetManager.load("wallSpaceSide.png", Texture.class);
		assetManager.load("emptySpace.png", Texture.class);
		assetManager.load("wallEntrance.png", Texture.class);
		assetManager.load("wallExit.png", Texture.class);
		assetManager.load("player.png", Texture.class);
		assetManager.load("evilRook.png", Texture.class);
	}
	
	
}
