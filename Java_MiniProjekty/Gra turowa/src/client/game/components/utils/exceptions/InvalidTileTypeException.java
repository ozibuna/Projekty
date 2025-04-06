package client.game.components.utils.exceptions;

public class InvalidTileTypeException extends RuntimeException{
    public InvalidTileTypeException(){
        super("You can not assign given action to tile of this type");
    }
}