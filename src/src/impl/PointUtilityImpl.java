package src.impl;

import java.util.StringTokenizer;

import src.PointUtility;
import src.model.Point;

public class PointUtilityImpl implements PointUtility{
	
	@Override
	public Point getPoint(String pointRecord) {
		try {
			String fieldSeparator = " ";

			// parse data from vector
			StringTokenizer st = new StringTokenizer(pointRecord);
			Point point = new Point();
			String[] data = new String[5];
			int i = 0;
			while (st.hasMoreTokens()) {
				data[i] = st.nextToken(fieldSeparator);
				i++;
			}
			// set point data
			point.setName(data[0]);
			point.setFirstCoordinate(Integer.parseInt(data[1]));
			point.setSecondCoordinate(Integer.parseInt(data[2]));
			point.setThirdCoordinate(Integer.parseInt(data[3]));
			point.setFourthCoordinate(Integer.parseInt(data[4]));

			return point;
		} catch (Exception ex) {
			System.out.println("Invalid record : " + pointRecord + " . This record will be ignored.");
			return null;
		}
	}

}
