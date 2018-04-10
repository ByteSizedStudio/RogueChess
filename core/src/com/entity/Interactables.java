package com.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.framework.GameState;
import com.game.Board;

//WTF THE X AND Y ON EVERYTHING IS BACKWARDS!!!! TIME TO FIX. DAMMIT MICHAEL
public abstract class Interactables extends Sprite{
	protected int xPos, yPos, x, y;
	protected Texture texture;
	protected SpriteBatch batch;
	
	public Interactables(int r, int c) {
		xPos = r;
		yPos = c;
		x = r * 32;
		y = c * 32;
	}
	
	public void setPos(int r, int c) {
		if(!Board.isFirstRoom)
			Board.getBoard().getSpaces()[xPos][yPos].setEntity(null);
		xPos = r;
		yPos = c;
		if(!Board.isFirstRoom)
			Board.getBoard().getSpaces()[xPos][yPos].setEntity(this);
		x = r * 32;
		y = c * 32;
	}

	public void render(SpriteBatch batch, float delta) {
		update(delta);
		batch.draw(texture, x, y);
	}
	
	public void update(float delta) {
		if(x > xPos * 32)
			x -= 2;
		if(x < xPos * 32)
			x += 2;
		if(y > yPos * 32)
			y -= 2;
		if(y < yPos * 32)
			y += 2;

		GameState.getInstance().getCamera().position.set(x,y,0);
		GameState.getInstance().getCamera().update();
	}
	
	public int getXCord() {
		return x;
	}

	public int getYCord() {
		return y;
	}

	
	
	
}
