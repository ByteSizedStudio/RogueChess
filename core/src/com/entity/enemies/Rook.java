package com.entity.enemies;

import com.badlogic.gdx.graphics.Texture;
import com.framework.AssetLoader;

public class Rook extends Enemy{
	public Rook(int c, int r) {
		super(c,r);
		setPos(c,r);
		texture = AssetLoader.getInstance().getManager().get("evilRook.png", Texture.class);
	}
	
	public void getDrops() {
		
	}
}
