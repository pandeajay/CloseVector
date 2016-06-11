package src.model;

public class Result {
	Double dist ;
	String name;
	
	public Double getDist() {
		return dist;
	}
	public void setDist(Double dist) {
		this.dist = dist;
	}
	public Result() {
		super();
	}
	public Result(Double dist, String name) {
		super();
		this.dist = dist;
		this.name = name;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	

}
