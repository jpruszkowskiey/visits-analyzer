package com.ey.va;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.ey.va.analyzer.PeriodWithCount;
import com.ey.va.analyzer.VisitAnalyzer;
import com.ey.va.model.Visit;
import com.ey.va.reader.CsvVisitReader;
import com.ey.va.reader.VisitReader;

/**
 * 
 * Class representing main class of the program
 * 
 * @author jacek.pruszkowski@xe05.ey.com
 *
 */
public class Program {

	private final VisitReader visitReader;
	private final VisitAnalyzer visitAnalyzer;
	private final ProgramParameters parameters;

	/**
	 * 
	 * @param args
	 *            command line java program arguments
	 * @throws FileNotFoundException
	 *             thrown if input file does not exists
	 */
	public Program(String... args) throws FileNotFoundException {
		this.parameters = new ProgramParameters(args);

		this.visitReader = new CsvVisitReader(
				new FileInputStream(parameters.getInputPath()),
				parameters.getCsvSeparator());

		this.visitAnalyzer = new VisitAnalyzer();
	}

	/**
	 * constructor that allows to change the implementation of the main
	 * application components
	 * 
	 * @param visitReader
	 * @param visitAnalyzer
	 * @param parameters
	 */
	public Program(VisitReader visitReader, VisitAnalyzer visitAnalyzer, ProgramParameters parameters) {
		this.visitReader = visitReader;
		this.visitAnalyzer = visitAnalyzer;
		this.parameters = parameters;
	}

	/**
	 * Main method of the program. Takes command line params: <br>
	 * --in /some/path/file.txt <br>
	 * --separator ; <br>
	 * <br>
	 * e.g: <br>
	 * java -jar visits-analyzer-1.0.jar --in /some/path/file.txt --separator ;
	 * <br>
	 * 
	 * @param args
	 *            command line java program arguments
	 */
	public static void main(String... args) {
		try {
			new Program(args).run();
		} catch (Exception e) {
			System.err.println(e.getMessage());
			System.err.println();
			e.printStackTrace();
		}
	}

	/**
	 * runs the whole input data analyzing process
	 * 
	 * @throws FileNotFoundException
	 *             when input file does not exist
	 * @throws IOException
	 *             when IO error occures
	 */
	public void run() throws FileNotFoundException, IOException {

		List<Visit> visits = loadAllVisits();

		PeriodWithCount result = visitAnalyzer.getMostCrowdedPeriod(visits);

		String resultStr =
				result.getStart().format(DateTimeFormatter.ISO_LOCAL_TIME) + " - "
						+ result.getEnd().format(DateTimeFormatter.ISO_LOCAL_TIME) + "; "
						+ result.getCount();

		System.out.println(resultStr);
	}

	private List<Visit> loadAllVisits() throws IOException {
		try (VisitReader reader = this.visitReader) {
			List<Visit> visits = new ArrayList<>();
			Visit visit = null;
			while ((visit = reader.readVisit()) != null) {
				visits.add(visit);
			}
			return visits;
		}
	}

}
