//package com.Players;
//
//import com.Items.Armor;
//import com.Items.Item;
//import com.Items.Weapons;
//import com.Rooms.Room;
//
//import java.util.ArrayList;
//import java.util.Collection;
//
//
//public class Player {
//    private String name;
//    long hp; // hit points
//    //long mp; // magic points
//    long attack; // attack power
//    //long xp; // experience points
//    //long gp; // gold in inventory
//    //long lv; // player level
//    private Collection<Item> items = new ArrayList<>(); // what is this? maybe inventory?
//    private Room currentRoom;
//    private Weapons equippedWeapon = null;
//    private Armor equippedArmor = null;
//
///*
//*  int lv = 1;
//*  long attack = 5;
//*  long mp = 5;
//*  long hp = 15;
//*  int gp = 0;
//*  int xp = 0;
//* */
//
//    public Player(String name, long hp, long attack) {
//        setName(name);
//        setHp(hp);
//        setAttack(attack);
//    }
////    public Player(String name, long lv, long hp, long mp, long attack, long gp, long xp) {
////        setName(name);
////        setHp(hp);
////        setAttack(attack);
////        setMp(mp);
////        setXp(xp);
////        setGp(gp);
////        setLv(lv);
////    }
//
//    public int getRandomNumber(int min, int max) {
//        return (int) ((Math.random() * (max - min)) + min);
//    }
//
//
//    public long getHp() {
//        return hp;
//    }
//
//    public long getMp() {
//        return mp;
//    }
//
//    public void setMp(long mp) {
//        this.mp = mp;
//    }
//
//    public long getXp() {
//        return xp;
//    }
//
//    public void setXp(long xp) {
//        this.xp = xp;
//    }
//
//    public long getGp() {
//        return gp;
//    }
//
//    public void setGp(long gp) {
//        this.gp = gp;
//    }
//
//    public long getLv() {
//        return lv;
//    }
//
//    public void setLv(long lv) {
//        this.lv = lv;
//    }
//
//    public void setHp(long hp) {
//        this.hp = hp;
//    }
//
//    public long getAttack() {
//        return attack;
//    }
//
//    public void setAttack(long attack) {
//        this.attack = attack;
//    }
//
//    public Room getCurrentRoom() {
//        return currentRoom;
//    }
//
//    public void setCurrentRoom(Room currentRoom) {
//        this.currentRoom = currentRoom;
//    }
//
//    public void addItem(Item item) {
//        items.add(item);
//    }
//
//    public void removeItem(Item item) {
//        items.remove(item);
//    }
//
//    public Collection<Item> getItems() {
//        return items;
//    }
//
//    public void setItems(Collection<Item> items) {
//        this.items = items;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public Weapons getEquippedWeapon() {
//        return equippedWeapon;
//    }
//
//    public void setEquippedWeapon(Weapons equippedWeapon) {
//        this.equippedWeapon = equippedWeapon;
//    }
//
//    public Armor getEquippedArmor() {
//        return equippedArmor;
//    }
//
//    public void setEquippedArmor(Armor equippedArmor) {
//        this.equippedArmor = equippedArmor;
//    }
//
//    public Player battle(Player enemy) {
//        int randAtk = getRandomNumber(0,100);
//        if (randAtk > 80) {
//            System.out.println(getName() + " landed a critical hit!");
//            System.out.println(getName() + " dealt "+ (getAttack() * 2) + " damage!");
//            enemy.setHp(enemy.getHp()-(getAttack() * 2) );
//        }
//        else if (randAtk >= 20) {
//            System.out.println(getName() + " dealt "+ getAttack() + " damage!");
//            enemy.setHp(enemy.getHp() - getAttack());
//        }
//         else {
//            System.out.println(getName() + " missed!");
//        }
//        return enemy;
//    }
//
//    public void showPlayerDetails(){
//        System.out.println("Test");
//    }
//
//
////This is the old toString I refactored to get rid of this format -> Player{name='William', hp=100, attack=15}
////    @Override
////    public String toString() {
////        return "Player{" +
////                "name='" + name + '\'' +
////                ", hp=" + hp +
////                ", attack=" + attack +
////                '}';
////    }
//
//
//    @Override
//    public String toString() {
//        return "Here are your players details:\n" +
//                "Level: " + lv + '\n' +
//                "HP: " + hp + '\n' +
//                "MP: " + mp + '\n' +
//                "Attack: " + attack + '\n' +
//                "Gold: " + gp + '\n' +
//                "Experience: " + xp + '\n';
//    }
//}


package com.Players;

import com.Items.Armor;
import com.Items.Item;
import com.Items.Weapons;
import com.Rooms.Room;

import java.util.ArrayList;
import java.util.Collection;


public class Player implements java.io.Serializable{
    private String name;
    private long hp;
    private long attack;
    private Collection<Item> items = new ArrayList<>();
    private Room currentRoom;
    private Weapons equippedWeapon = null;
    private Armor equippedArmor = null;



    public Player(String name, long hp, long attack) {
        setName(name);
        setHp(hp);
        setAttack(attack);
    }

    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }


    public long getHp() {
        return hp;
    }

    public void setHp(long hp) {
        this.hp = hp;
    }

    public long getAttack() {
        return attack;
    }

    public void setAttack(long attack) {
        this.attack = attack;
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }

    public void setCurrentRoom(Room currentRoom) {
        this.currentRoom = currentRoom;
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public void removeItem(Item item) {
        items.remove(item);
    }

    public Collection<Item> getItems() {
        return items;
    }

    public void setItems(Collection<Item> items) {
        this.items = items;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Weapons getEquippedWeapon() {
        return equippedWeapon;
    }

    public void setEquippedWeapon(Weapons equippedWeapon) {
        this.equippedWeapon = equippedWeapon;
    }

    public Armor getEquippedArmor() {
        return equippedArmor;
    }

    public void setEquippedArmor(Armor equippedArmor) {
        this.equippedArmor = equippedArmor;
    }

    public Player battle(Player enemy) {
        int randAtk = getRandomNumber(0,100);
        if (randAtk > 80) {
            System.out.println(getName() + " landed a critical hit!");
            System.out.println(getName() + " dealt "+ (getAttack() * 2) + " damage!");
            enemy.setHp(enemy.getHp()-(getAttack() * 2) );
        }
        else if (randAtk >= 20) {
            System.out.println(getName() + " dealt "+ getAttack() + " damage!");
            enemy.setHp(enemy.getHp() - getAttack());
        }
        else {
            System.out.println(getName() + " missed!");
        }
        return enemy;
    }


    @Override
    public String toString() {
        return "Player{" +
                "name='" + name + '\'' +
                ", hp=" + hp +
                ", attack=" + attack +
                '}';
    }
}