package com.mygdx.pong;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by Eric on 12/6/2015.
 */
public class Player {
    Texture playerPaddle;
    Rectangle player;

    public Player()
    {
        playerPaddle = new Texture(Gdx.files.internal("player.png"));
        //create a rectangle to logically represent the paddle
        player = new Rectangle();
        player.x = 10;
        player.y =  480 / 2 - 60 /2;

        //bottom screen edge
        player.width = 14;
        player.height = 60;

    }
}
