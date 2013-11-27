package me.parapenguin.parapgm.map.exception;

public class MapLoadException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3916258479623734870L;
	
	String message;
	
	public MapLoadException(String message) {
		this.message = message;
	}
	
	@Override
	public String getMessage() {
		return message;
	}
	
}
