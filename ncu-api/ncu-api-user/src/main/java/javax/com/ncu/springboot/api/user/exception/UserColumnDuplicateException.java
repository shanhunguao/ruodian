package javax.com.ncu.springboot.api.user.exception;

public class UserColumnDuplicateException extends RuntimeException {
	
	public UserColumnDuplicateException() {
		
	}
	
	public UserColumnDuplicateException(String msg) {
		super(msg);
	}
	
	public UserColumnDuplicateException(Throwable cause) {
		super(cause);
	}
	
	public UserColumnDuplicateException(String msg, Throwable cause) {
		super(msg, cause);
	}
	
}
