package net.benmclean.badroguelike.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import net.benmclean.badroguelike.model.Direction;
import net.benmclean.badroguelike.model.GameWorld;
import net.benmclean.badroguelike.view.GameScreen;

import java.util.*;

public class GameInputProcessor implements InputProcessor {
    public GameWorld world;

    public GameInputProcessor (GameWorld world) {
        this.world = world;
        for (int i=0; i<TRACKED_KEYS_ARRAY.size(); i++)
            keyPressed[i] = false;
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
        boolean moved = true;
        switch (keycode) {
            case Input.Keys.ESCAPE:
                Gdx.app.exit();
            case Input.Keys.UP:
                world.movePlayer(Direction.NORTH);
                break;
            case Input.Keys.RIGHT:
                world.movePlayer(Direction.EAST);
                break;
            case Input.Keys.DOWN:
                world.movePlayer(Direction.SOUTH);
                break;
            case Input.Keys.LEFT:
                world.movePlayer(Direction.WEST);
                break;
            default:
                moved=false;
                break;
        }
        if (moved) world.endTurn();
    }
}
