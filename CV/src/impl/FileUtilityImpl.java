package src.impl;

import java.io.File;
import exception.InvalidFileException;
import src.FileUtility;


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
				
				//TO-DO: add more validations and delay integer parsing to avoid duplicate

				return true;
			}
			
		}catch(Exception ex){
			return false;
		}
		return false;		
	}
}
