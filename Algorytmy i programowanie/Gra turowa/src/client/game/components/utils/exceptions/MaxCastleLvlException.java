package client.game.components.utils.exceptions;

public class MaxCastleLvlException extends RuntimeException{
    public MaxCastleLvlException(){
        super("You can not upgrade this castle because you have already maxed castle level");
    }
}
