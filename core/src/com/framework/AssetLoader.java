package com.framework;

import com.badlogic.gdx.assets.AssetManager;

public class AssetLoader{
	
	public static AssetLoader instance;
	private AssetManager assetManager;
	
	public AssetLoader() {
		assetManager = new AssetManager();
	}
	
	public static AssetLoader getInstance() {
		if(instance == null) 
			instance = new AssetLoader();
		return instance;
	}
	
	public AssetManager getManager() {
		return assetManager;
	}
	
	
}
