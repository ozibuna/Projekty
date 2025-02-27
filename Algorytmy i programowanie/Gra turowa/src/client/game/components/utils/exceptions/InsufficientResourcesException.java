package client.game.components.utils.exceptions;

public class InsufficientResourcesException extends IllegalArgumentException{
    public InsufficientResourcesException(String message){
        super(message);
    }
}
