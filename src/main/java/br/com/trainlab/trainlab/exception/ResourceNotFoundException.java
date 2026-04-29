package br.com.trainlab.trainlab.exception;

public class ResourceNotFoundException extends RuntimeException{

    private ErrorMessage errorMessage;

    public ResourceNotFoundException(ErrorMessage errorMessage){
        super(errorMessage.message());
        this.errorMessage = errorMessage;
    }

    public ErrorMessage getErrorMessage() {
        return errorMessage;
    }
}
