package com.entity;

import com.badlogic.gdx.graphics.g2d.Sprite;

public abstract class Interactables extends Sprite{
	protected int xLoc;
	protected int yLoc;
	
	public Interactables(int r, int c) {
		xLoc = r;
		yLoc = c;
	}
	
	
}
