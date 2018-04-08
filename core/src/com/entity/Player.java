package com.entity;

import java.util.ArrayList;

public class Player extends Interactables{
	private static Player player;
	private int health;
	private Item[] inventory;
	
	public Player(int r, int c) {
		super(r,c);
		inventory = new Item[8];
		health = 3;
	}
	
	public static Player getPlayer() {
		if(player == null)
			player = new Player(0,0);
		return player;
	}
	public Item[] getInventory() {	
		return inventory;
	}
	
	public void setPos(int r, int c) {
		xPos = r;
		yPos = c;
	}
	
	public int getHealth() {
		return health;
	}

	public void sethealth(int h) {
		health = h;
	}
	
	public boolean isValidMove(int newR, int newC) {
		if()
	}
	
}
