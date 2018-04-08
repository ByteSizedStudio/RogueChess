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
import com.framework.GameState;
import com.framework.MenuScreen;
import com.game.RoomHandler;

public class BattleChess extends Game {
	SpriteBatch batch;
	Texture img;
	private boolean set;

	@Override
	public void create () {
		batch = new SpriteBatch();
		while(!AssetLoader.getInstance().getManager().update()) {
			System.out.println(AssetLoader.getInstance().getManager().getProgress());
		}
		init();
	}
	
	public void init() {
		GameState gameState = new GameState(this);
		GameState.getInstance().setScreen(new MenuScreen(this));
		(new Thread(new RoomHandler())).start();
		(new Thread(new CollisionHandler())).start();
		set = false;
	}

	@Override
	public void render () {
		if(AssetLoader.getInstance().getManager().update() && !set) {
			set = true;
		}
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		if(set)
			GameState.getInstance().getScreen().render(Gdx.graphics.getDeltaTime());
		batch.begin();
		batch.end();
		
		
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		AssetLoader.getInstance().getManager().dispose();
	}
}
