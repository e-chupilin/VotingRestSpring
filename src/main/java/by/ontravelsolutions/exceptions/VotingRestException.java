package by.ontravelsolutions.exceptions;

import java.util.Arrays;

public class VotingRestException extends Exception{

	private static final long serialVersionUID = 1L;

	public VotingRestException() {
		super();
	}

	public VotingRestException(String message, Throwable cause) {
		super(message, cause);
	}

	public VotingRestException(String message) {
		super(message);
	}

	public VotingRestException(Throwable cause) {
		super(cause);
	}

	@Override
	public String toString() {
		return "VotingRestException [getMessage()=" + getMessage() + "]";
	}
	
	
}
