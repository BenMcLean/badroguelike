package net.benmclean.badroguelike.model;

import squidpony.squidgrid.mapping.DungeonGenerator;
import squidpony.squidgrid.mapping.DungeonUtility;
import squidpony.squidmath.Coord;
import java.util.UUID;

public class GameWorld {

    public static final int SIZE_X = 64;
    public static final int SIZE_Y = 64;

    private DungeonGenerator dungeonGen = new DungeonGenerator(SIZE_X, SIZE_Y);
    private DungeonUtility dungeonUtil = new DungeonUtility();
    private char[][] bareDungeon, lineDungeon;
    private LazySpatialMap<Mob> mobs = new LazySpatialMap<Mob>();
    public int playerHP=50;
    public int playerMaxHP=100;
    protected UUID playerUUID;

    public int getPlayerX() {return mobs.getPosition(playerUUID).getX();}
    public int getPlayerY() {return mobs.getPosition(playerUUID).getY();}
    public void setPlayerX(int x) {setPlayer(x, getPlayerY());}
    public void setPlayerY(int y) {setPlayer(getPlayerX(), y);}
    public void setPlayer(int x, int y) {mobs.move(playerUUID, Coord.get(x, y));}

    public Boolean movePlayer(Direction direction) {
        return movePlayer(direction.dx(), direction.dy());
    }
    public Boolean movePlayer(int dx, int dy) {
        Boolean answer = isWall(getPlayerX() + dx, getPlayerY() + dy);
        if (answer != null && !answer) {
            setPlayerX(getPlayerX() + dx);
            setPlayerY(getPlayerY() + dy);
            return true;
        }
        return false;
    }

    public GameWorld () {
        bareDungeon = dungeonGen.generate();
        playerUUID = mobs.put(
                dungeonUtil.randomFloor(bareDungeon),
                new Mob(Mob.Kind.HUMAN, true)
                );
    }

    public Boolean isWall (int x, int y) {
        if (x < 0 || y < 0 || x > SIZE_X || y > SIZE_Y) return null;
        if (x == 0 || y == 0 || x == SIZE_X-1 || y == SIZE_Y-1) return true;
        return bareDungeon[x][y] == '#';
    }
}
