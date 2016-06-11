package src.impl;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import src.ResultWriter;

public class ResultWriterImpl implements ResultWriter {

	// write results to a file
	@Override
	public synchronized void flushResults(String fileName, String result) throws IOException {

		File file = new File(fileName.substring(0, fileName.lastIndexOf('.')).concat("output.txt"));
		FileWriter writer = new FileWriter(file, true);
		writer.append(result);
		writer.flush();
		writer.close();
	}
}
