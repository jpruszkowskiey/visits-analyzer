package com.ey.va.model;

import java.time.LocalTime;

/**
 * 
 * Class representing visit of a single guest
 * 
 * @author jacek.pruszkowski@xe05.ey.com
 *
 */
public class Visit {

	private final LocalTime entranceTime;
	private final LocalTime leaveTime;

	/**
	 * @param entranceTime
	 *            guest entrance time
	 * @param leaveTime
	 *            guest leave time
	 */
	public Visit(LocalTime entranceTime, LocalTime leaveTime) {
		if (entranceTime == null || leaveTime == null) {
			throw new IllegalArgumentException("entrance and leave time must be provided");
		}
		if (leaveTime.isBefore(entranceTime)) {
			throw new IllegalArgumentException("leave time cannot before entrance time");
		}
		this.entranceTime = entranceTime;
		this.leaveTime = leaveTime;
	}

	public LocalTime getEntranceTime() {
		return entranceTime;
	}

	public LocalTime getLeaveTime() {
		return leaveTime;
	}

	@Override
	public String toString() {
		return "Visit [entranceTime=" + entranceTime + ", leaveTime=" + leaveTime + "]";
	}

}
