package src.impl;

import java.util.StringTokenizer;

import src.PointUtility;
import src.model.Point;

public class PointUtilityImpl implements PointUtility {

	//parse and get point
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
			Logger.getLogger().severe("Invalid record : " + pointRecord + " . This record will be ignored.");
			return null;
		}
	}
	
	@Override
	public double getDistance(Point point1, Point point2) {
		double dist = 0.0;
		try {
			dist = Math.sqrt(Math.pow(point1.getFirstCoordinate() - point2.getFirstCoordinate(), 2)
					+ Math.pow(point1.getSecondCoordinate() - point2.getSecondCoordinate(), 2)
					+ Math.pow(point1.getThirdCoordinate() - point2.getThirdCoordinate(), 2)
					+ Math.pow(point1.getFourthCoordinate() - point2.getFourthCoordinate(), 2));
		} catch (Exception ex) {

		}

		return dist;
	}

}
