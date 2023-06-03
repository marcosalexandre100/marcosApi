package br.com.desenvolvimento.marcosApi.services.expection;

public class DataIntegratyViolationException extends RuntimeException{
    public DataIntegratyViolationException(String message){
        super(message);
    }
}
