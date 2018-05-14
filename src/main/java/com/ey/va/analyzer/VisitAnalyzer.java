package com.ey.va.analyzer;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.ey.va.model.Visit;
import com.ey.va.model.VisitEvent;
import com.ey.va.model.VisitEventType;

/**
 * 
 * Class containing the logic required to analyze visits data
 * 
 * @author jacek.pruszkowski@xe05.ey.com
 *
 */
public class VisitAnalyzer {

	private static final VisitEventComparator visitEventComparator = new VisitEventComparator();

	/**
	 * 
	 * Analyzes the collection of visits and based on that computes the period
	 * with the maximum visitors count
	 * 
	 * @param visits
	 *            collection with visits
	 * @return PeriodWithCount instance representing the result
	 */
	public PeriodWithCount getMostCrowdedPeriod(Collection<Visit> visits) {
		if (visits.size() == 0) {
			throw new IllegalArgumentException("visits collection is empty");
		}

		final List<VisitEvent> events = createEvents(visits);
		VisitEvent first = events.get(0);

		LocalTime currentTime = first.getEventTime();
		int currentCount = 0;

		LocalTime start = first.getEventTime();
		LocalTime end = first.getEventTime();
		int maxCount = 0;

		for (VisitEvent event : events) {

			boolean currentCountEqMax = currentCount == maxCount;

			currentTime = event.getEventTime();

			if (VisitEventType.ENTRANCE.equals(event.getType())) {
				++currentCount;
			}

			if (VisitEventType.LEAVE.equals(event.getType())) {
				--currentCount;
			}

			if (currentCount >= maxCount) {
				maxCount = currentCount;
				start = currentTime;
				end = currentTime;
			}

			if (currentCountEqMax && currentCount < maxCount) {
				end = currentTime;
			}
		}

		return new PeriodWithCount(start, end, maxCount);
	}

	private List<VisitEvent> createEvents(Collection<Visit> visits) {
		final List<VisitEvent> events = new ArrayList<VisitEvent>(visits.size() * 2);
		visits.forEach((v) -> {
			events.add(new VisitEvent(v.getEntranceTime(), VisitEventType.ENTRANCE));
			events.add(new VisitEvent(v.getLeaveTime(), VisitEventType.LEAVE));
		});
		Collections.sort(events, visitEventComparator);
		return events;
	}

	private static class VisitEventComparator implements Comparator<VisitEvent> {

		@Override
		public int compare(VisitEvent first, VisitEvent second) {
			int timeCompare = first.getEventTime().compareTo(second.getEventTime());
			if (timeCompare == 0) {
				return first.getType().getProcessingOrder() - second.getType().getProcessingOrder();
			}
			return timeCompare;
		}
	}

}
