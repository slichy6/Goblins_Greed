package com.Items;

public class Weapons extends Item implements java.io.Serializable{
    private long attack;
    public Weapons(String name, String desc, long value, long attack) {
        super(name, desc, value);
        setAttack(attack);
    }

    public long getAttack() {
        return attack;
    }

    public void setAttack(long attack) {
        this.attack = attack;
    }
}
