package net.stefangaertner.aoc18.pojo;

public class Worker {

	int id = -1;
	int work = 0;
	String payload = null;
	
	int offset = 0;
		
	public Worker(int id, int offset) {
		this.id = id;
		this.offset = offset;
	}

	public void work() {
		if (work > 0)
			work--;
	}

	public boolean done() {
		return payload != null && work == 0;
	}

	public boolean isFree() {
		return payload == null;
	}

	public void start(String payload) {
		this.payload = payload;
		this.work = payload.charAt(0) - 'A' + 1 + offset;
	}

	public void reset() {
		work = 0;
		payload = null;
	}
	
	public String getPayload() {
		return payload;
	}
	
	public String toString() {
		if (this.isFree()) {
			return "Worker " + this.id + " is free to work.";
		} else {
			return "Worker " + this.id + " is working on " + this.payload + " - work left: " + this.work;
		}
	}
}