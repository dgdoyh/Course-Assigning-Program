package CourseAssignmentApp;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

public class Application {

	public static void main(String[] args) {
		
		ArrayList<Professor> listOfProfs = getProfListFromFile("C:\\Users\\b2uty\\eclipse-workspace\\DS_FinalProject\\src\\Data\\professors_edit.txt");		
		ArrayList<Course> listOfCourses = getCourseListFromFile("C:\\Users\\b2uty\\eclipse-workspace\\DS_FinalProject\\src\\Data\\ListeDeCours_edit.txt");
		
		Department dpt = new Department(listOfProfs);

		makeCourseMap(dpt, listOfCourses);
		
		assignCourses(listOfProfs, dpt);
	
		createUnassignedCourseFile(listOfCourses);
	}

	private static ArrayList<String> readFile(String filePath) {
		
		ArrayList<String> lines = new ArrayList<String>();
			
		try {	
			
		    BufferedReader reader = new BufferedReader(new FileReader(filePath));
		    
            String line;

            while ((line = reader.readLine()) != null) {
            	
            	           	
            	lines.add(line);
            }
            
            reader.close();
	            		    
		} catch (IOException e) {

            System.out.println("Cannot read the file.");
            e.printStackTrace();
        }
	
		return lines;
	}
	
	private static ArrayList<Professor> getProfListFromFile(String filePath) {
		
		ArrayList<Professor> profs = new ArrayList<>();
		ArrayList<String> profData = readFile(filePath);
		
		for (String line : profData) {
			
			String[] sData = getSplitedProfData(line);
			
	    	// Create professor
	    	Professor newProf = new Professor(getProfId(sData), getProfName(sData), getProfSeniorityLevel(sData), getProfDisciplines(sData));

	    	// Add the professor into the list
	    	profs.add(newProf);
		}
		
	    // Sort professor list by seniority level
	    Collections.sort(profs); // *change the order here
		    
		return profs;
	}
	
	private static ArrayList<Course> getCourseListFromFile(String filePath) {
		
		ArrayList<Course> courses = new ArrayList<Course>();
		ArrayList<String> courseData = readFile(filePath);
		
		for (String line : courseData) {
		    
	    	// Split data
	    	String[] sData = getSplitedCourseData(line);
	    	
	    	// Create course
	    	Course newCourse = new Course(getCourseId(sData), getCourseTitle(sData), getCourseDiscipline(sData), getCourseLanguage(sData), getCourseNumOfHours(sData), getCourseNumOfGroups(sData));
	    	// dpt.coursemap.put
	    	courses.add(newCourse);
		}  
		
		return courses;
	}
	
 	private static String[] getSplitedProfData(String data) {
		
		// special request: remove "|" for disciplines
		data = data.replace("|", "");
		data = data.replace(" ", "");
		
		String[] splitedData = data.split(":");
		
		return splitedData;
	}
	private static String[] getSplitedCourseData(String data) { return data.split(","); }
 	private static String[] getSplitedRequestData(String data) { return data.split(":"); }
	
 	// Professor Data
	private static int getProfId(String[] sData) { return Integer.parseInt(sData[0]); }
	private static String getProfName(String[] sData) { return sData[1]; }
	private static float getProfSeniorityLevel(String[] sData) { return Float.parseFloat(sData[2]); }
	private static ArrayList<String> getProfDisciplines(String[] sData) {
			
		String[] data= sData[sData.length-1].split(",");
		ArrayList<String> disciplines = new ArrayList<String>(Arrays.asList(data));
		
		return disciplines;
	}

	// Course Data
	private static String getCourseId(String[] sData) { return sData[1]; }
	private static String getCourseTitle(String[] sData) { return sData[2]; }
	private static String getCourseDiscipline(String[] sData) { return sData[0]; }
	private static String getCourseLanguage(String[] sData) { return sData[4]; }
	private static int getCourseNumOfHours(String[] sData) { return Integer.parseInt(sData[3]); }
	private static int getCourseNumOfGroups(String[] sData) { return Integer.parseInt(sData[5]); }
	
	// Request Data
	private static String getReqCourseId(String[] sData) { return sData[0]; }
	private static String getReqCourseDiscipline(String[] sData) { return sData[2]; }
	private static String getReqCourseLanguage(String[] sData) { return sData[3]; }
	private static int getReqCourseNumOfGroups(String[] sData) { 
		
		int numOfGroups = 0;
		
		try {
			
			numOfGroups = Integer.parseInt(sData[4]);
			
		} catch (NumberFormatException e) {
			
			// If it's not integer type, set the numOfGroups as 1
			System.out.println("It is not a valid group number. It will be set it as 1 (default).");
			numOfGroups = 1;
		}  
		
		return numOfGroups;
	}
	
