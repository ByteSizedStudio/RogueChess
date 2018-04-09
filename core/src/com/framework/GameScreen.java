package com.framework;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.entity.Player;
import com.game.Board;

public class GameScreen extends DrawHandler {

	private Game battleChess;
	private SpriteBatch batch;
	private FillViewport viewport;
	
	public GameScreen(Game battleChess) {
		this.battleChess = battleChess;
		batch = new SpriteBatch();
		GameState.getInstance().getCamera().position.set(
		        Player.getPlayer().getXCord(),
                Player.getPlayer().getYCord(),
                0
        );
		viewport = new FillViewport(640, 360, GameState.getInstance().getCamera());
		viewport.apply();
		Gdx.input.setInputProcessor(InputManager.getInstance());
	}
	
	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(float delta) {
		//Gdx.gl.glClearColor(8/255f, 0, 38/255f, 1); Black looks better right now sorry
		Gdx.gl.glClearColor(0/255f, 0, 0/255f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		GameState.getInstance().update();
		batch.setProjectionMatrix(GameState.getInstance().getCamera().combined);

		batch.begin();
		Board.getBoard().render(batch, delta);
        Player.getPlayer().render(batch, delta);
		batch.end();

		//InputManager.getInstance().touchDown();
	}

	@Override
	public void resize(int width, int height) {
        viewport.update(width, height, true);
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

	public SpriteBatch getSpriteBatch() {
		return batch;
	}

}
