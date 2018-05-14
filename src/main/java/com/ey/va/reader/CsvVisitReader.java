package com.ey.va.reader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

import com.ey.va.model.Visit;

/**
 * 
 * Implementation of {@link com.ey.va.reader.VisitReader} that read visits from
 * stream that represents CSV data content
 * 
 * @author jacek.pruszkowski@xe05.ey.com
 *
 */
public final class CsvVisitReader implements VisitReader {

	private final BufferedReader reader;
	private final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_TIME;
	private final String separatorRegex;

	/**
	 * 
	 * @param inputStream
	 *            input stream containing CSV data content
	 * @param separator
	 *            separator used in CSV data
	 */
	public CsvVisitReader(InputStream inputStream, String separator) {
		this.separatorRegex = Pattern.quote(separator);
		this.reader = new BufferedReader(new InputStreamReader(inputStream));
	}

	public Visit readVisit() throws IOException {
		String line = readNextNotBlankLine();

		if (line == null) {
			return null;
		}

		String[] split = line.trim().split(separatorRegex);

		if (split.length != 2) {
			throw new IllegalArgumentException("Invalid input line: " + line);
		}

		LocalTime entrance = LocalTime.parse(split[0], formatter);
		LocalTime leave = LocalTime.parse(split[1], formatter);

		return new Visit(entrance, leave);
	}

	public void close() throws IOException {
		reader.close();
	}

	private String readNextNotBlankLine() throws IOException {
		String line = null;
		while (true) {
			line = reader.readLine();
			if (line == null) {
				return null;
			}
			line = line.trim();
			if (line.length() == 0) {
				continue;
			} else {
				break;
			}
		}
		return line;

	}
}
