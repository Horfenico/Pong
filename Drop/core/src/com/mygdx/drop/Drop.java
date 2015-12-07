package com.mygdx.drop;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import java.util.Iterator;
public class Drop extends ApplicationAdapter {
	private Texture dropImage;
    private Texture bucketImage;
    private Sound dropSound;
    private Music rainMusic;
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private Rectangle bucket;
    private Array<Rectangle> rainDrops;
    private long lastDropTime;
	@Override
	public void create () {
		//load images for droplet and bucket
        dropImage = new Texture(Gdx.files.internal("droplet.png"));
        bucketImage = new Texture(Gdx.files.internal("bucket.png"));
        //load drop sound effect and rain music
        dropSound = Gdx.audio.newSound(Gdx.files.internal("drop.wav"));
        rainMusic = Gdx.audio.newMusic(Gdx.files.internal("rain.mp3"));
        //start BGM playback
        rainMusic.setLooping(true);
        rainMusic.play();
        //Create camera
        camera = new OrthographicCamera();
        camera.setToOrtho(false,800,480);
        //Create SpriteBatch
        batch = new SpriteBatch();
        //Instantiate rectangle, specify values
        bucket = new Rectangle();
        bucket.x = 800 / 2 - 64 / 2;
        bucket.y = 20;
        bucket.width = 64;
        bucket.height = 64;
        //Instantiate Raindrops and create first raindrop
        rainDrops = new Array<Rectangle>();
        SpawnRaindrop();
	}
	@Override
	public void render () {
        //Clear screen with dark blue color
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        //Update the camera
        camera.update();
        //Render the bucket
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(bucketImage, bucket.x, bucket.y);
        for(Rectangle raindrop: rainDrops)
            batch.draw(dropImage, raindrop.x, raindrop.y);
        batch.end();
        //Make bucket move
        //Check for screen touch
        if(Gdx.input.isTouched())
        {
            Vector3 touchPos = new Vector3();
            touchPos.set(Gdx.input.getX(),Gdx.input.getY(),0);
            camera.unproject(touchPos);
            bucket.x = (int)touchPos.x - 64 / 2;
        }
        //Keyboard input
        if(Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) bucket.x -= 200 * Gdx.graphics.getDeltaTime();
        if(Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) bucket.x += 200 * Gdx.graphics.getDeltaTime();
        //Make sure bucket stays within the screen
        if (bucket.x < 0) bucket.x = 0;
        if (bucket.x > 800 - 64) bucket.x = 800 - 64;
        //check time since we last spawned raindrop and spawns a new one if necessary
        if(TimeUtils.nanoTime() - lastDropTime > 1000000000) SpawnRaindrop();
        //make raindrops fall remove from array if past bottom of the screen
        Iterator<Rectangle> iter = rainDrops.iterator();
        while(iter.hasNext())
        {
            Rectangle raindrop = iter.next();
            raindrop.y -= 200 * Gdx.graphics.getDeltaTime();
            if(raindrop.y + 64 < 0) iter.remove();
            if(raindrop.overlaps(bucket))
            {
                dropSound.play();
                iter.remove();
            }
        }
    }
    private void SpawnRaindrop()
    {
        Rectangle raindrop = new Rectangle();
        //Generate random x position
        raindrop.x = MathUtils.random(0, 800 - 64);
        raindrop.y = 480;
        raindrop.width = 64;
        raindrop.height = 64;
        rainDrops.add(raindrop);
        lastDropTime = TimeUtils.nanoTime();
    }
    //Help with garbage collection
    @Override
    public void dispose()
    {
        dropImage.dispose();
        bucketImage.dispose();
        dropSound.dispose();
        rainMusic.dispose();
        batch.dispose();
    }
}
