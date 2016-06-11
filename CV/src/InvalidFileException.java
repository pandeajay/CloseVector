package src;

public class InvalidFileException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public InvalidFileException (String file){
		System.out.println("Invalid file or file does not exits or an empty file: " + file);		
	};
}
