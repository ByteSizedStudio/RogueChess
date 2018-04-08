package com.battlechess;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.entity.CollisionHandler;
import com.entity.Player;
import com.framework.AssetLoader;
import com.framework.MenuScreen;
import com.game.RoomHandler;

public class BattleChess extends Game {
	RoomHandler room;
	CollisionHandler collide;
	SpriteBatch batch;
	Texture img;

	@Override
	public void create () {
		batch = new SpriteBatch();
		init();
	}
	
	public void init() {
		room = new RoomHandler();
		collide = new CollisionHandler();
	}

	@Override
	public void render () {
		if(AssetLoader.getInstance().getManager().update()) {
			this.setScreen(new MenuScreen(this));
		}
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		//batch.draw(img, 0, 0);
		batch.end();
		
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		AssetLoader.getInstance().getManager().dispose();
	}
}
