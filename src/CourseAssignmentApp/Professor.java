package CourseAssignmentApp;

import java.util.ArrayList;
import java.util.List;

public class Professor implements Comparable<Professor>{
	
	private int id; // 4 digits
	private String name;
	private float seniorityLevel; // 0.00 - 60.00
	private String requestFile; // *shouldn't be here
	private int assignedGroupNum;
	
	private ArrayList<String> disciplines;
	private ArrayList<Course> assignedCourses;
	
	// Getters
	public int getId() { return id; }
	public String getName() { return name; }
	public String getRequestFile() { return requestFile; }
	public int getAssignedGroupNum() { return assignedGroupNum; }
	public ArrayList<String> getDisciplines() { return disciplines; }  // *should be Set<String>
	public ArrayList<Course> getAssignedCourses() { return assignedCourses; }
	
	public void increaseAssignedGroupNum(int val) { this.assignedGroupNum += val; }
	
	// Constructor
	public Professor(int id, String name, float seniorityLevel, ArrayList<String> disciplines) {
		
		this.id = id;
		this.name = name;
		this.seniorityLevel = seniorityLevel;
		this.requestFile = "C:\\Users\\b2uty\\eclipse-workspace\\DS_FinalProject\\bin\\Data\\" + id + "_request.txt";
		this.assignedGroupNum = 0;
		this.disciplines = disciplines;
		this.assignedCourses = new ArrayList<Course>();
	}

	@Override
	public int compareTo(Professor other) { // *should be ascending order as it was
		
		// Descending order
		
		// Compare seniority level
		if (this.seniorityLevel < other.seniorityLevel) { return 1; }
		else if (this.seniorityLevel > other.seniorityLevel) { return -1; }
		
		// If they have the same seniority level,
		// Compare id (= hiring date)
		else { 
			
			if (this.id < other.id) { return 1;	}
			else if (this.id > other.id) { return -1; }
			
			else { return 0; }
		}
	}
	
	public void printAssignedCourses() {
		
		System.out.println(this.name + "'s assigned courses :");
		
		for (Course c : this.assignedCourses) {
			
			System.out.println(" - " + c.getId() + " / Language: " + c.getLanguage() + " / Groups: " + c.getNumOfGroups());
		}
		
		System.out.println("===================");
	}
}
