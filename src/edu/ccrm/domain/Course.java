package edu.ccrm.domain;

public class Course {
    private String code;
    private String title;
    private int credits;
    private String instructor;
    private Semester semester;
    private String department;

    public Course(String code, String title, int credits, String instructor, String department, Semester semester) {
        if (credits<0) {
            throw new IllegalArgumentException("Credits must be positive");
        }
        this.code = code;
        this.title = title;
        this.credits = credits;
        this.instructor = instructor;
        this.department = department;
        this.semester = semester;
    }
    //getters
    public String getCode() {return code;}
    public String getTitle() {return title;}
    public int getCredits() {return credits;}
    public String getInstructor() {return instructor;}
    public String getDepartment() {return department;}
    public Semester getSemester() {return semester;}

    @Override
    public String toString() {
        return code+" - "+title+"("+credits+"credits)";
    }
    //setters for future updates
    public void setCredits(int credits) {this.credits = credits;}
    public void setInstructor(String instructor) {this.instructor = instructor;}
}
