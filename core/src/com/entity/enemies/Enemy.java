package com.entity.enemies;

import java.util.HashMap;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.entity.Interactables;
import com.game.Board;

public abstract class Enemy extends Interactables{

	protected long moveTime = 0;
	protected boolean alive;
	protected boolean isAttacking;
	public static HashMap<Integer,Enemy> enemyData;
	
	public Enemy(int c, int r) {
		super(c,r);
		alive = true;
		isAttacking = false;
	}
	
	public static void init() {
		enemyData = new HashMap<Integer,Enemy>();
	}
	
	public static Enemy spawn(int id,int c,int r) {
		int mapPos = 0;
		while(enemyData.containsKey(mapPos)) {
			System.out.println(mapPos);
			mapPos++;
		}
		enemyData.put(mapPos, IDtoEnemy(id,c,r));
		Enemy spawnedEnemy = enemyData.get(mapPos);
		return spawnedEnemy;
		
	}
	
	private static Enemy IDtoEnemy(int id,int c,int r) {
		switch(id) {
		case 1:
			return new Rook(c,r);
		}
		System.out.println("Unknown ID or other spawn error");
		return null;
	}
	
	public Enemy getEnemy() {
		return this;
	}
	
	public void setAttacking(boolean n) {
		isAttacking = n;
	}
	
	public boolean attacking() {
		return isAttacking;
	}
	
	public long getMoveTime() {
		return moveTime;
	}
	
	public abstract int getId();
	
	public abstract void getDrops();
	
	public abstract void attack();

	public void update(float delta) {
		if(!alive)
			return;

		super.update(delta);
		if(Board.getBoard().getSpaces()[yPos][xPos].isAttacked()) {
			alive = false;
		}

	}


	@Override
	public void render(SpriteBatch batch, float delta) {
		if(alive) {
			super.render(batch, delta);
		}
	}

}
