package edu.ccrm.service;

import edu.ccrm.domain.Course;
import edu.ccrm.domain.Semester;
import java.util.ArrayList;
import java.util.List;

public class CourseService {

    private ArrayList<Course> courses = new ArrayList<>();

    //Add new course
    public void addCourse(Course course) {
        // Check if the course already exists
        if (findCourseByCode(course.getCode()) != null) {
            throw new IllegalArgumentException("Course with code " + course.getCode() + " already exists");
        }
        courses.add(course);
    }

    // R - READ: Find course by code
    public Course findCourseByCode(String code) {
        for (Course course : courses) {
            if (course.getCode().equalsIgnoreCase(code)) {
                return course;
            }
        }
        return null; // Not found
    }

    // R - READ: Get all courses
    public List<Course> getAllCourses() {
        return new ArrayList<>(courses);
    }

    // R - READ: Find courses by department (using Streams - project requirement)
    public List<Course> findCoursesByDepartment(String department) {
        return courses.stream()
                .filter(course -> course.getDepartment().equalsIgnoreCase(department))
                .toList();
    }

    // R - READ: Find courses by semester (using Streams)
    public List<Course> findCoursesBySemester(Semester semester) {
        return courses.stream()
                .filter(course -> course.getSemester() == semester)
                .toList();
    }

    // R - READ: Find courses by instructor
    public List<Course> findCoursesByInstructor(String instructor) {
        return courses.stream()
                .filter(course -> course.getInstructor().equalsIgnoreCase(instructor))
                .toList();
    }

    // U - UPDATE: Update course information
    public void updateCourse(String courseCode, Course updatedCourse) {
        Course existingCourse = findCourseByCode(courseCode);
        if (existingCourse == null) {
            throw new IllegalArgumentException("Course with code " + courseCode + " not found");
        }

        // Update fields (except code which shouldn't change)
        existingCourse.setCredits(updatedCourse.getCredits());
        existingCourse.setInstructor(updatedCourse.getInstructor());
    }

    // D - DEACTIVATE: "Delete" by marking as inactive (we'll add status field to Course later)
    public void deactivateCourse(String courseCode) {
        Course course = findCourseByCode(courseCode);
        if (course == null) {
            throw new IllegalArgumentException("Course with code " + courseCode + " not found");
        }
        // For now, we'll remove from a list. Later we can add an 'active' field.
        courses.remove(course);
    }

    // Search courses by title (using Streams)
    public List<Course> searchCoursesByTitle(String keyword) {
        return courses.stream()
                .filter(course -> course.getTitle().toLowerCase().contains(keyword.toLowerCase()))
                .toList();
    }
}