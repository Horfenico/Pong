package com.mygdx.pong;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;

/**
 * Created by Eric on 12/6/2015.
 */
public class GameOverScreen implements Screen {

    final Pong pong;
    OrthographicCamera camera;
    String winner;

    GameOverScreen(final Pong png, String win)
    {
        pong = png;
        camera = new OrthographicCamera();
        camera.setToOrtho(false,800,480);
        pong.font.getData().setScale(2,2);
        winner = win;
    }

    @Override
    public void render(float delta)
    {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        //set camera
        camera.update();
        pong.batch.setProjectionMatrix(camera.combined);
        //Sprite batch
        pong.batch.begin();
        pong.font.draw(pong.batch, "Game Over!",800 / 2, 480 / 2);
        pong.font.draw(pong.batch, "Winner: " + winner  , 350, 480 / 3);
        if (winner == "Player")
            pong.font.draw(pong.batch, "Congratulations!!!" ,350, 480/4);
        else
            pong.font.draw(pong.batch, "Better luck next time!", 350, 480/4);
        pong.batch.end();
        if(Gdx.input.isTouched())
        {
            pong.setScreen(new MainMenu(pong));
            dispose();
        }
    }
    @Override
    public void resize(int width, int height)
    {
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
    }
}
