package src;

import exception.InvalidFileException;

public interface FileUtility {

	void validateFile(String file) throws InvalidFileException;
	boolean validateRecord(String file) ;

}
