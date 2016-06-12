import java.net.URL;

import src.ClosePointFinder;
import src.impl.ClosePointFinderImpl;
import src.impl.Logger;

public class ClosePoints {

	public static void main(String[] args) {
		System.out.println("Usuage : Needs 2 inputs. First file path and levels of close points needed.");
		
		//validate inputs
		if(args.length != 2){
			Logger.getLogger().severe("Insufficient or over number of inputs " + args);
			return ;
		}
		
		//process
		try {
			ClosePointFinder closepoints = new ClosePointFinderImpl();
			closepoints.findClosePoints(args[0], Integer.parseInt(args[1]));
		} catch (Exception e) {
			Logger.getLogger().severe("Exception while processing file " + args[0]);			
		}
	}

}
