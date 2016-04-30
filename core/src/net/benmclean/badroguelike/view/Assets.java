package net.benmclean.badroguelike.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Disposable;

/**
 * Created by Benjamin on 4/4/2016.
 * I am using this tileset here: http://opengameart.org/content/rogue-dungeon
 */
public class Assets implements Disposable {
    public TextureAtlas atlas;
    public TextureAtlas.AtlasRegion[] character = new TextureAtlas.AtlasRegion[32];
    public TextureAtlas.AtlasRegion[] environment = new TextureAtlas.AtlasRegion[78];
    public TextureAtlas.AtlasRegion[] item = new TextureAtlas.AtlasRegion[48];
    public TextureAtlas.AtlasRegion[][] thing = {character, environment, item};

    public TextureAtlas.AtlasRegion wall;
    public TextureAtlas.AtlasRegion floor;
    public TextureAtlas.AtlasRegion human;
    public TextureAtlas.AtlasRegion orc;

    public static String description (int viewing, int index) {
        return description(viewing) + "[" + index + "]";
    }
    public static String description (int viewing) {
        switch (viewing) {
            case 1: return "environment";
            case 2: return "item";
            default: return "character";
        }
    }

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
        human = character[0];
        orc = character[1];
    }

    public void dispose() {
        atlas.dispose();
    }
}
