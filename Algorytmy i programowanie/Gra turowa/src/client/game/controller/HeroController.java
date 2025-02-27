package client.game.controller;

import client.game.components.Castle;
import client.game.components.map.tile.Tile;
import client.game.components.unit.Unit;
import client.game.components.unit.hero.Hero;

public class HeroController {
    public HeroController(){

    }

    public void attack(Hero hero,Unit target){
        try{
            hero.doAction(Hero->{
                hero.attack(target);
                return null;
            });
        }catch (Exception e){
            System.out.println(e);
        }
    }
    public void walk(Hero hero, Tile destination){
        try{
            hero.doAction(Hero->{
                hero.walk(destination);
                return null;
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void specialAction(Hero hero, Unit target){
        try{
            hero.doAction(Hero->{
                hero.specialAction(target);
                return null;
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void plunder(Hero hero){
        try{
            hero.doAction(Hero->{
                hero.plunder();
                return null;
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void attackCastle(Hero hero, Castle castle){
        try{
            hero.doAction(Hero->{
                hero.attackCastle(castle);
                return null;
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public boolean isUnitAlive(Unit unit){
        return unit.isAlive();
    }
}
