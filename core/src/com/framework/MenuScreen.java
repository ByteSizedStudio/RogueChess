package com.framework;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.battlechess.BattleChess;

public class MenuScreen implements Screen {
	
	private Game battleChess;
	
	public MenuScreen(Game battleChess) {
		this.battleChess = battleChess;
	}

	@Override
	public void show() {
		System.out.println("hi");
		
	}

	@Override
	public void render(float delta) {
		System.out.println("Hey");
		if(Gdx.input.isKeyPressed(Keys.SPACE)) {
			GameState.getInstance().setScreen(new GameScreen(battleChess));
			GameState.getInstance().getScreen().render(delta);
			System.out.println("Space");
		}
	}

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

}
