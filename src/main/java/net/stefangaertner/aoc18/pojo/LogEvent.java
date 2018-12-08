package net.stefangaertner.aoc18.pojo;

import java.util.Comparator;

public class LogEvent {

	int hour;
	int min;
	String event;

	public LogEvent(int hour2, int min2, String event2) {
		this.hour = hour2;
		this.min = min2;
		this.event = event2;
	}

	@Override
	public String toString() {
		return "LogEvent [hour=" + hour + ", min=" + min + ", event=" + event + "]";
	}

	public static Comparator<? super LogEvent> getComparator() {
		return new Comparator<LogEvent>() {

			@Override
			public int compare(LogEvent arg0, LogEvent arg1) {
				if (arg0.hour == arg1.hour) {
					return arg0.min - arg1.min;
				}
				return arg0.hour - arg1.hour;
			}
			
		};
	}	
}
