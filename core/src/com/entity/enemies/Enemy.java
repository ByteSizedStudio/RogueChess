package com.entity.enemies;

import com.entity.Interactables;

public abstract class Enemy extends Interactables{

	protected long moveTime = 0;

	public Enemy(int c, int r) {
		super(c,r);
	}
	
	public Enemy getEnemy() {
		return this;
	}
	
	public abstract void getDrops();
	
	public abstract void attack();
}