	private static void makeCourseMap(Department dpt, ArrayList<Course> listOfCourses) {
		
		for (Course newCourse : listOfCourses) {
			
			ArrayList<Course> courseList = new ArrayList<Course>();
			
			// If the new course id is already in the course map
			if (dpt.courseMap.containsKey(newCourse.getId())) {
				
				// Copy the all courses with the same id from courseMap into courseList
				courseList = new ArrayList<Course>(dpt.courseMap.get(newCourse.getId()));
				
				// Check all the courses with the same id
				for (Course existingCourse : dpt.courseMap.get(newCourse.getId())) {
					
					// If the languages are the same, just increase the numOfGroups of the existing course
					if (existingCourse.getLanguage().equals(newCourse.getLanguage())) {
						
						existingCourse.increaseNumOfGroups(newCourse.getNumOfGroups());
						break;						
					}
				}
			
			} else { // If the new course id is not in the course map, add it to the list
				
				courseList.add(newCourse);
			}
			
			// Update courseMap with the courseList
			dpt.courseMap.put(newCourse.getId(), courseList);
		}
	}

	private static boolean compareLanguage(Course course, String reqLanguage) {
		
		// This compares course's language and requestedLanguage
		
		if (course.getLanguage().equals("AN") && reqLanguage.equals("Anglais / English")) { return true; }
		else if (course.getLanguage().equals("FR") && reqLanguage.equals("Fran�ais / French")) { return true; }
		else if (course.getLanguage().equals("AN") && reqLanguage.equals("Les deux / Both")) { return true; }
		else if (course.getLanguage().equals("FR") && reqLanguage.equals("Les deux / Both")) { return true; }
		else { return false; }
	}
	
	private static int getSameLanguageCourseIndex(ArrayList<Course> courses, String language) {
		
		for (int i = 0; i < courses.size(); i++) {
			
			if (compareLanguage(courses.get(i), language)) {
				
				return i;
			}
		}

		return -1;
	}
	
