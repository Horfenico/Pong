package com.mygdx.drop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;

/**
 * Created by Eric on 12/5/2015.
 */
public class MainMenuScreen implements Screen {
    final drop game;
    OrthographicCamera camera;

    public MainMenuScreen(final drop gam)
    {
        game = gam;
        camera = new OrthographicCamera();
        camera.setToOrtho(false,800,480);
    }

    @Override
    public  void render(float delta)
    {
        Gdx.gl.glClearColor(0,0,0.2f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        //set camera
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        //begin sprite batch
        game.batch.begin();
        game.font.draw(game.batch,"Welcome to Drop!!!", 100,150);
        game.font.draw(game.batch,"Tap Anywhere To Begin!", 100, 100);
        game.batch.end();

        if(Gdx.input.isTouched())
        {
            game.setScreen(new GameScreen(game));
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
