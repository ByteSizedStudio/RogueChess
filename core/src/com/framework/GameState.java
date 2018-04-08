package com.framework;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.battlechess.BattleChess;

public class GameState {

	private static GameState instance;
	private Game battleChess;
	private Screen currentScreen;
	
	public GameState(Game battleChess) {
		this.battleChess = battleChess;
		instance = this;
	}
	
	public static GameState getInstance() {
		return instance;
	}
	
	public enum State {
		
	}
	
	public Screen getScreen() {
		return currentScreen;
	}
	
	public void setScreen(Screen screen) {
		battleChess.setScreen(screen);
		currentScreen = screen;
	}
	
}
