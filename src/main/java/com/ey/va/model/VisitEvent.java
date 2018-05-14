package com.ey.va.model;

import java.time.LocalTime;

/**
 * 
 * Class representing an event related to the visit of a single guest
 * 
 * @author jacek.pruszkowski@xe05.ey.com
 *
 */
public class VisitEvent {

	private final LocalTime eventTime;
	private final VisitEventType type;

	/**
	 * @param eventTime point in time when the event occurred 
	 * @param type event type
	 */
	public VisitEvent(LocalTime eventTime, VisitEventType type) {
		if (eventTime == null || type == null) {
			throw new IllegalArgumentException("eventTime and type must be provided");
		}
		this.eventTime = eventTime;
		this.type = type;
	}

	public LocalTime getEventTime() {
		return eventTime;
	}

	public VisitEventType getType() {
		return type;
	}

	@Override
	public String toString() {
		return "VisitEvent [eventTime=" + eventTime + ", type=" + type + "]";
	}

}
