package net.benmclean.badroguelike.model;

import squidpony.squidgrid.mapping.ClassicRogueMapGenerator;
import squidpony.squidgrid.mapping.DungeonGenerator;

public class GameWorld {

    public static final int SIZE_X = 64;
    public static final int SIZE_Y = 64;

    private DungeonGenerator dungeonGen = new DungeonGenerator(SIZE_X, SIZE_Y);
    private char[][] bareDungeon, lineDungeon;
    private ClassicRogueMapGenerator rogueGen;

    // Coord pt = generator.utility.randomFloor(myCharArray2D); would give a better starting place
    private int playerX=4;
    private int playerY=4;
    public int playerHP=50;
    public int playerMaxHP=100;

    public int getPlayerX(){return playerX;}
    public int getPlayerY(){return playerY;}
    public int setPlayerX(int x){playerX=x; return getPlayerX();}
    public int setPlayerY(int y){playerY=y; return getPlayerY();}
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
    }

    public Boolean isWall (int x, int y) {
        if (x < 0 || y < 0 || x > SIZE_X || y > SIZE_Y) return null;
        if (x == 0 || y == 0 || x == SIZE_X-1 || y == SIZE_Y-1) return true;
        return bareDungeon[x][y] == '#';
    }
}
