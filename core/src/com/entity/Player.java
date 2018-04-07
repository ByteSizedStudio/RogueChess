package com.entity;

import java.util.ArrayList;

public class Player extends Interactables{
	private int health;
	private Item[] inventory;
	
	public Player(int r, int c) {
		super(r,c);
		inventory = new Item[8];
		health = 3;
	}
	
	public Item[] getInventory() {	
		return inventory;
	}
	
	public int getHealth() {
		return health;
	}

	public void sethealth(int h) {
		health = h;
	}
	
	
	
}
