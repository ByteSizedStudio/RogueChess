package com.framework;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.battlechess.BattleChess;

public class MenuScreen extends DrawHandler {
	
	private Game battleChess;
	private SpriteBatch batch;
	
	public MenuScreen(Game battleChess) {
		this.battleChess = battleChess;
	}

	@Override
	public void show() {
		batch = new SpriteBatch();
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		//if(Gdx.input.isKeyPressed(Keys.SPACE)) {
			GameState.getInstance().setRunning();
			GameState.getInstance().setScreen(new GameScreen(battleChess));
			GameState.getInstance().getScreen().render(delta);
		//}
	}

	public void setFading(int duration) {}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public SpriteBatch getSpriteBatch() {
		return batch;
	}
}
