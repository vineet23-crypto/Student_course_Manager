package edu.ccrm.service;

import edu.ccrm.domain.*;
import java.util.ArrayList;
import java.util.List;

public class EnrollmentService {
    private List<Enrollment> enrollments = new ArrayList<>();

    // Basic enrollment without complex business rules for now
    public boolean enrollStudent(Student student, Course course, Semester semester) {
        // Check if already enrolled
        if (isAlreadyEnrolled(student, course, semester)) {
            System.out.println("Student already enrolled in this course for " + semester);
            return false;
        }

        Enrollment enrollment = new Enrollment(student, course, semester);
        enrollments.add(enrollment);
        student.enrollInCourse(course); // Update student's course list
        return true;
    }

    // Unenroll student
    public boolean unenrollStudent(Student student, Course course, Semester semester) {
        Enrollment enrollment = findEnrollment(student, course, semester);
        if (enrollment != null) {
            enrollments.remove(enrollment);
            student.unenrollFromCourse(course);
            return true;
        }
        return false;
    }

    // Record grade
    public boolean recordGrade(Student student, Course course, Semester semester, Grade grade) {
        Enrollment enrollment = findEnrollment(student, course, semester);
        if (enrollment != null) {
            enrollment.setGrade(grade);
            return true;
        }
        return false;
    }

    // Helper methods
    private boolean isAlreadyEnrolled(Student student, Course course, Semester semester) {
        return findEnrollment(student, course, semester) != null;
    }

    private Enrollment findEnrollment(Student student, Course course, Semester semester) {
        for (Enrollment enrollment : enrollments) {
            if (enrollment.getStudent().equals(student) &&
                    enrollment.getCourse().equals(course) &&
                    enrollment.getSemester() == semester) {
                return enrollment;
            }
        }
        return null;
    }

    // Get all enrollments
    public List<Enrollment> getAllEnrollments() {
        return new ArrayList<>(enrollments);
    }

    // Get enrollments for a specific student
    public List<Enrollment> getEnrollmentsForStudent(Student student) {
        List<Enrollment> studentEnrollments = new ArrayList<>();
        for (Enrollment enrollment : enrollments) {
            if (enrollment.getStudent().equals(student)) {
                studentEnrollments.add(enrollment);
            }
        }
        return studentEnrollments;
    }
}