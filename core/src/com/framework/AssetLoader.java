package com.framework;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class AssetLoader{
	
	public static AssetLoader instance;
	private AssetManager assetManager;
	private TextureAtlas atlas;
	
	public AssetLoader() {
		assetManager = new AssetManager();
		loadAssets();
		atlas = new TextureAtlas(Gdx.files.internal("BattleChess.atlas"));
	}
	
	public static AssetLoader getInstance() {
		if(instance == null) 
			instance = new AssetLoader();
		return instance;
	}
	
	public AssetManager getManager() {
		return assetManager;
	}

	public TextureAtlas getAtlas() {
		return atlas;
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
