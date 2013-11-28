package me.parapenguin.parapgm.server;

public enum ServerPhase {
	
	Waiting,
	Cycling(Waiting),
	Playing(Cycling),
	Starting(Playing);
	
	
	int duration;
	ServerPhase next;
	
	ServerPhase() {
		// do nothing
	}
	
	ServerPhase(ServerPhase next) {
		this(next, -1);
	}
	
	ServerPhase(ServerPhase next, int duration) {
		this.duration = duration;
		this.next = next;
	}
	
	public ServerPhase getNextPhase() {
		if(next == null) next = Starting;
		
		return next;
	}
	
	public int getDuration() {
		return duration;
	}
	
	public boolean hasDuration() {
		return duration > 0;
	}
	
}
