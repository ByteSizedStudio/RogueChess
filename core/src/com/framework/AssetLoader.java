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
		System.out.println("Hi");
		assetManager.load("whiteSpace.png", Texture.class);
		assetManager.load("blackSpace.png", Texture.class);
	}
	
	
}
