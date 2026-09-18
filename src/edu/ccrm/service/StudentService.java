package edu.ccrm.service;

import edu.ccrm.domain.Student;
import java.util.ArrayList;
import java.util.List;

public class StudentService {
    // In-memory storage (our "database")
    private ArrayList<Student> students = new ArrayList<>();

    // C - CREATE: Add new student
    public void addStudent(Student student) {
        // Check if student with same ID already exists
        if (findStudentById(student.getId()) != null) {
            throw new IllegalArgumentException("Student with ID " + student.getId() + " already exists");
        }
        students.add(student);
    }

    // R - READ: Find student by ID
    public Student findStudentById(String id) {
        for (Student student : students) {
            if (student.getId().equals(id)) {
                return student;
            }
        }
        return null; // Not found
    }

    // R - READ: Get all students
    public List<Student> getAllStudents() {
        // Return a copy to protect the original list (encapsulation)
        return new ArrayList<>(students);
    }

    // U - UPDATE: Update student information
    public void updateStudent(String id, Student updatedStudent) {
        Student existingStudent = findStudentById(id);
        if (existingStudent == null) {
            throw new IllegalArgumentException("Student with ID " + id + " not found");
        }

        // Update fields (except ID which shouldn't change)
        existingStudent.setFullName(updatedStudent.getFullName());
        existingStudent.setEmail(updatedStudent.getEmail());
        existingStudent.setPhoneNumber(updatedStudent.getPhoneNumber());
        existingStudent.setStatus(updatedStudent.getStatus());
    }

    // D - DELETE/DEACTIVATE: Deactivate student
    public void deactivateStudent(String id) {
        Student student = findStudentById(id);
        if (student == null) {
            throw new IllegalArgumentException("Student with ID " + id + " not found");
        }
        student.setStatus("INACTIVE");
    }
}