package src;

import src.model.Point;

public interface PointUtility {

	//parse and get point / vector
	Point getPoint(String pointRecord);

	//get distance between 2 points
	double getDistance(Point point1, Point point2);

}

