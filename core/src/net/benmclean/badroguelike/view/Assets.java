package net.benmclean.badroguelike.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

/**
 * Created by Benjamin on 4/4/2016.
 * I am using this tileset here: http://opengameart.org/content/rogue-dungeon
 */
public class Assets {
    TextureAtlas atlas;
    TextureAtlas.AtlasRegion[] character = new TextureAtlas.AtlasRegion[32];
    TextureAtlas.AtlasRegion[] environment = new TextureAtlas.AtlasRegion[78];
    TextureAtlas.AtlasRegion[] item = new TextureAtlas.AtlasRegion[48];

    TextureAtlas.AtlasRegion wall;
    TextureAtlas.AtlasRegion floor;
    TextureAtlas.AtlasRegion player;

    public Assets () {
        atlas = new TextureAtlas(Gdx.files.internal("roguedungeon/pack.atlas"));

        for (int x=0; x < character.length; x++) {
            character[x] = atlas.findRegion("Character" + (x+1));
            if (character[x] == null)
                throw new IllegalStateException("Missing graphic: Character" + (x+1));
        }
        for (int x=0; x < environment.length; x++) {
            environment[x] = atlas.findRegion("Environment" + (x+1));
            if (environment[x] == null)
                throw new IllegalStateException("Missing graphic: Environment" + (x+1));
        }
        for (int x=0; x < item.length; x++) {
            item[x] = atlas.findRegion("Item" + (x+1));
            if (item[x] == null)
                throw new IllegalStateException("Missing graphic: Item" + (x+1));
        }

        wall = environment[1];
        floor = environment[2];
        player = character[0];
    }
}
