package src.impl;

public class Logger {
	
	private final static java.util.logging.Logger logger = java.util.logging.Logger
			.getLogger(ClosePointFinderImpl.class.getName());
	
	
	public static java.util.logging.Logger getLogger(){
		return logger;
	}

}
