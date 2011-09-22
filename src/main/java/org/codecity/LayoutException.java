package org.codecity;

public class LayoutException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public LayoutException() {
		super();
	}

	public LayoutException(String message, Throwable cause) {
		super(message, cause);
	}

	public LayoutException(String message) {
		super(message);
	}

	public LayoutException(Throwable cause) {
		super(cause);
	}
}
