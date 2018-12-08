package net.stefangaertner.aoc18.pojo;

import java.util.ArrayList;
import java.util.List;

public class Day {

	int year;
	int month;
	int day;

	public Day(int year2, int month2, int day2) {
		this.year = year2;
		this.month = month2;
		this.day = day2;
	}
	
	List<LogEvent> events = new ArrayList<>();
	
	public void addEvent(LogEvent e) {
		this.events.add(e);
		this.events.sort(LogEvent.getComparator());
	}

	@Override
	public String toString() {
		return "Day [year=" + year + ", month=" + month + ", day=" + day + "]";
	}
	
	public int getGuardId() {
		for (LogEvent e : this.events) {
			if (e.event.contains("#")) {
				
				for (String s : e.event.split(" ")) {
					if (s.contains("#")) {
						return Integer.parseInt(s.substring(1));
					}
				}
				
			}
		}
		return -1;
	}

	public int getSleepTime() {
		
		int sum = 0;
		
		boolean asleep = false;
		int time = 0;
		
		for (LogEvent e : this.events) {
			if (e.event.contains("#")) {
				continue;
			}
			asleep = !asleep;
			if (!asleep) {
				sum += e.min - time;
			}
			time = e.min;
		}
		return sum;
	}
	
	public int[] getSchedule() {
		
		boolean asleep = false;
		int time = 0;
		
		int[] day = new int[60];
		for (int i = 0; i < day.length; i++) {
			day[i] = 0;
		}
		
		for (LogEvent e : this.events) {
			if (e.event.contains("#")) {
				continue;
			}
			asleep = !asleep;
			if (asleep) {
				
				time = e.min;
				
			} else {
				// woke up
				
				for (int i = time; i < e.min; i++) {
					day[i] = 1;
				}
				
			}
		}
		
		return day;
	}
}
