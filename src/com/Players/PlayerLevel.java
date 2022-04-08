package com.Players;

public class PlayerLevel implements java.io.Serializable{

    public static int lastLevel;

    private final long lv;
    private final long attack;
    private final long hp;
    private final long mp;
    private final long xp;

    public PlayerLevel(long lv, long attack, long hp, long mp, long xp) {
        this.lv = lv;
        this.attack = attack;
        this.hp = hp;
        this.mp = mp;
        this.xp = xp;
    }

    public PlayerLevel(String serializedData) {
        String[] args = serializedData.trim().split(",");
        String[] h = args[0].trim().split("\\s+");
        lv = Integer.parseInt(h[1]);
        attack = Integer.parseInt(args[1].trim());
        hp = Integer.parseInt(args[2].trim());
        mp = Integer.parseInt(args[3].trim());
        xp = Integer.parseInt(args[4].trim());
    }

    public long getLv() {
        return lv;
    }

    public long getAttack() {
        return attack;
    }

    public long getHp() {
        return hp;
    }

    public long getMp() {
        return mp;
    }

    public long getXp() {
        return xp;
    }

    @Override
    public String toString() {
        return "PlayerLevel{" +
                "lv=" + lv +
                ", attack=" + attack +
                ", hp=" + hp +
                ", mp=" + mp +
                ", xp=" + xp +
                '}';
    }
}
