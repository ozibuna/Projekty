package client.game.management;

import client.game.components.Player;
import client.game.components.map.WorldMap;

public interface IGameManager {
    void startGame();
    void endGame();
    void nextTurn();
    void updateMap(WorldMap map);
    void playTurn();
    void gameOver();
    void gameWon();
    void setPlayerName(String name);
    void setRefreshMap(boolean refreshMap);
    boolean isRefreshMap();
    WorldMap getWorldMap();
    void setPlayerID(int playerID);
    void setTotalPlayers(int players);
    void setNetworkGame(boolean networkGame);
    boolean isNetworkGame();
    boolean getCanMove();
    Player getPlayer();
    int getTotalPlayers();
}
