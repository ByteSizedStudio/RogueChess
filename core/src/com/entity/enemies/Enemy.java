package com.entity.enemies;

import com.entity.Interactables;

public abstract class Enemy extends Interactables{
	public Enemy(int r, int c, int h) {
		super(r,c);
	}
	
	public abstract void getEnemy();
	
	
}
