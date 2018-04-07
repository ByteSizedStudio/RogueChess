package com.game;
import com.entity.*;

public class Space {
	public enum State {
		WALL("wall"),
		CLEAR("unused"),
		USED("used");
		
		private final String id;
		
		State(String s){
			id = s;
		}
		
		public String getId() {
			return this.id;
		}
	}
	
	private Interactables entity;
	private State status;
	
	public Space(Interactables n,String s) {
		entity = n;
		for(State t:State.values()) 
			if(t.getId().equals(s))
				status = t;
		
	}
	public String getStatus(){
		return status.getId();
	}
	
	public boolean isFilled() {
		if(entity != null)
			return true;
		return false;
	}
	
}

