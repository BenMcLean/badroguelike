package net.benmclean.badroguelike.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import net.benmclean.badroguelike.controller.GameInputProcessor;
import net.benmclean.badroguelike.model.GameWorld;

public class GameScreen implements Screen, Disposable {
    public static final int VIRTUAL_WIDTH = 64;
    public static final int VIRTUAL_HEIGHT = 64;
    public Assets assets = new Assets();
    private Color worldBackgroundColor = Color.DARK_GRAY;
    private Color screenBackgroundColor = Color.BLACK;
    private Viewport worldView = new FitViewport(VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
    private Viewport screenView = new FitViewport(VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
    private SpriteBatch batch;
    private TiledMap map;
    private TiledMapRenderer tiledMapRenderer;
    private FrameBuffer frameBuffer = new FrameBuffer(Pixmap.Format.RGBA8888, VIRTUAL_HEIGHT, VIRTUAL_WIDTH, true, true);
    private Texture screenTexture;
    private TextureRegion screenRegion = new TextureRegion();
    public GameWorld world = new GameWorld();
    public GameInputProcessor input = new GameInputProcessor(world);

    public static TiledMapTileLayer.Cell makeCell (TiledMapTile tile) {TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell(); cell.setTile(tile); return cell;}

    @Override
    public void show() {
        batch = new SpriteBatch();

        map = new TiledMap();
        MapLayers layers = map.getLayers();
        TiledMapTileLayer layer = new TiledMapTileLayer(world.SIZE_X, world.SIZE_Y, 8, 8);
        for (int x = 0; x < world.SIZE_X; x++) {
            for (int y = 0; y < world.SIZE_Y; y++) {
                StaticTiledMapTile tile=null;
                Boolean answer = world.isWall(x, y);
                if (answer != null && !answer)
                    tile = new StaticTiledMapTile(assets.floor);
                else if (answer != null)
                    tile = new StaticTiledMapTile(assets.wall);
                if (tile != null) layer.setCell(x, y, makeCell(tile));
            }
        }
        layers.add(layer);
        tiledMapRenderer = new OrthogonalTiledMapRenderer(map);
        Gdx.input.setInputProcessor(input);
    }

    @Override
    public void render(float delta) {
        frameBuffer.begin();
        Gdx.gl.glClearColor(worldBackgroundColor.r, worldBackgroundColor.g, worldBackgroundColor.b, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        worldView.apply();
        worldView.getCamera().position.set(world.getPlayerX() * 8 + 4, world.getPlayerY() * 8 + 4, 0);
        worldView.update(VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
        tiledMapRenderer.setView((OrthographicCamera) worldView.getCamera());
        tiledMapRenderer.render();
        batch.setProjectionMatrix(worldView.getCamera().combined);
        batch.begin();
        batch.draw(assets.player, world.getPlayerX() * 8, world.getPlayerY() * 8);
        batch.end();
        frameBuffer.end();

        Gdx.gl.glClearColor(screenBackgroundColor.r, screenBackgroundColor.g, screenBackgroundColor.b, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        screenView.apply();
        screenView.getCamera().position.set(32, 32, 0);
        batch.setProjectionMatrix(screenView.getCamera().combined);
        batch.begin();
        screenTexture = frameBuffer.getColorBufferTexture();
        screenTexture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        screenRegion.setRegion(screenTexture);
        screenRegion.flip(false, true);
        batch.draw(screenRegion, 0, 0);
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        screenView.update(width, height);
    }

    @Override
    public void dispose() {
        batch.dispose();
        frameBuffer.dispose();
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
}
