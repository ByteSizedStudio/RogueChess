package com.entity;

import com.badlogic.gdx.graphics.g2d.Sprite;

public abstract class Interactables extends Sprite{
	protected int xPos, yPos, x, y;
	
	public Interactables(int r, int c) {
		xPos = r;
		yPos = c;
		x = r * 32;
		y = r * 32;
	}
	
	
	
}
