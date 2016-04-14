package net.benmclean.badroguelike.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import net.benmclean.badroguelike.controller.GameInputProcessor;
import net.benmclean.badroguelike.model.GameWorld;

public class GameScreen implements Screen {
    public static final int VIRTUAL_WIDTH = 64;
    public static final int VIRTUAL_HEIGHT = 64;
    public Assets assets = new Assets();
    private Color color = Color.BLACK;
    private Viewport gameView;
    private SpriteBatch batch;
    private TiledMap map;
    private TiledMapRenderer renderer;
    public GameWorld world = new GameWorld();
    public GameInputProcessor input = new GameInputProcessor(world);

    public static TiledMapTileLayer.Cell makeCell (TiledMapTile tile) {TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell(); cell.setTile(tile); return cell;}

    @Override
    public void show() {
        gameView = new FitViewport(VIRTUAL_WIDTH, VIRTUAL_HEIGHT); // This is the internal resolution your game will display regardless of window size
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
        renderer = new OrthogonalTiledMapRenderer(map);
        Gdx.input.setInputProcessor(input);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(color.r, color.g, color.b, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        gameView.apply(); // we set this as the current viewport (we could have more viewports, for example a hudView)
        OrthographicCamera cam = (OrthographicCamera) gameView.getCamera();
        cam.position.set(world.getPlayerX() * 8 + 4, world.getPlayerY() * 8 + 4, 0);
        cam.update();
        renderer.setView(cam);
        renderer.render();
        batch.setProjectionMatrix(cam.combined); // Don't forget this else your viewport won't be used when rendering
        batch.begin();
        batch.draw(assets.player, world.getPlayerX() * 8, world.getPlayerY() * 8);
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
