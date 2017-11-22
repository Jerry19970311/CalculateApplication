package cn.edu.bistu.cs.se.calculateapplication;

public class IllegalInputException extends Exception{
    public IllegalInputException(){
        super();
    }
    public IllegalInputException(String message){
        super(message);
    }
}
