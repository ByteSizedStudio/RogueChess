package com.entity;

import com.badlogic.gdx.graphics.g2d.Sprite;

public abstract class Interactables extends Sprite{
	protected int xPos;
	protected int yPos;
	
	public Interactables(int r, int c) {
		xPos = r;
		yPos = c;
	}
	
	
	
}
