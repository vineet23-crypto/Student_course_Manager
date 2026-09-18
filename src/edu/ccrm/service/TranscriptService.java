package edu.ccrm.service;

import edu.ccrm.domain.*;
import java.util.List;
import java.util.Objects;

public class TranscriptService {
    private final EnrollmentService enrollmentService;

    public record Line(String courseCode, String title, int credits, String semester, String grade) {
    }

    public static class Formatter {
        public String format(Line l) {
            return l.courseCode + " - " + l.title +
                    " | Semester: " + l.semester +
                    " | Credits: " + l.credits +
                    " | Grade: " + l.grade;
        }
    }

    public TranscriptService(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    public double computeGpa(Student student) {
        List<Enrollment> enrollments = enrollmentService.getEnrollmentsForStudent(student);

        int totalCredits = 0;
        int totalPoints = 0;

        for (Enrollment e : enrollments) {
            Grade g = e.getGrade();
            if (g != null) {
                int credits = safeCredits(e.getCourse());
                int points = g.getGradePoint();
                totalCredits += credits;
                totalPoints += credits * points;
            }
        }

        if (totalCredits == 0) return 0.0;
        return (double) totalPoints / totalCredits;
    }

    // New: return null when no graded courses, for cleaner distribution bucketing
    public Double gpaOf(Student student) {
        List<Enrollment> graded = enrollmentService.getEnrollmentsForStudent(student).stream()
                .filter(e -> e.getGrade() != null)
                .toList();

        int totalCredits = graded.stream()
                .map(Enrollment::getCourse)
                .filter(Objects::nonNull)
                .mapToInt(Course::getCredits)
                .sum();

        if (totalCredits == 0) return null;

        int totalPoints = graded.stream()
                .mapToInt(e -> safeCredits(e.getCourse()) * e.getGrade().getGradePoint())
                .sum();

        return (double) totalPoints / totalCredits;
    }

    public String buildTranscript(Student student) {
        StringBuilder sb = new StringBuilder();
        sb.append("Transcript for ").append(student.getFullName())
                .append(" (").append(student.getRegNo()).append(")").append("\n");

        List<Enrollment> enrollments = enrollmentService.getEnrollmentsForStudent(student);
        if (enrollments.isEmpty()) {
            sb.append("No enrollments found.\n");
        } else {
            Formatter formatter = new Formatter();
            for (Enrollment e : enrollments) {
                Line line = new Line(
                        e.getCourse().getCode(),
                        e.getCourse().getTitle(),
                        safeCredits(e.getCourse()),
                        e.getSemester().toString(),
                        e.getGrade() == null ? "N/A" : e.getGrade().toString()
                );
                sb.append(formatter.format(line)).append("\n");
            }
        }

        sb.append(String.format("GPA: %.2f%n", computeGpa(student)));
        return sb.toString();
    }

    private int safeCredits(Course c) {
        return c == null ? 0 : c.getCredits();
    }

}
