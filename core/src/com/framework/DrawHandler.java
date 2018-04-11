package com.framework;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class DrawHandler implements Screen {
	public static boolean systemHalt = false;
	
    abstract public SpriteBatch getSpriteBatch();

    public void setFading(int duration) {
    	systemHalt = true;
    }

}
