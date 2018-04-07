
package com.game;
import com.entity.*;

public class Space {
	public static enum State {
		WALL,CLEAR,USED;

		}
	
	private boolean isEnterance;
	private Interactables entity;
	private State status;
	
	public Space(Interactables n,State s) {
		entity = n;
		status = s;
		
	}
	public State getStatus(){
		return status;
	}
	
	public boolean isFilled() {
		if(entity != null)
			return true;
		return false;
	}
	
}


