package com.mygdx.pong;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;

/**
 * Created by Eric on 12/6/2015.
 */
public class MainMenu implements Screen {
    final Pong pong;
    OrthographicCamera camera;

    MainMenu(final Pong png)
    {
        pong = png;
        camera = new OrthographicCamera();
        camera.setToOrtho(false,800,480);
        pong.font.getData().setScale(2,2);
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
        pong.font.draw(pong.batch, "Pong",400, 480);
        pong.font.draw(pong.batch, "Touch To Start!" , 350, 240);
        pong.batch.end();
        if(Gdx.input.isTouched())
        {
            pong.setScreen(new GameScreen(pong));
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
