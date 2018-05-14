package com.ey.va;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProgramTest {

	@Test
	public void testProgram_shouldPrintsResultWhenOK_customSeparator() throws IOException {
		List<String> args = new ArrayList<>();
		args.add("--in");
		args.add(getFilePath("test_csv.txt"));
		args.add("--separator");
		args.add("|");

		ByteArrayOutputStream bo = new ByteArrayOutputStream();
		System.setOut(new PrintStream(bo));

		Program.main(args.toArray(new String[0]));

		String allWritten = new String(bo.toByteArray());
		Assert.assertEquals("11:10:00 - 14:20:00; 2", allWritten.trim());
	}

	@Test
	public void testProgram_shouldPrintsResultWhenOK_defaultSeparator() throws IOException {
		List<String> args = new ArrayList<>();
		args.add("--in");
		args.add(getFilePath("test_csv_big.txt"));

		ByteArrayOutputStream bo = new ByteArrayOutputStream();
		System.setOut(new PrintStream(bo));

		Program.main(args.toArray(new String[0]));

		String allWritten = new String(bo.toByteArray());

		System.out.println();
		Assert.assertEquals("11:10:00 - 11:10:00; 17", allWritten.trim());
	}

	@Test
	public void testProgram_printsErrorWhenError() throws IOException {
		List<String> args = new ArrayList<>();
		args.add("--in");
		args.add(getFilePath("test_csv.txt"));
		args.add("--separator");
		args.add(",");

		ByteArrayOutputStream bo = new ByteArrayOutputStream();
		System.setErr(new PrintStream(bo));

		Program.main(args.toArray(new String[0]));

		String allWritten = new String(bo.toByteArray());

		Assert.assertTrue(allWritten.startsWith("Invalid input line: 10:15|14:20"));
	}

	public String getFilePath(String resource) throws IOException {
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource(resource).getFile());
		return file.getAbsolutePath();
	}

}
