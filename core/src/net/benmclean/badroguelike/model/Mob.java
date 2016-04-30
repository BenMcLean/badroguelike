package net.benmclean.badroguelike.model;

/**
 * Created by Benjamin on 4/29/2016.
 */
public class Mob {
    public enum Kind {
     HUMAN, ORC
    }

    protected Kind kind;
    protected int maxHP;
    protected int HP;
    protected boolean player;

    public Mob setKind (Kind kind) {
        this.kind = kind;
        return this;
    }
    public Kind getKind () {
        return kind;
    }
    public Mob setHP (int HP) {
        this.HP = HP;
        return this;
    }
    public int getHP () {
        return HP;
    }
    public Mob setMaxHP (int maxHP) {
        this.maxHP = maxHP;
        return this;
    }
    public int getMaxHP () {
        return maxHP;
    }
    public Mob setPlayer () {
        setPlayer(true);
        return this;
    }
    public Mob setPlayer (boolean player) {
        this.player = player;
        return this;
    }
    public boolean isPlayer () {
        return player;
    }

    Mob (Kind kind, int HP) {
        this(kind, HP, HP);
    }
    Mob (Kind kind, int HP, int maxHP) {
        this(kind, HP, maxHP, false);
    }
    Mob (Kind kind) {
        this(kind, false);
    }
    Mob (Kind kind, boolean isPlayer) {
        this(kind, 100, isPlayer);
    }
    Mob (Kind kind, int HP, boolean isPlayer) {
        this(kind, HP, HP, isPlayer);
    }
    Mob (Kind kind, int HP, int maxHP, boolean isPlayer) {
        setKind(kind);
        setHP(HP);
        setMaxHP(maxHP);
        setPlayer(player);
    }
}
