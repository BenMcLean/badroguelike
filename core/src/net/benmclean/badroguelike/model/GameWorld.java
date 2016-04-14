package net.benmclean.badroguelike.model;

//import squidpony.squidgrid.mapping.DungeonGenerator;

public class GameWorld {

    public static final int WORLD_SIZE_X = 200;
    public static final int WORLD_SIZE_Y = 200;

    //private DungeonGenerator dungeonGen = new DungeonGenerator();
    private char[][] bareDungeon, lineDungeon;

    private int playerX=4;
    private int playerY=4;
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

    public GameWorld () {}

    public Boolean isWall (int x, int y) {
        //if (x == 0 && y == 2) return false;
        if (x >= 0 && y >= 0 && x <= 8 && y <= 8)
            if (x == 0 || y == 0 || x == 8 || y == 8)
                return true;
            else
                return false;
        else
            return null;
    }
}

