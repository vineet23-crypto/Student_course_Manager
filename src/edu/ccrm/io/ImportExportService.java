package edu.ccrm.io;

import edu.ccrm.domain.*;
import edu.ccrm.service.*;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ImportExportService {
    private final StudentService studentService;
    private final CourseService courseService;
    private final EnrollmentService enrollmentService;

    public ImportExportService(StudentService ss, CourseService cs, EnrollmentService es) {
        this.studentService = ss;
        this.courseService = cs;
        this.enrollmentService = es;
    }

    //  Import
    public void importStudents(Path file) throws IOException {
        try (Stream<String> lines = Files.lines(file)) {
            lines.skip(1).forEach(line -> {
                String[] parts = line.split(",");
                if (parts.length >= 5) {
                    Student s = new Student(parts[0].trim(), parts[1].trim(),
                            parts[2].trim(), parts[3].trim(), parts[4].trim());
                    studentService.addStudent(s);
                }
            });
        }
    }

    public void importCourses(Path file) throws IOException {
        try (Stream<String> lines = Files.lines(file)) {
            lines.skip(1).forEach(line -> {
                String[] parts = line.split(",");
                if (parts.length >= 6) {
                    Course c = new Course(
                            parts[0].trim(),
                            parts[1].trim(),
                            Integer.parseInt(parts[2].trim()),
                            parts[3].trim(),
                            parts[4].trim(),
                            Semester.valueOf(parts[5].trim().toUpperCase())
                    );
                    courseService.addCourse(c);
                }
            });
        }
    }

    //  Export
    public void exportStudents(Path file) throws IOException {
        List<String> lines = studentService.getAllStudents().stream()
                .map(s -> String.join(",", s.getId(), s.getFullName(), s.getEmail(),
                        s.getPhoneNumber(), s.getRegNo(), s.getStatus()))
                .collect(Collectors.toList());

        lines.add(0, "id,fullName,email,phone,regNo,status"); // header
        Files.write(file, lines);
    }

    public void exportCourses(Path file) throws IOException {
        List<String> lines = courseService.getAllCourses().stream()
                .map(c -> String.join(",", c.getCode(), c.getTitle(),
                        String.valueOf(c.getCredits()), c.getInstructor(),
                        c.getDepartment(), c.getSemester().toString()))
                .collect(Collectors.toList());

        lines.add(0, "code,title,credits,instructor,department,semester");
        Files.write(file, lines);
    }

    public void exportEnrollments(Path file) throws IOException {
        List<String> lines = enrollmentService.getAllEnrollments().stream()
                .map(e -> String.join(",",
                        e.getStudent().getId(),
                        e.getCourse().getCode(),
                        e.getSemester().toString(),
                        e.getGrade() == null ? "N/A" : e.getGrade().toString(),
                        e.getEnrollmentDate().toString()))
                .collect(Collectors.toList());

        lines.add(0, "studentId,courseCode,semester,grade,date");
        Files.write(file, lines);
    }
}