package CourseAssignmentApp;

public class Course {
	
	private String id; // ex) 420A02AS
	private String title; // ex) Linear Algebra
	private String discipline; // ex) AI00
	private String language; // ex) AN
	
	private int numOfHours;
	private int numOfGroups; // numOfGroups available
	
	// Getters
	public String getId() { return id; }
	public String getTitle() { return title; }
	public String getDiscipline() { return discipline; }
	public String getLanguage() { return language; }
	public int getNumOfHours() { return numOfHours; }
	public int getNumOfGroups() { return numOfGroups; }
	
	// Setter
	public void setNumOfGroups(int newNumOfGroups) { this.numOfGroups = newNumOfGroups; }
	
	// Constructor
	public Course(String id, String title, String discipline, String language, int numOfHours, int numOfGroups) {
		
		this.id = id;
		this.title = title;
		this.discipline = discipline;
		this.language = language;
		
		this.numOfHours = numOfHours;
		this.numOfGroups = numOfGroups;
	}
	
	// Copy Constructor
	public Course(Course other) {
		
		this.id = other.id;
		this.title = other.title;
		this.discipline = other.discipline;
		this.language = other.language;
		
		this.numOfHours = other.numOfHours;
		this.numOfGroups = other.numOfGroups;
	}
	
	public int getHoursPerWeek() { return this.numOfHours/15; }
	
	public void increaseNumOfGroups(int num) { this.numOfGroups += num; }
	public void decreaseNumOfGroups(int num) { this.numOfGroups -= num; }
}
