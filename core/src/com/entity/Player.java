package com.entity;

import java.util.ArrayList;

import com.entity.items.Item;

public class Player extends Interactables{
	private static Player player;
	private int health;
	private Item[] inventory;
	private boolean isMoving;
	
	public Player(int r, int c) {
		super(r,c);
		inventory = new Item[8];
		health = 3;
		isMoving = false;
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
		return true;
	}

	public void render(float delta) {

	}

	public void update(float delta) {

	}
	
}
