import src.ClosePointUtility;
import src.impl.ClostPointUtilityImpl;

public class ClosePoints {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		try {
			ClosePointUtility closepoints = new ClostPointUtilityImpl();
			closepoints.closePoints(args[0], Integer.parseInt(args[1]));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
