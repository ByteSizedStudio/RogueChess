
package com.game;

public class Board {
	public static volatile boolean exit = false;
	public static boolean isFirstRoom;
	private Space[][] spaces;
	
	
	public Board() {
		spaces = new Space[16][16];
		isFirstRoom = false;
		
	}
	public Board(Space[][] room) {
		spaces = room;
		isFirstRoom = false;
	}
	
    public Space[][] getSpaces() {
    	return spaces;
    }
	
	public void render(float delta) {
		
	}
	
	public void upate(float delta) {
		
	}
	
	
}

