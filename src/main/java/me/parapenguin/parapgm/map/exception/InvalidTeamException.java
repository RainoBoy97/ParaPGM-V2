package me.parapenguin.parapgm.map.exception;

public class InvalidTeamException extends Exception {
	
	private static final long serialVersionUID = -5426188253003033857L;
	
	String reason;
	
	public InvalidTeamException(String reason) {
		this.reason = reason;
	}
	
	public String getReason() {
		return reason;
	}
	
}
