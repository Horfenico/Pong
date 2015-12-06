package com.mygdx.pong;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;


/**
 * Created by Eric on 12/6/2015.
 */
public class GameScreen implements Screen
{
    final Pong pong;
    Vector3 touchpos;
    Texture background;
    Texture playerPaddle;
    Texture ball;
    OrthographicCamera camera;
    Rectangle player;
    Rectangle opponent;
    Rectangle bal;
    int playerScore;
    int oppScore;

    public GameScreen(final Pong png)
    {
        this.pong = png;

        //Load paddle textures
        playerPaddle = new Texture(Gdx.files.internal("player.png"));
        ball = new Texture(Gdx.files.internal("ball.png"));
        background = new Texture(Gdx.files.internal("background.png"));

        //TODO sound

        //create camera and spritebatch
        camera = new OrthographicCamera();
        camera.setToOrtho(false,800,480);

        //create a rectangle to logically represent the bucket
        player = new Rectangle();
        player.x = 30;
        player.y =  480 / 2 - 60 /2;

        //bottom screen edge
        player.width = 14;
        player.height = 60;

        //create a rectangle to logically represent the bucket
        opponent = new Rectangle();
        opponent.x = 770;
        opponent.y =  480 / 2 - 60 /2;

        //bottom screen edge
        opponent.width = 14;
        opponent.height = 60;

        bal = new Rectangle();
        bal.x = 800 /2;
        bal.y = 480 /2;
        bal.width = 16;
        bal.height = 16;
    }

    public void render(float delta)
    {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        pong.batch.setProjectionMatrix(camera.combined);
        pong.batch.begin();
        pong.batch.draw(background, 0, 0);
        pong.font.draw(pong.batch, "Player: " + playerScore, 50, 450);
        pong.font.draw(pong.batch, "Opponent: " + oppScore, 625, 450);
        pong.batch.draw(playerPaddle, player.x, player.y);
        pong.batch.draw(playerPaddle, opponent.x, opponent.y);
        pong.batch.draw(ball,bal.x,bal.y);
        pong.batch.end();

        //process input
        if (Gdx.input.isTouched())
        {
            touchpos = new Vector3();
            touchpos.set(Gdx.input.getX(), Gdx.input.getY(),0);
            camera.unproject(touchpos);
            player.y = touchpos.y - 60 / 2;
        }

        //Keyboard
        if (Gdx.input.isKeyPressed(Input.Keys.UP))
            player.y += 200 * Gdx.graphics.getDeltaTime();
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN))
            player.y -= 200 * Gdx.graphics.getDeltaTime();

        //Make sure player stays within bounds
        if (player.y < 24)
            player.y = 24;
        if (player.y > 480 - 88)
            player.y = 480 - 88;
        //Make AI stay in bounds
        if (opponent.y < 24)
            opponent.y = 24;
        if (opponent.y > 480 - 88)
            opponent.y = 480 - 88;
        //Make ball stay in vertical bounds, check if it has gone out of horizontal bounds
        if (bal.y < 24)
            bal.y = 24;
        if (bal.y > 480 - 88)
            bal.y = 480 - 88;
        if (bal.x < 0)
        {
            bal.x = 800 /2;
            bal.y = 480 /2;
            oppScore++;
        }
        if (bal.x > 480)
        {
            bal.x = 800 /2;
            bal.y = 480 /2;
            playerScore++;
        }

        bal.x -= 50 * Gdx.graphics.getDeltaTime();

    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void show() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
        playerPaddle.dispose();
        ball.dispose();
        background.dispose();
    }

}
