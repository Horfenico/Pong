package com.mygdx.pong;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by Eric on 12/6/2015.
 */
public class Opponent {
    Rectangle opponent;
    Ball b;

    public Opponent(Ball b)
    {
    //create a rectangle to logically represent the paddle
        opponent = new Rectangle();
        opponent.x = 780;
        opponent.y =  480 / 2 - 60 /2;

        //bottom screen edge
        opponent.width = 14;
        opponent.height = 60;

        this.b = b;
    }

    public void ProcessAIMove()
    {
        int decision = MathUtils.random(1, 3);
        int compy = 0;
        int ballCenter = (int)b.bal.y + (int) b.bal.height /2;

        switch(decision)
        {
            case 1:
                compy = (int)opponent.y;
                break;
            case 2:
                compy = (int)opponent.y + (int)opponent.height;
                break;
            case 3:
                compy = (int)opponent.y + (int)opponent.height/2;
                break;
        }
        if (Math.abs(compy - ballCenter) < 10)
            return;
        //Ball is to the left of paddle
        if(compy > ballCenter )
            opponent.y -= 135 * Gdx.graphics.getDeltaTime();
        else if (compy < ballCenter)
            opponent.y += 135 * Gdx.graphics.getDeltaTime();
    }
}
