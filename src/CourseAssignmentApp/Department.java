package CourseAssignmentApp;

import java.util.*;

public class Department {
	
	HashMap<String, ArrayList<Course>> courseMap; // * getters
	ArrayList<Professor> listOfProfs;

	public Department(ArrayList<Professor> listOfProfs) {
		
		courseMap = new HashMap<String, ArrayList<Course>>();
		this.listOfProfs = listOfProfs;
	}
}
