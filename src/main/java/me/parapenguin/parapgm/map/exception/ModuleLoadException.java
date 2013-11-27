package me.parapenguin.parapgm.map.exception;

public class ModuleLoadException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4743495996323223505L;
	
	String message;
	
	public ModuleLoadException(String message) {
		this.message = message;
	}
	
	@Override
	public String getMessage() {
		return message;
	}
	
}
