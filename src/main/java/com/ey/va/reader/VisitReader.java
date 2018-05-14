package com.ey.va.reader;

import java.io.Closeable;
import java.io.IOException;

import com.ey.va.model.Visit;

/**
 * 
 * Interface for the class that is able to
 * read visits from some place  
 * 
 * @author jacek.pruszkowski@xe05.ey.com
 *
 */
public interface VisitReader extends Closeable {

	/**
	 * Method should return next visit if visit exists.
	 *  
	 * @return Visit instance or null when there is no more visits available 
	 * @throws IOException may be thrown when some IO operation fails 
	 */
	Visit readVisit() throws IOException;

}
