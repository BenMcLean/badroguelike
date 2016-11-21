package net.benmclean.badroguelike.DebugTools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import net.benmclean.badroguelike.view.Assets;
import net.benmclean.badroguelike.view.GameScreen;

import java.util.*;

/**
 * Created by Benjamin on 4/21/2016.
 */
public class GraphicsViewer implements Screen, InputProcessor {
    public static final int VIRTUAL_WIDTH = 64;
    public static final int VIRTUAL_HEIGHT = 64;
    public Assets assets = new Assets();
    private Color worldBackgroundColor = Color.DARK_GRAY;
    private Color screenBackgroundColor = Color.BLACK;
    private Viewport worldView = new FitViewport(VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
    private Viewport screenView = new FitViewport(VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
    private SpriteBatch batch =  new SpriteBatch();
    private FrameBuffer frameBuffer = new FrameBuffer(Pixmap.Format.RGBA8888, VIRTUAL_WIDTH, VIRTUAL_HEIGHT, true, true);
    private Texture screenTexture;
    private TextureRegion screenRegion = new TextureRegion();
    private BitmapFont font = new BitmapFont(Gdx.files.internal("tiny/tiny.fnt"));
    private int viewing=0;
    private int index=0;

    @Override
    public void show() {
        for (int i=0; i<TRACKED_KEYS_ARRAY.size(); i++)
            keyPressed[i] = false;
        font.setColor(Color.WHITE);
        screenView.getCamera().position.set(32, 32, 0);
        screenView.update(VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void render(float delta) {
        frameBuffer.begin();
        Gdx.gl.glClearColor(worldBackgroundColor.r, worldBackgroundColor.g, worldBackgroundColor.b, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        worldView.apply();
        worldView.getCamera().position.set(0, 0, 0);
        worldView.update(VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
        batch.setProjectionMatrix(worldView.getCamera().combined);
        batch.begin();
        draw(batch, assets.thing[viewing][index], -4, -4);
        font.draw(batch, assets.description(viewing, index), -28, -4);
        batch.end();
        frameBuffer.end();

        Gdx.gl.glClearColor(screenBackgroundColor.r, screenBackgroundColor.g, screenBackgroundColor.b, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        screenView.apply();
        batch.setProjectionMatrix(screenView.getCamera().combined);
        batch.begin();
        screenTexture = frameBuffer.getColorBufferTexture();
        screenTexture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        screenRegion.setRegion(screenTexture);
        screenRegion.flip(false, true);
        batch.draw(screenRegion, 0, 0);
        batch.end();
    }

    private void draw(SpriteBatch batch, TextureRegion region, float x, float y) {
        batch.draw(region, Math.round(x), Math.round(y));
    }

    @Override
    public void resize(int width, int height) {
        screenView.update(width, height); // also don't forget this
    }

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
        assets.dispose();
    }
    @Override
    public void hide() {}
    @Override
    public void pause() {}
    @Override
    public void resume() {}

    public static void toggleFullscreen() {
        if (Gdx.graphics.isFullscreen())
            Gdx.graphics.setWindowedMode(VIRTUAL_WIDTH*12, VIRTUAL_HEIGHT*12);
        else
            Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
    }

    public static final double REPEAT_RATE = 0.12;
    private double timeSinceRepeat = 0;

    public final static ArrayList<Integer> TRACKED_KEYS_ARRAY = new ArrayList<Integer>(Arrays.asList(
            Input.Keys.UP,
            Input.Keys.DOWN,
            Input.Keys.LEFT,
            Input.Keys.RIGHT,
            Input.Keys.SPACE,
            Input.Keys.ESCAPE,
            Input.Keys.ENTER,
            Input.Keys.ALT_LEFT,
            Input.Keys.ALT_RIGHT
    ));
    public final static Set<Integer> TRACKED_KEYS = Collections.unmodifiableSet(
            new HashSet<Integer>(TRACKED_KEYS_ARRAY));
    public boolean[] keyPressed = new boolean[TRACKED_KEYS_ARRAY.size()];

    public static int keyInt(int keycode) {
        for (int x = 0; x < TRACKED_KEYS_ARRAY.size(); x++)
            if (TRACKED_KEYS_ARRAY.get(x) == keycode)
                return x;
        throw new ArrayIndexOutOfBoundsException();  // This keycode is not contained in TRACKED_KEYS_ARRAY
    }

    @Override
    public boolean keyDown(int keycode) {
        if (TRACKED_KEYS.contains(keycode)) keyPressed[keyInt(keycode)] = true;

        if (keycode == Input.Keys.ENTER && (keyPressed[keyInt(Input.Keys.ALT_LEFT)] || keyPressed[keyInt(Input.Keys.ALT_RIGHT)]))
            GameScreen.toggleFullscreen();
        timeSinceRepeat = 0;
        moveFromInput(keycode);
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (TRACKED_KEYS.contains(keycode)) keyPressed[keyInt(keycode)] = false;
        return true;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    public void moveFromInput(int keycode) {
        switch (keycode) {
            case Input.Keys.ESCAPE:
                Gdx.app.exit();
            case Input.Keys.UP:
                viewing--;
                if (viewing < 0) viewing = assets.thing.length - 1;
                if (index >= assets.thing[viewing].length) index = assets.thing[viewing].length - 1;
                break;
            case Input.Keys.RIGHT:
                index++;
                if (index >= assets.thing[viewing].length) index=0;
                break;
            case Input.Keys.DOWN:
                viewing++;
                if (viewing >= assets.thing.length) viewing=0;
                if (index >= assets.thing[viewing].length) index = assets.thing[viewing].length - 1;
                break;
            case Input.Keys.LEFT:
                index--;
                if (index < 0) index = assets.thing[viewing].length - 1;
                break;
        }
    }
}
