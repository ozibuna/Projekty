package client.game.components.utils.exceptions;

public class VillagePlunderedException extends RuntimeException{
    public VillagePlunderedException(){
        super("You can not plunder village that has been already plundered");
    }
}