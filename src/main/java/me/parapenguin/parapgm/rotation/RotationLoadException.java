package me.parapenguin.parapgm.rotation;

public class RotationLoadException extends Exception {

	private static final long serialVersionUID = 6288917033131370868L;
	
	String message;
	
	public RotationLoadException(String message) {
		this.message = message;
	}
	
	@Override
	public String getMessage() {
		return message;
	}
	
}
