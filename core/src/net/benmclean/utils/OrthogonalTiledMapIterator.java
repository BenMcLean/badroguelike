package net.benmclean.utils;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import squidpony.squidmath.Coord;

import java.util.Iterator;

/**
 * Created by Benjamin on 5/5/2016.
 */
public class OrthogonalTiledMapIterator implements Iterator<Coord> {
    protected float unitScale;
    protected int col1, col2, row1, row2, row, col;

    public OrthogonalTiledMapIterator(OrthographicCamera camera, TiledMapTileLayer layer) {
        this(camera, layer, 1.0f);
    }

    public OrthogonalTiledMapIterator(OrthographicCamera camera, TiledMapTileLayer layer, float unitScale) {
        this.unitScale = unitScale;
        float width = camera.viewportWidth * camera.zoom;
        float height = camera.viewportHeight * camera.zoom;
        Rectangle viewBounds = new Rectangle().set(camera.position.x - width / 2, camera.position.y - height / 2, width, height);

        final int layerWidth = layer.getWidth();
        final int layerHeight = layer.getHeight();

        final float layerTileWidth = layer.getTileWidth() * unitScale;
        final float layerTileHeight = layer.getTileHeight() * unitScale;

        col1 = Math.max(0, (int) (viewBounds.x / layerTileWidth));
        col2 = Math.min(layerWidth, (int) ((viewBounds.x + viewBounds.width + layerTileWidth) / layerTileWidth));

        row1 = Math.max(0, (int) (viewBounds.y / layerTileHeight));
        row2 = Math.min(layerHeight, (int) ((viewBounds.y + viewBounds.height + layerTileHeight) / layerTileHeight));

        row = row2-1;
        col = col1-1;

//        for (int row = row2; row >= row1; row--) {
//            for (int col = col1; col < col2; col++) {
//            }
//        }
    }

    @Override
    public boolean hasNext() {
        return row >= row1 && !(row == row1 && col == col2);
    }

    @Override
    public Coord next() {

        //Gdx.app.log("OrthogonalTileMapIterator", "row, col = " + row + ", " + col);
        col++;
        if (col >= col2) {
            row--;
            col=col1;
        }
        return Coord.get(col, row);
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }
}
