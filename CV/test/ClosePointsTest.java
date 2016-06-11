/**
 * 
 */
package test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import junit.framework.*;

import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import src.ClosePointFinder;
import src.impl.ClosePointFinderImpl;

/**
 * @author apande
 *
 */
public class ClosePointsTest extends TestCase {
	String newLine = System.getProperty("line.separator");
	ClosePointFinder closepoints = new ClosePointFinderImpl();
	List<String> records = new ArrayList<String>();

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {

		File file = new File("test.txt");
		file.delete();
		file.createNewFile();
		File output = new File("testoutput.txt");
		output.delete();
		// creates a FileWriter Object
		FileWriter writer = new FileWriter(file);
		records.add("Vector_Id_01       2              4              5              6" + newLine);
		records.add("Vector_Id_02       1              3              2              7" + newLine);
		records.add("Vector_Id_03       0              2              2              1" + newLine);
		records.add("Vector_Id_04       1              1              1              1" + newLine);
		records.add("Vector_Id_05       1              1              1              1" + newLine);
		records.add("Vector_Id_06       1              2              1              1" + newLine);
		records.add("Vector_Id_07       1              1              2              1" + newLine);
		records.add("Vector_Id_08       1              1              1              2" + newLine);
		records.add("Vector_Id_09       1              1              2              1" + newLine);
		records.add("Vector_Id_10       2              2              1              1" + newLine);

		String sample = "";
		for (String record : this.records) {
			sample += record;
		}
		writer.append(sample);
		writer.close();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		File file = new File("testoutput.txt");
		file.delete();
		file = new File("test.txt");
		file.delete();
		file = null;
		records.clear();
	}

	@Test
	public void testOutputFile() throws Exception {
		closepoints.closePoints("test.txt", 3);
		
		// verify if result file is generated
		File file = new File("testoutput.txt");
		System.out.println("file.exists() " + file.exists());
		assertTrue(file.exists());
	}

	@Test
	public void testNumberOfRecords() throws Exception {
		closepoints.closePoints("test.txt", 3);

		// verify if result file is generated
		BufferedReader br = new BufferedReader(new FileReader("testoutput.txt"));
		String line = br.readLine();
		int count = 0;
		while (line != null) {
			count++;
			line = br.readLine();
		}
		assertEquals(count, this.records.size() / 2);
	}

	@Test
	public void testResult() throws Exception {
		closepoints.closePoints("test.txt", 3);

		// verify if result file is generated
		BufferedReader br = new BufferedReader(new FileReader("testoutput.txt"));
		List<String> results = new ArrayList<String>();
		String line = br.readLine();
		
		while (line != null) {
			results.add(line);
			line = br.readLine();
		}
		
		//verify that all records from first half are collected for in second half
		for (int i = 0; i < 5; i++) {
			boolean found = false;
			for (String result : results) {
				System.out.println("records.get(i).substring(0, records.get(i).indexOf(' '))" +
								records.get(i).substring(0, records.get(i).indexOf(' ')));
				System.out.println("result.substring(0, result.indexOf(' '))" +
						result.substring(4, result.indexOf(':') -1));
				if (records.get(i).substring(0, records.get(i).indexOf(' '))
						.equals(result.substring(4, result.indexOf(':') -1))) {
					found = true;
					break;
				}
			}
			assertTrue(found);
		}
	}

}
