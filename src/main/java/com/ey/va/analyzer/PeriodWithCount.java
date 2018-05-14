package com.ey.va.analyzer;

import java.time.LocalTime;

/**
 * 
 * Class representing time period and the count of something during that period
 * 
 * @author jacek.pruszkowski@xe05.ey.com
 *
 */
public class PeriodWithCount {

	private final LocalTime start;
	private final LocalTime end;
	private final int count;

	/**
	 * 
	 * @param start
	 *            start period
	 * @param end
	 *            end period
	 * @param count
	 *            count of something
	 */
	public PeriodWithCount(LocalTime start, LocalTime end, int count) {
		if (count < 0) {
			throw new IllegalArgumentException("Count cannot be lower than zero");
		}
		if (start == null || end == null) {
			throw new IllegalArgumentException("start and end time must be provided");
		}
		if (end.isBefore(start)) {
			throw new IllegalArgumentException("end time cannot before start time");
		}
		this.start = start;
		this.end = end;
		this.count = count;
	}

	public LocalTime getStart() {
		return start;
	}

	public LocalTime getEnd() {
		return end;
	}

	public int getCount() {
		return count;
	}

}
