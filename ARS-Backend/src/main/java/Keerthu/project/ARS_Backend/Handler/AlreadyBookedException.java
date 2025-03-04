package Keerthu.project.ARS_Backend.Handler;

public class AlreadyBookedException extends RuntimeException{

    public AlreadyBookedException(String message){
        super(message);
    }
}
