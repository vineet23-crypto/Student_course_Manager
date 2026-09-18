package edu.ccrm.domain;

import java.time.LocalDate;

public class Enrollment {
    private Student student;
    private Semester semester;
    private Grade grade;
    private Course course;
    private LocalDate enrollmentDate;

    public Enrollment(Student student, Course course,Semester semester) {
        this.student = student;
        this.semester = semester;
        this.grade = null;//initially not graded
        this.course = course;
        this.enrollmentDate = LocalDate.now();//sets to today's date
    }
    //getters
    public Student getStudent() {return student;}
    public Course getCourse() {return course;}
    public Semester getSemester() {return semester;}
    public Grade getGrade() {return grade;}
    public LocalDate getEnrollmentDate() {return enrollmentDate;}
    //setter
    public void setGrade(Grade grade) {this.grade = grade;}
}