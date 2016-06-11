package test;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import src.ClosePointUtility;
import src.impl.ClostPointUtilityImpl;
import src.FileUtility;
import src.InvalidFileException;
import src.impl.FileUtilityImpl;

public class FileAndRecordsTest {
	String newLine = System.getProperty("line.separator");
	FileUtilityImpl fileUtility = new FileUtilityImpl();



	@Before
	public void setUp() throws Exception {

	}

	@After
	public void tearDown() throws Exception {

	}

	@Test(expected = InvalidFileException.class)
	public void testFile() throws InvalidFileException {
		this.fileUtility.validateFile("test1.txt");		
	}
	@Test
	public void testRecordFromInputFile() {
		assertFalse(this.fileUtility.validateRecord("vector")); 
		assertFalse(this.fileUtility.validateRecord("vector-"));
		assertTrue(this.fileUtility.validateRecord("Vector_Id_01       2              4              5              6")); 
		assertTrue(this.fileUtility.validateRecord("Vector_Id_")); 
		assertTrue(this.fileUtility.validateRecord("Vector_Id_")); 
	}

}
