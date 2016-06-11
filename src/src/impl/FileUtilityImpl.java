package src.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Paths;

import src.FileUtility;
import src.InvalidFileException;

public class FileUtilityImpl implements FileUtility {
	String newLine = System.getProperty("line.separator");

	@Override
	public void validateFile(String fileName) throws InvalidFileException {
		// TODO Auto-generated method stub
		File file = new File(fileName);
		
		if(file.isDirectory() == true){
			throw new InvalidFileException(fileName);
		}
		if (file.exists() == false ){
			throw new InvalidFileException(fileName);
		}
		
		if( file.isFile() == false){
			throw new InvalidFileException(fileName);
		}
		if(file.length() == 0){
			throw new InvalidFileException(fileName);
		}	
		

	}

	@Override
	public boolean validateRecord(String record) {
		//add basic record validations
		try{
			if (record != null) {
				if (!record.startsWith("Vector_Id_")) {					
					return false;
				}

				return true;
			}
			
		}catch(Exception ex){
			return false;
		}
		return false;		
	}
}