	private static void assignCourses(ArrayList<Professor> profs, Department dpt) {
		
		for (Professor p : profs) {
			
			System.out.println("< Start processing request of " + p.getName() + " >");
			
			ArrayList<String> reqData = readFile(p.getRequestFile());
			
			String nullString = "NaN:NaN:NaN:NaN:NaN";
			
			int numOfHours = 0; // Remaining hours per week
			int assignedHours = 0;
			
			// Set numOfHours
			try { 
				
				numOfHours = Integer.parseInt(reqData.get(0)); // First line of request file is numOfHours				
				
			} catch (NumberFormatException e) {
				
				// If the numOfHours data is not integer type, set it as 24
				System.out.println("It is not a valid hour. Number of hours per week will be set as 24 (default).");
				numOfHours = 24;
			} 
			
			// Update request_final file
			String r_filePath = "C:\\Users\\b2uty\\eclipse-workspace\\DS_FinalProject\\bin\\Data\\Results\\"+ p.getId() + "_request_final.csv";
	        
	        try {
			
				File file = new File(r_filePath); 
		        
		        if (!file.exists()) { 
		        	
		            file.createNewFile();
		        }
		        
	        	BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
	        	
	            writer.write(reqData.get(0));
	            writer.newLine();

	            writer.flush();
	            writer.close();
	        }
	        
	        catch (IOException except) {
	            except.printStackTrace();
	        }
			
			System.out.println("numOfHours: " + numOfHours);
			
			// Process the request line by line
			for (int i = 1; i < reqData.size(); i++) {
				
				int assignedGroupNum = 0;
				
				if (reqData.get(i).equals(nullString)) {
					
					continue;
					
				} else { // Professor has remaining requests

					// Split the current line of data
					String[] sData = getSplitedRequestData(reqData.get(i));

					if (!dpt.courseMap.containsKey(getReqCourseId(sData))) {
						
						System.out.printf("%s cannot have %s (%s). Reason: the course is not in the course map\n", p.getName(), getReqCourseId(sData), getReqCourseLanguage(sData));
						
					} else { // The requested course is in the course map
						
						ArrayList<Course> courses = dpt.courseMap.get(getReqCourseId(sData));
						
						// Check if there's a course in the requested language
						int index = getSameLanguageCourseIndex(courses, getReqCourseLanguage(sData));
					
						if (index < 0) {
								
							System.out.printf("%s cannot have %s (%s). Reason: language\n", p.getName(), getReqCourseId(sData), getReqCourseLanguage(sData));
								
						} else { // Languages matched
							
							Course currCourse = courses.get(index);
							
							// Check if the professor has minimum remaining working hours for 1 numOfGroup
							// Check this first to prevent unnecessary process
							if (numOfHours - currCourse.getHoursPerWeek() < 0) {

								System.out.printf("%s cannot have %s (%s). Reason: numOfHours\n", p.getName(), getReqCourseId(sData), getReqCourseLanguage(sData));
								
							} else { // numOfHours are enough
								
								// Check discipline
								if (!p.getDisciplines().contains(getReqCourseDiscipline(sData))) {
									
									System.out.printf("%s cannot have %s (%s). Reason: discipline\n", p.getName(), getReqCourseId(sData), getReqCourseLanguage(sData));
									
								} else { // Discipline is checked
											
									// Check number of groups
									if (currCourse.getNumOfGroups() <= 0 || getReqCourseNumOfGroups(sData) <= 0) {
									
										System.out.printf("%s cannot have %s (%s). Reason: groupNum\n", p.getName(), getReqCourseId(sData), getReqCourseLanguage(sData));
										
									} else { // numOfGroups remains 
										
										int numOfGroupsToAssign = getReqCourseNumOfGroups(sData);
										// *how many are available? 
										// *Math.min(a,b)
										// Check if it's possible to assign all the groups requested
										if (numOfHours - currCourse.getHoursPerWeek() * numOfGroupsToAssign < 0) {
											
											// If numOfHours is not enough, assign 1 group
											System.out.println(p.getName() + " doesn't have enough numOfHours for requested numOfGroups.");
											System.out.println("1 group (default) will be assigned.");
											
											numOfGroupsToAssign = 1;
										}
										
										// Create new course to assign because assigning course will have different groupOfNum values to the original course data
										// courseToAssign = for professor
										// currCourse = reference of a course of courseMap
										Course courseToAssign = new Course(currCourse);							
										 
										if (currCourse.getNumOfGroups() <= numOfGroupsToAssign) { 
											
											courseToAssign.setNumOfGroups(currCourse.getNumOfGroups()); // Professor's course's numOfGroup
											currCourse.setNumOfGroups(0); // CourseMap's course's numOfGroup
											
										} else { 
											
											courseToAssign.setNumOfGroups(numOfGroupsToAssign); // Professor's course's numOfGroup
											currCourse.decreaseNumOfGroups(numOfGroupsToAssign); // CourseMap's course's numOfGroup
										}
										
										assignedGroupNum += numOfGroupsToAssign;
										p.increaseAssignedGroupNum(numOfGroupsToAssign);
										
										// Assign the course
										p.getAssignedCourses().add(courseToAssign);

										// Calculate remaining numOfHours of the professor
										numOfHours -= courseToAssign.getHoursPerWeek() * courseToAssign.getNumOfGroups(); 
										assignedHours += courseToAssign.getHoursPerWeek() * courseToAssign.getNumOfGroups(); 
										
										System.out.println(courseToAssign.getId() + " / " + courseToAssign.getLanguage() + " has been assigned to " + p.getName());
										
										System.out.println(p.getName() + "'s remaining numOfHours: " + numOfHours);
										System.out.println(courseToAssign.getId() + "'s remaining numOfGroups: " + dpt.courseMap.get(getReqCourseId(sData)).get(index).getNumOfGroups());
									}
								}
							}
						}						
					}
				}
				// Update request_final file		    
		        try {
				
					File file = new File(r_filePath); 
			        
			        if (!file.exists()) { 
			        	
			            file.createNewFile();
			        }
			        
		        	BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
		        	
		            writer.write(reqData.get(i) + ", " + assignedGroupNum);
		            writer.newLine();

		            writer.flush();
		            writer.close();
		        }
		        
		        catch (IOException except) {
		            except.printStackTrace();
		        }
			}
			
			// Update professor_finalAssignments file
			String p_filePath = "C:\\Users\\b2uty\\eclipse-workspace\\DS_FinalProject\\bin\\Data\\Results\\professors_finalAssignments.txt";
	        
	        try {
			
				File file = new File(p_filePath); 
		        
		        if (!file.exists()) { 
		        	
		            file.createNewFile();
		        }
		        
	        	BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
	        	
	            writer.write(p.getId() + " | " + assignedHours + " | " + p.getAssignedGroupNum());
	            writer.newLine();

	            writer.flush();
	            writer.close();
	        }
	        
	        catch (IOException except) {
	            except.printStackTrace();
	        }
			
			System.out.println(p.getName() + " process done");
			System.out.println("==============");
		}
		
		System.out.println("Processed all the requests.");
		System.out.println("==============");
	}


	private static void createUnassignedCourseFile(ArrayList<Course> courses) {
		
		// Create courses_unassigned.csv file
		String c_filePath = "C:\\Users\\b2uty\\eclipse-workspace\\DS_FinalProject\\bin\\Data\\Results\\courses_unassigned.csv";
        
        try {
		
			File file = new File(c_filePath); 
	        
	        if (!file.exists()) { 
	        	
	            file.createNewFile();
	        }
	        
        	BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
        	
        	writer.write("courseId, courseTitle, discipline, numOfHours, numOfRemainingGroups");
        	writer.newLine();
        	
            for (Course c : courses) {
            	
            	if (c.getNumOfGroups() > 0) {
            		
            		writer.write(c.getId() + ", " + c.getTitle() + ", " + c.getDiscipline() + ", " + c.getNumOfHours() + ", " + c.getNumOfGroups());
                    writer.newLine();
            	}
            }

            writer.flush();
            writer.close();
        }
        
        catch (IOException except) {
            except.printStackTrace();
        }
	}
}
