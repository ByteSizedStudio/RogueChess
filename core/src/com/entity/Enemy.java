package com.entity;

public abstract class Enemy extends Interactables{
	protected int health;
	public Enemy(int r, int c, int h) {
		super(r,c);
		health = h;
	}
}
