package src.model;

public class Point {

	String name;
	int firstCoordinate;
	int secondCoordinate;
	int thirdCoordinate;
	int fourthCoordinate;
	
	public Point(String name, int firstCoordinate, int secondCoordinate, int thirdCoordinate, int fourthCoordinate) {
		super();
		this.name = name;
		this.firstCoordinate = firstCoordinate;
		this.secondCoordinate = secondCoordinate;
		this.thirdCoordinate = thirdCoordinate;
		this.fourthCoordinate = fourthCoordinate;
	}
	
	public Point() {
		super();
		this.name = "";
		this.firstCoordinate = 0;
		this.secondCoordinate = 0;
		this.thirdCoordinate = 0;
		this.fourthCoordinate = 0;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getFirstCoordinate() {
		return firstCoordinate;
	}
	public void setFirstCoordinate(int firstCoordinate) {
		this.firstCoordinate = firstCoordinate;
	}
	public int getSecondCoordinate() {
		return secondCoordinate;
	}
	public void setSecondCoordinate(int secondCoordinate) {
		this.secondCoordinate = secondCoordinate;
	}
	public int getThirdCoordinate() {
		return thirdCoordinate;
	}
	public void setThirdCoordinate(int thirdCoordinate) {
		this.thirdCoordinate = thirdCoordinate;
	}
	public int getFourthCoordinate() {
		return fourthCoordinate;
	}
	public void setFourthCoordinate(int fourthCoordinate) {
		this.fourthCoordinate = fourthCoordinate;
	}
}
