package net.benmclean.GDXPixelShapeRenderer;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * Created by Benjamin on 4/23/2016.
 */
public class GDXPixelShapeRenderer {
    // com.badlogic.gdx.math.Bresenham2
    protected ShapeRenderer shapeRenderer;
    public GDXPixelShapeRenderer() {}
    public GDXPixelShapeRenderer setShapeRenderer (ShapeRenderer shapeRenderer) {
        this.shapeRenderer = shapeRenderer;
        return this;
    }

    public GDXPixelShapeRenderer point (float x, float y) {
        shapeRenderer.rect(x, y, 1, 1);
        return this;
    }
}
