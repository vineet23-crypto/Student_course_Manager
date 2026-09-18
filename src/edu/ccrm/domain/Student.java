package edu.ccrm.domain;

import java.util.ArrayList;

public class Student extends Person {
    private String regNo;
    private String status;
    private ArrayList<Course> enrolledCourses;

    public Student(String id,String fullName,String email,String phoneNo,String regNo) {
        super(id,fullName,email,phoneNo);
        this.regNo=regNo;
        this.status="ACTIVE";
        this.enrolledCourses=new ArrayList<>();
    }
    @Override
    public String getRole(){return "STUDENT";}
    //getters for other classes to access private fields
    public String getStatus(){return status;}
    public String getRegNo(){return regNo;}
    public ArrayList<Course> getCourses(){return enrolledCourses;}
    //setter for status updating of student
    public void setStatus(String status){this.status=status;}

    public void enrollInCourse(Course course) {this.enrolledCourses.add(course);}
    public void unenrollFromCourse(Course course) {this.enrolledCourses.remove(course);}
    public String getPhoneNo() {
        return super.getPhoneNumber(); // or whatever your Person class uses
    }
    public String getEmail() {
        return super.getEmail(); // or however you store email in Person
    }
}
