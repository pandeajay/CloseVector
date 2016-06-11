package src;

import java.io.IOException;


//interface for result writing
public interface ResultWriter {
	void flushResults(String fileName, String result) throws IOException;
}
