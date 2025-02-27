package client.game.controller;

import client.game.components.Castle;
import client.game.components.Resources;
import client.game.components.unit.hero.Hero;
import client.game.components.utils.HeroFactory;

import java.util.Map;

public class CastleController {
    public CastleController(){

    }
    public <T extends Hero> T spawnTroop(String heroType, Castle castle) {
        HeroFactory heroFactory = new HeroFactory();
        return heroFactory.spawnHero(heroType, castle);
    }
    public void upgradeCastle(Castle castle){
        try{
            castle.upgradeCastle();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public Map<Resources,Integer> returnResource(Castle castle){
        return castle.getResources();
    }
}
