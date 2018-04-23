package com.entity.enemies;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.entity.Interactables;
import com.game.Board;

public abstract class Enemy extends Interactables{

	protected long moveTime = 0;
	protected boolean alive;

	public Enemy(int c, int r) {
		super(c,r);
		alive = true;
	}
	
	public Enemy getEnemy() {
		return this;
	}
	
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
