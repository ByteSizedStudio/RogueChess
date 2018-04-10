package com.entity.enemies;

import com.badlogic.gdx.graphics.Texture;
import com.framework.AssetLoader;

public class Rook extends Enemy{
	public Rook(int r, int c) {
		super(r,c);
		setPos(r,c);
		texture = AssetLoader.getInstance().getManager().get("evilRook.png", Texture.class);
	}
	
	public void getDrops() {
		
	}
}
