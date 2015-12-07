package com.mygdx.pong;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by Eric on 12/6/2015.
 */
public class Ball {
    float xSpeed;
    float ySpeed;
    Texture ball;
    Rectangle bal;

    public Ball(int dir)
    {
        ball = new Texture(Gdx.files.internal("ball.png"));
        if(dir == 0)
            xSpeed = 250;
        else
            xSpeed = -250;
        bal = new Rectangle();
        bal.x = 800 /2;
        bal.y = 480 /2;
        bal.width = 16;
        bal.height = 16;
    }

    public void MoveBall()
    {
        bal.x += xSpeed * Gdx.graphics.getDeltaTime();
        bal.y += ySpeed * Gdx.graphics.getDeltaTime();
    }
}
