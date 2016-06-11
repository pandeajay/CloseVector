package test;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import src.InvalidFileException;
import src.impl.FileUtilityImpl;

/**
 * 
 */

/**
 * @author apande
 *
 */
public class FileUtility {

	FileUtilityImpl fileUtility = new FileUtilityImpl();

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		File testFile = new File("tetingFile.txt");
		testFile.createNewFile();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		File testFile = new File("tetingFile.txt");
		testFile.delete();
	}

	@Test(expected = InvalidFileException.class)
	public void testNullFileName() throws InvalidFileException {
		fileUtility.validateFile("");
	}

	@Test(expected = InvalidFileException.class)
	public void testEmptyFile() throws InvalidFileException {
		fileUtility.validateFile("tetingFile.txt");
	}

}
