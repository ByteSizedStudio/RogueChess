package com.entity.enemies;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.TimeUtils;
import com.entity.Player;
import com.framework.AssetLoader;

import java.sql.Time;

public class Rook extends Enemy{
	public Rook(int c, int r) {
		super(c,r);
		setPos(c,r);
		texture = AssetLoader.getInstance().getManager().get("evilRook.png", Texture.class);
	}

	public void update(float delta) {
		super.update(delta);

		if(Math.abs(xPos - Player.getPlayer().getxPos()) > Math.abs(yPos - Player.getPlayer().getyPos()) && TimeUtils.timeSinceMillis(moveTime) > 1000L) {

			if (xPos > Player.getPlayer().getxPos() && xPos * 32 == x) {
				xPos--;
				moveTime = TimeUtils.millis();
			}
			if (xPos < Player.getPlayer().getxPos() && xPos * 32 == x) {
				xPos++;
				moveTime = TimeUtils.millis();
			}
		} else if(TimeUtils.timeSinceMillis(moveTime) > 1000L){
			if (yPos > Player.getPlayer().getyPos() && yPos * 32 == y) {
				yPos--;
				moveTime = TimeUtils.millis();
			}
			if (yPos < Player.getPlayer().getyPos() && yPos * 32 == y) {
				yPos++;
				moveTime = TimeUtils.millis();
			}
		}
	}
	
	public void getDrops() {
		
	}
	
	public void attack() {
		
	}
}
