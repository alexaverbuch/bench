package bench.errors;

public class LogReaderException extends Exception {
	private static final long serialVersionUID = 5141322414459398624L;

	public LogReaderException() {
		super();
	}

	public LogReaderException(String msg) {
		super(msg);
	}

	public LogReaderException(Throwable cause) {
		super(cause);
	}

	public LogReaderException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
