package Keerthu.project.ARS_Backend.Handler;
public class InvalidCancellationException extends RuntimeException{
    public InvalidCancellationException(String message){
        super(message);
    }
}