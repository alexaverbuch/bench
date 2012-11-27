package bench.errors;

public class OperationFactoryException extends Exception {
	private static final long serialVersionUID = 5141322414459398624L;

	public OperationFactoryException() {
		super();
	}

	public OperationFactoryException(String msg) {
		super(msg);
	}

	public OperationFactoryException(Throwable cause) {
		super(cause);
	}

	public OperationFactoryException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
