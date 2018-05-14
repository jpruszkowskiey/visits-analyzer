package com.ey.va.model;

/**
 * 
 * Enumeration representing type of VisitEvent
 * 
 * @see com.ey.va.model.VisitEvent
 * 
 * @author jacek.pruszkowski@xe05.ey.com
 *
 */
public enum VisitEventType {
	ENTRANCE(10),
	LEAVE(20);

	private int processingOrder;

	private VisitEventType(int processingOrder) {
		this.processingOrder = processingOrder;
	}

	public int getProcessingOrder() {
		return processingOrder;
	}

}
