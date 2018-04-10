package com.framework;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.entity.Player;
import com.game.Board;

public class GameScreen extends DrawHandler {

	private Game battleChess;
	private SpriteBatch batch;
	private FillViewport viewport;
	private ShapeRenderer shapeRenderer;

	private boolean isFading;
	private float screenAlpha;
	private long fadeTime;
	private int fadeDuration;
	
	public GameScreen(Game battleChess) {
		this.battleChess = battleChess;
		batch = new SpriteBatch();
		shapeRenderer = new ShapeRenderer();
		GameState.getInstance().getCamera().position.set(
		        Player.getPlayer().getXCord(),
                Player.getPlayer().getYCord(),
                0
        );
		viewport = new FillViewport(640, 360, GameState.getInstance().getCamera());
		viewport.apply();
		Gdx.input.setInputProcessor(InputManager.getInstance());

		isFading = false;
		screenAlpha = 0f;
		fadeTime = 0L;
		fadeDuration = 0;
	}
	
	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(float delta) {

		//Update all attacks
		update(delta);


		//Gdx.gl.glClearColor(8/255f, 0, 38/255f, 1); Black looks better right now sorry
		Gdx.gl.glClearColor(0/255f, 0, 0/255f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


		GameState.getInstance().update();
		batch.setProjectionMatrix(GameState.getInstance().getCamera().combined);


		batch.begin(); //Begin Drawing to Screen
		Board.getBoard().render(batch, delta);
        Player.getPlayer().render(batch, delta);
		batch.end(); //End Drawing to Screen

		Gdx.gl.glEnable(GL20.GL_BLEND); //Enable Translucent Shapes
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

		shapeRenderer.begin(ShapeRenderer.ShapeType.Filled); //Begin Drawing the Shapes
		Board.getBoard().renderAttack(shapeRenderer);
		if(isFading) {
			fade();
			shapeRenderer.setColor(new Color(0, 0, 0, screenAlpha));
			shapeRenderer.rect(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		}
		shapeRenderer.end(); //End Drawing Shapes
		Gdx.gl.glDisable(GL20.GL_BLEND); //Disable Translucent Shapes
	}

	public void update(float delta) {
		Board.getBoard().update(delta);
	}

	@Override
	public void resize(int width, int height) {
        viewport.update(width, height, true);
	}

	public void setFading(int duration) {
		fadeDuration = duration;
		isFading = true;
		fadeTime = TimeUtils.millis();
	}

	public void fade() {
		if (TimeUtils.timeSinceMillis(fadeTime) < (fadeDuration / 2f) * 1000)
			screenAlpha += .05f;
		if (TimeUtils.timeSinceMillis(fadeTime) > (fadeDuration/2f) * 1000)
			screenAlpha -= .05f;
		if (TimeUtils.timeSinceMillis(fadeTime) > fadeDuration * 1000) {
			isFading = false;
			screenAlpha = 0f;
		}
		if(screenAlpha > 1f)
			screenAlpha = 1f;
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
