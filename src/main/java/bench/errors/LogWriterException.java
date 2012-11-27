package bench.errors;

public class LogWriterException extends Exception {
	private static final long serialVersionUID = 5141322414459398624L;

	public LogWriterException() {
		super();
	}

	public LogWriterException(String msg) {
		super(msg);
	}

	public LogWriterException(Throwable cause) {
		super(cause);
	}

	public LogWriterException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
