import src.ClosePointUtility;
import src.impl.ClostPointUtilityImpl;
import src.impl.Logger;

public class ClosePoints {

	public static void main(String[] args) {
		
		//validate inputs
		if(args.length != 2){
			Logger.getLogger().severe("Insufficient or over number of inputs " + args);
			return ;
		}
		
		//process
		try {
			ClosePointUtility closepoints = new ClostPointUtilityImpl();
			closepoints.closePoints(args[0], Integer.parseInt(args[1]));
		} catch (Exception e) {
			Logger.getLogger().severe("Exception while processing file " + args[0]);			
		}
	}

}
