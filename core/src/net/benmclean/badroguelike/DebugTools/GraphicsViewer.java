package net.benmclean.badroguelike.DebugTools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
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
    private Color color = Color.DARK_GRAY;
    private Viewport gameView;
    private SpriteBatch batch;
    private BitmapFont font;
    private int viewing=0;
    private int index=0;

    @Override
    public void show() {
        for (int i=0; i<TRACKED_KEYS_ARRAY.size(); i++)
            keyPressed[i] = false;
        gameView = new FitViewport(VIRTUAL_WIDTH, VIRTUAL_HEIGHT); // This is the internal resolution your game will display regardless of window size
        batch = new SpriteBatch();
        font = new BitmapFont(Gdx.files.internal("tiny/tiny.fnt"));
        font.setColor(Color.WHITE);
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(color.r, color.g, color.b, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        gameView.apply(); // we set this as the current viewport (we could have more viewports, for example a hudView)
        OrthographicCamera cam = (OrthographicCamera) gameView.getCamera();
        cam.position.set(0, 0, 0);
        cam.update();
        batch.setProjectionMatrix(cam.combined); // Don't forget this else your viewport won't be used when rendering
        batch.begin();
        draw(batch, assets.thing[viewing][index], -4, -4);
        font.draw(batch, assets.description(viewing, index), -28, -4);
        batch.end();
    }

    private void draw(SpriteBatch batch, TextureRegion region, float x, float y) {
        batch.draw(region, Math.round(x), Math.round(y));
    }

    @Override
    public void resize(int width, int height) {
        gameView.update(width, height); // also don't forget this
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
