package com.framework;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.battlechess.BattleChess;
import com.entity.Player;

public class GameState {

	private static GameState instance;
	private Game battleChess;
	private DrawHandler currentScreen;
	private OrthographicCamera camera;
	private State s;
	
	public GameState(Game battleChess) {
		this.battleChess = battleChess;
		instance = this;
		camera = new OrthographicCamera(640,480);
		s = State.MENU;
	}
	
	public static GameState getInstance() {
		return instance;
	}
	
	public enum State {
		MENU, RUNNING
	}

	public void update() {
		camera.position.set(Player.getPlayer().getXCord(), Player.getPlayer().getYCord(), 0);
		camera.update();
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

	public boolean isMenu() {
		return s == State.MENU;
	}

	public void setRunning() {
		s = State.RUNNING;
	}
	
}
