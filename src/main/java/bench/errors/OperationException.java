package bench.errors;

public class OperationException extends Exception {
	private static final long serialVersionUID = 5141322414459398624L;

	public OperationException() {
		super();
	}

	public OperationException(String msg) {
		super(msg);
	}

	public OperationException(Throwable cause) {
		super(cause);
	}

	public OperationException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
