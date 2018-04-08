package com.framework;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.entity.Player;
import com.game.Board;

public class GameScreen extends DrawHandler {

	private Game battleChess;
	private SpriteBatch batch;
	private ScreenViewport viewport;
	
	public GameScreen(Game battleChess) {
		this.battleChess = battleChess;
		batch = new SpriteBatch();
		GameState.getInstance().getCamera().position.set(
		        GameState.getInstance().getCamera().viewportWidth / 2,
                GameState.getInstance().getCamera().viewportHeight/2,
                0
        );
		viewport = new ScreenViewport(GameState.getInstance().getCamera());
		viewport.apply();
		Gdx.input.setInputProcessor(InputManager.getInstance());
	}
	
	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(8/255f, 0, 38/255f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        GameState.getInstance().getCamera().update();


		batch.begin();
		Board.getBoard().render(batch, delta);
        Player.getPlayer().render(batch, delta);
		batch.end();

		//InputManager.getInstance().touchDown();
	}

	@Override
	public void resize(int width, int height) {
        //GameState.getInstance().getCamera().position.set(
        //        GameState.getInstance().getCamera().viewportWidth / 2,
        //        GameState.getInstance().getCamera().viewportHeight/2,
        //        0
        //);
        viewport.update(width, height, true);
		batch.setProjectionMatrix(GameState.getInstance().getCamera().combined);
		
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
