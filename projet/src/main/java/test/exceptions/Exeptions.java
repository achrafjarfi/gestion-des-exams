package test.exceptions;

public class Exeptions extends Exception{
    public Exeptions(){
        super();
    }
    public Exeptions(String message){
        super(message);
    }
    public Exeptions(String message ,Throwable cause){
        super(message,cause);
    }
    public Exeptions(Throwable cause){
        super(cause);
    }
}
