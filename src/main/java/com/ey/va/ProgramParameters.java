package com.ey.va;

/**
 * 
 * Class parsed form of program command line parameters.<br>
 * At this point parses two params:<br>
 * --in INPUT_PATH<br>
 * --separator CSV_SEPARATOR<br>
 * 
 * @author jacek.pruszkowski@xe05.ey.com
 *
 */
public class ProgramParameters {
	/**
	 * default CSV separator
	 */
	public static final String DEFAULT_CSV_SEPARATOR = ",";
	/**
	 * name of the parameter used to provide input file path
	 */
	public static final String INPUT_PATH_PARAM = "in";
	/**
	 * name of the parameter used to provide input file path
	 */
	public static final String CSV_SEPARATOR_PARAM = "separator";

	private String inputPath;
	private String csvSeparator;

	/**
	 * 
	 * @param args
	 *            command line program parameters to parse
	 */
	public ProgramParameters(String[] args) {
		for (int i = 0; i < args.length; i++) {
			String s = args[i];
			if (s.startsWith("--") && s.length() > 2) {
				if (i < args.length - 1) {
					setParamater(s.substring(2), args[i + 1]);
					++i;
				} else {
					throw new IllegalArgumentException("Missing value for parameter: " + args[i]);
				}
			} else if (s.startsWith("-") && s.length() > 1) {
				s.substring(1).chars().forEach((c) -> setFlag((char) c));
			} else {
				throw new IllegalArgumentException("Invalid argumet: " + s);
			}
		}
	}

	private void setFlag(char flag) {
		// not used at this point
	}

	private void setParamater(String key, String val) {
		if (INPUT_PATH_PARAM.equals(key)) {
			inputPath = val.trim();
		}
		if (CSV_SEPARATOR_PARAM.equals(key)) {
			csvSeparator = val.trim();
		}
	}

	public String getInputPath() {
		if (inputPath == null) {
			throw new IllegalStateException("Missing inputPath parameter");
		}
		return inputPath;
	}

	public String getCsvSeparator() {
		if (csvSeparator == null) {
			csvSeparator = DEFAULT_CSV_SEPARATOR;
		}
		return csvSeparator;
	}

}
