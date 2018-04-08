package com.framework;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.battlechess.BattleChess;

public class GameState {

	private static GameState instance;
	private Game battleChess;
	private DrawHandler currentScreen;
	private OrthographicCamera camera;
	//private SpriteBatch batch;
	
	public GameState(Game battleChess) {
		this.battleChess = battleChess;
		instance = this;
		camera = new OrthographicCamera(1140,640);
		//currentScreen = battleChess.getScreen();
	}
	
	public static GameState getInstance() {
		return instance;
	}
	
	public enum State {
		
	}
	
	public DrawHandler getScreen() {
		return currentScreen;
	}
	
	public void setScreen(DrawHandler screen) {
		battleChess.setScreen(screen);
		currentScreen = screen;
	}

	public SpriteBatch getBatch() {
		return currentScreen.getSpriteBatch();
	}

	public OrthographicCamera getCamera() {
	    return camera;
    }

    public void setCamera(OrthographicCamera camera) {
	    this.camera = camera;
    }


	
}
