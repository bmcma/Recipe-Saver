package businesslayer;

public class ValidationException extends Exception{
	private static final long serialVersionUID = 1L;
	public ValidationException(){
		super("Not a valid input");
	}
	public ValidationException(String message){
		super(message);
	}
	public ValidationException(String message, Throwable throwable){
		super(message, throwable);
	}
	
	public ValidationException(Throwable throwable){
		super(throwable);
	}
}
