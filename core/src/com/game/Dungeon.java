package com.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Dungeon {

    private Board[][] rooms;

    public Dungeon() {
        rooms = new Board[2][2];
        for(int r = 0; r < rooms.length; r++) {
            for(int c = 0; c < rooms[r].length; c++) {
                rooms[r][c]= new Board(RoomHandler.genBoard(new Space[17][17]));
            }
        }
    }

    public void render(SpriteBatch batch) {
        for(int r = 0; r < rooms.length; r++) {
            for(int c = 0; c < rooms[r].length; c++) {
                rooms[r][c].render(batch, 0);
            }
        }
    }
}
