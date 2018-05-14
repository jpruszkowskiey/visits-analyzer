package com.ey.va.reader;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.ey.va.model.Visit;

public class CsvVisitReaderTest {

	private final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_TIME;

	private static final String CORRECT_CONTENT =
			"\n\n   10:15,14:20    \n\n\n"
					+ "   11:10,15:22    \n";

	private static final String INCORRECT_CONTENT =
			"\n\n   10:15|14:20    \n\n\n"
					+ "   11:10|15:22    \n";

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Before
	public void init() {

	}

	@Test
	public void testReadVisit_shouldThrowException_whenIncorrectContent() throws IOException {
		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage("Invalid input line: 10:15|14:20");

		InputStream inputStream = new ByteArrayInputStream(INCORRECT_CONTENT.getBytes());

		CsvVisitReader csvVisitReader = new CsvVisitReader(inputStream, ",");

		csvVisitReader.readVisit();

		Assert.fail();
		csvVisitReader.close();
	}

	@Test
	public void testReadVisit_shouldReturnCorrectValues_whenCorrectContent() throws IOException {
		InputStream inputStream = new ByteArrayInputStream(CORRECT_CONTENT.getBytes());

		CsvVisitReader csvVisitReader = new CsvVisitReader(inputStream, ",");

		Visit visit = null;
		visit = csvVisitReader.readVisit();
		Assert.assertEquals(LocalTime.parse("10:15", formatter), visit.getEntranceTime());
		Assert.assertEquals(LocalTime.parse("14:20", formatter), visit.getLeaveTime());

		visit = csvVisitReader.readVisit();
		Assert.assertEquals(LocalTime.parse("11:10", formatter), visit.getEntranceTime());
		Assert.assertEquals(LocalTime.parse("15:22", formatter), visit.getLeaveTime());

		visit = csvVisitReader.readVisit();
		Assert.assertNull(visit);

		csvVisitReader.close();
	}

}
