package com.entity.enemies;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.TimeUtils;
import com.entity.Player;
import com.framework.AssetLoader;
import com.game.Board;

import java.sql.Time;


public class Rook extends Enemy{

	private int target;
	
	public Rook(int c, int r) {
		super(c,r);
		setPos(c,r);
		target = 0;
		texture = AssetLoader.getInstance().getManager().get("evilRook.png", Texture.class);
	}

	public void update(float delta) {
		super.update(delta);
		if(isAttacking == false && xPos == Player.getPlayer().getxPos())
		    attack();
			
		else if(isAttacking == false && (Math.abs(xPos - Player.getPlayer().getxPos()) >= Math.abs(yPos - Player.getPlayer().getyPos()) && TimeUtils.timeSinceMillis(moveTime) > 800L)) {

			if (xPos > Player.getPlayer().getxPos() && xPos * 32 == x) {
				xPos--;
				moveTime = TimeUtils.millis();
			}
			if (xPos < Player.getPlayer().getxPos() && xPos * 32 == x) {
				xPos++;
				moveTime = TimeUtils.millis();
			}
		} else if(isAttacking == false && TimeUtils.timeSinceMillis(moveTime) > 800L){
			if (yPos > Player.getPlayer().getyPos() && yPos * 32 == y) {
				yPos--;
				moveTime = TimeUtils.millis();
			}
			if (yPos < Player.getPlayer().getyPos() && yPos * 32 == y) {
				yPos++;
				moveTime = TimeUtils.millis();
			}
		} else if (isAttacking) {
			if(TimeUtils.timeSinceMillis(moveTime) % 10 < 5) {
				if(x > xPos * 32)
					x-= 3;
				else if(x <= xPos * 32)
					x += 3;
			  }
		 if(TimeUtils.timeSinceMillis(moveTime) > 1500L){
			 x = xPos * 32;
			 y = yPos * 32;
			 isAttacking = false;
		 }
		}
	}
	
	public void getDrops() {
		
	}
	
	public void attack() {
			 moveTime = TimeUtils.millis();
			 if(x == xPos * 32 && y == yPos * 32)
				 isAttacking = true;
	}
}
