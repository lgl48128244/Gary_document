package org.zttc.itat.exception;

@SuppressWarnings("serial")
public class DocumentException extends RuntimeException {

	public DocumentException() {
		super();
	}

	public DocumentException(String message, Throwable cause) {
		super(message, cause);
	}

	public DocumentException(String message) {
		super(message);
	}

	public DocumentException(Throwable cause) {
		super(cause);
	}
	
}
