package com.entity.enemies;

import com.entity.Interactables;

public abstract class Enemy extends Interactables{
	public Enemy(int r, int c) {
		super(r,c);
	}
	
	public Enemy getEnemy() {
		return this;
	}
	
	public abstract void getDrops();
	
	
}
