package src;

import exception.InvalidFileException;

public interface FileUtility {

	//validate a file
	void validateFile(String file) throws InvalidFileException;
	
	// validate record / point
	boolean validateRecord(String file) ;

}
