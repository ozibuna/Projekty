package client.game.components.utils.exceptions;

public class TileAlreadyOccupiedException extends RuntimeException{
    public TileAlreadyOccupiedException(String message){
        super(message);
    }
}
