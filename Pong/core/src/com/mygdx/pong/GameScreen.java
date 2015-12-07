package com.mygdx.pong;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;import com.badlogic.gdx.math.MathUtils;
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
    Ball b;
    Player p;
    Opponent o;
    OrthographicCamera camera;
    int playerScore;
    int oppScore;
    int STATE;
    public static final int GAMEPLAY = 0;
    public static final int GAMEEND = 1;
    public static final int HITPLAYER = 2;
    public static final int HITOPP = 3;


    public GameScreen(final Pong png)
    {
        this.pong = png;
        STATE = GAMEPLAY;
        int rand = MathUtils.random(0,1);
         b = new Ball(rand);
         p = new Player();
        o = new Opponent(b);
        //Set background texture
        background = new Texture(Gdx.files.internal("background.png"));
        //create camera and spritebatch
        camera = new OrthographicCamera();
        camera.setToOrtho(false,800,480);
        //TODO sound
    }

    public void render(float delta)
    {
        DrawGame();
        ProcessInput();
        CheckBounds();
        SetState();
        ProcessState();
        ProcessGoal();
        b.MoveBall();
        o.ProcessAIMove();
    }

    @Override
    public void dispose() {
        p.playerPaddle.dispose();
        b.ball.dispose();
        background.dispose();
    }

    private void ProcessInput()
    {
        //process input
        if (Gdx.input.isTouched())
        {
            touchpos = new Vector3();
            touchpos.set(Gdx.input.getX(), Gdx.input.getY(),0);
            camera.unproject(touchpos);
            p.player.y = touchpos.y - 60 / 2;
        }

        //Keyboard
        if (Gdx.input.isKeyPressed(Input.Keys.UP))
            p.player.y += 200 * Gdx.graphics.getDeltaTime();
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN))
            p.player.y -= 200 * Gdx.graphics.getDeltaTime();
    }

    private void DrawGame()
    {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        pong.batch.setProjectionMatrix(camera.combined);
        pong.batch.begin();
        pong.batch.draw(background, 0, 0);
        pong.font.draw(pong.batch, "Player: " + playerScore, 50, 450);
        pong.font.draw(pong.batch, "Opponent: " + oppScore, 625, 450);
        pong.batch.draw(p.playerPaddle, p.player.x, p.player.y);
        pong.batch.draw(p.playerPaddle, o.opponent.x, o.opponent.y);
        pong.batch.draw(b.ball,b.bal.x,b.bal.y);
        pong.batch.end();
    }

    private void CheckBounds()
    {
        //Make sure player stays within bounds
        if (p.player.y < 24)
            p.player.y = 24;
        if (p.player.y > 480 - 88)
            p.player.y = 480 - 88;
        //Make AI stay in bounds
        if (o.opponent.y < 24)
            o.opponent.y = 24;
        if (o.opponent.y > 480 - 88)
            o.opponent.y = 480 - 88;
        //Make ball stay in vertical bounds, check if it has gone out of horizontal bounds
        if (b.bal.y < 24)
            b.ySpeed = -b.ySpeed;
        if (b.bal.y > 480 - 44)
            b.ySpeed = -b.ySpeed;
    }

    private void SetState()
    {
        if (b.bal.overlaps(p.player))
            STATE = HITPLAYER;
        if (b.bal.overlaps(o.opponent))
            STATE = HITOPP;
        if (oppScore == 10 || playerScore == 10)
            STATE = GAMEEND;
    }

    private void ProcessState()
    {
        switch(STATE)
        {
            case HITPLAYER :
                float paddleCenter = p.player.y + p.player.height / 2;
                float ballCenter = b.bal.y + b.bal.height / 2;
                float paddleLoc = ballCenter - paddleCenter;
                b.xSpeed = -b.xSpeed ;
                b.ySpeed = paddleLoc * 5;
                STATE = GAMEPLAY;
                break;
            case HITOPP :
                paddleCenter = o.opponent.y + p.player.height / 2;
                ballCenter = b.bal.y + b.bal.height / 2;
                paddleLoc = ballCenter - paddleCenter;
                b.xSpeed = -b.xSpeed;
                b.ySpeed = paddleLoc * 5;
                STATE = GAMEPLAY;
                break;
            case GAMEEND :
                if (playerScore == 10)
                    pong.setScreen(new GameOverScreen(pong,"Player"));
                else
                    pong.setScreen(new GameOverScreen(pong, "Computer"));
                dispose();
                break;
        }
    }

    private void ProcessGoal()
    {
        if (b.bal.x < 0)
        {
            b.bal.x = 800 /2;
            b.bal.y = 480 /2;
            oppScore++;
            b.xSpeed  = -250;
            b.ySpeed = 0;
        }
        if (b.bal.x > 800)
        {
            b.bal.x = 800 /2;
            b.bal.y = 480 /2;
            playerScore++;
            b.xSpeed = 250;
            b.ySpeed = 0;
        }
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
}
