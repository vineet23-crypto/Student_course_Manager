package edu.ccrm.cli;

import edu.ccrm.domain.*;
import edu.ccrm.service.*;
import edu.ccrm.io.ImportExportService;
import edu.ccrm.io.BackupService;
import edu.ccrm.config.AppConfig;

import java.nio.file.Path;
import java.util.List;
import java.util.Scanner;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {
    private static final Scanner sc = new Scanner(System.in);

    private static final StudentService studentService = new StudentService();
    private static final CourseService courseService = new CourseService();
    private static final EnrollmentService enrollmentService = new EnrollmentService();
    private static final TranscriptService transcriptService = new TranscriptService(enrollmentService);

    private static final ImportExportService ioService =
            new ImportExportService(studentService, courseService, enrollmentService);
    private static final BackupService backupService = new BackupService();

    public static void main(String[] args) {
        AppConfig.get();
        mainLoop:
        while (true) {
            System.out.println("\n=== Campus Course & Records Manager ===");
            System.out.println("1. Manage Students");
            System.out.println("2. Manage Courses");
            System.out.println("3. Manage Enrollments");
            System.out.println("4. File Utilities");
            System.out.println("5. Reports");
            System.out.println("6. Exit");
            System.out.print("Enter choice: ");

            String choice = sc.nextLine().trim();

            switch (choice) {
                case "1" -> studentMenu();
                case "2" -> courseMenu();
                case "3" -> enrollmentMenu();
                case "4" -> fileUtilitiesMenu();
                case "5" -> reportsMenu();
                case "6" -> {
                    System.out.println("Exiting program...");
                    break mainLoop;
                }
                default -> System.out.println("Invalid choice, try again.");
            }
        }
        sc.close();
    }

    // ------------------ Student Menu ------------------
    private static void studentMenu() {
        while (true) {
            System.out.println("\n--- Student Management ---");
            System.out.println("1. Add Student");
            System.out.println("2. List Students");
            System.out.println("3. Update Student");
            System.out.println("4. Deactivate Student");
            System.out.println("5. Back");
            System.out.print("Choice: ");
            String ch = sc.nextLine().trim();

            switch (ch) {
                case "1" -> addStudent();
                case "2" -> listStudents();
                case "3" -> updateStudent();
                case "4" -> deactivateStudent();
                case "5" -> { return; }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    private static void addStudent() {
        System.out.print("Enter ID: ");
        String id = sc.nextLine();
        System.out.print("Enter Full Name: ");
        String name = sc.nextLine();
        System.out.print("Enter Email: ");
        String email = sc.nextLine();
        System.out.print("Enter Phone: ");
        String phone = sc.nextLine();
        System.out.print("Enter RegNo: ");
        String regNo = sc.nextLine();

        Student s = new Student(id, name, email, phone, regNo);
        studentService.addStudent(s);
        System.out.println("Student added successfully!");
    }

    private static void listStudents() {
        List<Student> students = studentService.getAllStudents();
        if (students.isEmpty()) {
            System.out.println("No students found.");
        } else {
            students.forEach(s -> System.out.println(s.getId() + " - " + s.getFullName() + " (" + s.getStatus() + ")"));
        }
    }

    private static void updateStudent() {
        System.out.print("Enter Student ID to update: ");
        String id = sc.nextLine();
        Student existing = studentService.findStudentById(id);
        if (existing == null) {
            System.out.println("Student not found.");
            return;
        }
        System.out.print("Enter New Full Name: ");
        String name = sc.nextLine();
        System.out.print("Enter New Email: ");
        String email = sc.nextLine();
        System.out.print("Enter New Phone: ");
        String phone = sc.nextLine();
        System.out.print("Enter New Status: ");
        String status = sc.nextLine();

        Student updated = new Student(id, name, email, phone, existing.getRegNo());
        updated.setStatus(status);
        studentService.updateStudent(id, updated);
        System.out.println("Student updated!");
    }

    private static void deactivateStudent() {
        System.out.print("Enter Student ID to deactivate: ");
        String id = sc.nextLine();
        studentService.deactivateStudent(id);
        System.out.println("Student deactivated.");
    }

    // ------------------ Course Menu ------------------
    private static void courseMenu() {
        while (true) {
            System.out.println("\n--- Course Management ---");
            System.out.println("1. Add Course");
            System.out.println("2. List Courses");
            System.out.println("3. Search by Department");
            System.out.println("4. Search by Semester");
            System.out.println("5. Update Course");
            System.out.println("6. Deactivate Course");
            System.out.println("7. Back");
            System.out.print("Choice: ");
            String ch = sc.nextLine().trim();

            switch (ch) {
                case "1" -> addCourse();
                case "2" -> listCourses();
                case "3" -> searchByDepartment();
                case "4" -> searchBySemester();
                case "5" -> updateCourse();
                case "6" -> deactivateCourse();
                case "7" -> { return; }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    private static void addCourse() {
        System.out.print("Enter Code: ");
        String code = sc.nextLine();
        System.out.print("Enter Title: ");
        String title = sc.nextLine();
        System.out.print("Enter Credits: ");
        int credits = Integer.parseInt(sc.nextLine());
        System.out.print("Enter Instructor: ");
        String instructor = sc.nextLine();
        System.out.print("Enter Department: ");
        String dept = sc.nextLine();
        Semester sem = readSemester();

        Course c = new Course(code, title, credits, instructor, dept, sem);
        courseService.addCourse(c);
        System.out.println("Course added!");
    }

    private static void listCourses() {
        List<Course> courses = courseService.getAllCourses();
        if (courses.isEmpty()) {
            System.out.println("No courses found.");
        } else {
            courses.forEach(System.out::println);
        }
    }

    private static void searchByDepartment() {
        System.out.print("Enter Department: ");
        String dept = sc.nextLine();
        courseService.findCoursesByDepartment(dept).forEach(System.out::println);
    }

    private static void searchBySemester() {
        Semester sem = readSemester();
        courseService.findCoursesBySemester(sem).forEach(System.out::println);
    }

    private static void updateCourse() {
        System.out.print("Enter Course Code to update: ");
        String code = sc.nextLine();
        Course existing = courseService.findCourseByCode(code);
        if (existing == null) {
            System.out.println("Course not found.");
            return;
        }
        System.out.print("Enter New Credits: ");
        int credits = Integer.parseInt(sc.nextLine());
        System.out.print("Enter New Instructor: ");
        String instructor = sc.nextLine();

        Course updated = new Course(code, existing.getTitle(), credits, instructor, existing.getDepartment(), existing.getSemester());
        courseService.updateCourse(code, updated);
        System.out.println("Course updated!");
    }

    private static void deactivateCourse() {
        System.out.print("Enter Course Code to deactivate: ");
        String code = sc.nextLine();
        courseService.deactivateCourse(code);
        System.out.println("Course deactivated.");
    }

    // ------------------ Enrollment Menu ------------------
    private static void enrollmentMenu() {
        while (true) {
            System.out.println("\n--- Enrollment Management ---");
            System.out.println("1. Enroll Student");
            System.out.println("2. Unenroll Student");
            System.out.println("3. Record Grade");
            System.out.println("4. Show Transcript");
            System.out.println("5. Back");
            System.out.print("Choice: ");
            String ch = sc.nextLine().trim();

            switch (ch) {
                case "1" -> enrollStudent();
                case "2" -> unenrollStudent();
                case "3" -> recordGrade();
                case "4" -> showTranscript();
                case "5" -> { return; }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    private static void enrollStudent() {
        System.out.print("Enter Student ID: ");
        String sid = sc.nextLine();
        Student s = studentService.findStudentById(sid);
        if (s == null) { System.out.println("Student not found."); return; }

        System.out.print("Enter Course Code: ");
        String code = sc.nextLine();
        Course c = courseService.findCourseByCode(code);
        if (c == null) { System.out.println("Course not found."); return; }

        Semester sem = readSemester();

        if (enrollmentService.enrollStudent(s, c, sem)) {
            System.out.println("Enrolled successfully!");
        } else {
            System.out.println("Enrollment failed.");
        }
    }

    private static void unenrollStudent() {
        System.out.print("Enter Student ID: ");
        String sid = sc.nextLine();
        Student s = studentService.findStudentById(sid);
        if (s == null) { System.out.println("Student not found."); return; }
        System.out.print("Enter Course Code: ");
        String code = sc.nextLine();
        Course c = courseService.findCourseByCode(code);
        if (c == null) { System.out.println("Course not found."); return; }
        Semester sem = readSemester();

        if (enrollmentService.unenrollStudent(s, c, sem)) {
            System.out.println("Unenrolled successfully!");
        } else {
            System.out.println("Unenrollment failed.");
        }
    }

    private static Semester readSemester() {
        System.out.print("Enter Semester (SPRING/SUMMER/FALL/WINTER): ");
        String input = sc.nextLine().trim().toUpperCase();
        try {
            return Semester.valueOf(input);
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid semester entered. Defaulting to SPRING.");
            return Semester.SPRING;
        }
    }

    private static Grade readGrade() {
        System.out.print("Enter Grade (S/A/B/C/D/F): ");
        String input = sc.nextLine().trim().toUpperCase();
        try {
            return Grade.valueOf(input);
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid grade entered. Defaulting to F.");
            return Grade.F;
        }
    }

    private static void recordGrade() {
        System.out.print("Enter Student ID: ");
        String sid = sc.nextLine();
        Student s = studentService.findStudentById(sid);
        if (s == null) { System.out.println("Student not found."); return; }

        System.out.print("Enter Course Code: ");
        String code = sc.nextLine();
        Course c = courseService.findCourseByCode(code);
        if (c == null) { System.out.println("Course not found."); return; }

        Semester sem = readSemester();
        Grade g = readGrade();

        if (enrollmentService.recordGrade(s, c, sem, g)) {
            System.out.println("Grade recorded.");
        } else {
            System.out.println("Failed to record grade.");
        }
    }

    private static void showTranscript() {
        System.out.print("Enter Student ID: ");
        String sid = sc.nextLine();
        Student s = studentService.findStudentById(sid);
        if (s == null) {
            System.out.println("Student not found.");
            return;
        }
        System.out.println(transcriptService.buildTranscript(s));
    }
    // ------------------ File Utilities ------------------
    private static void fileUtilitiesMenu() {
        while (true) {
            System.out.println("\n--- File Utilities ---");
            System.out.println("1. Import Students (CSV)");
            System.out.println("2. Import Courses (CSV)");
            System.out.println("3. Export Students (CSV)");
            System.out.println("4. Export Courses (CSV)");
            System.out.println("5. Export Enrollments (CSV)");
            System.out.println("6. Backup Export Folder");
            System.out.println("7. Compute Backup Size");
            System.out.println("8. Back");
            System.out.println("9. Show Config (dirs)");
            System.out.print("Choice: ");
            String ch = sc.nextLine().trim();

            try {
                switch (ch) {
                    case "1" -> {
                        Path file = askPath("import students");
                        ioService.importStudents(file);
                        System.out.println("Students imported from: " + file);
                    }
                    case "2" -> {
                        Path file = askPath("import courses");
                        ioService.importCourses(file);
                        System.out.println("Courses imported from: " + file);
                    }
                    case "3" -> {
                        Path file = askPath("export students");

                        // Check if we have data first
                        if (studentService.getAllStudents().isEmpty()) {
                            System.out.println("WARNING: No student data to export! Add students first or use option 10 for sample data.");
                        } else {
                            ioService.exportStudents(file);
                            System.out.println("Students exported to: " + file);
                            System.out.println("Exported " + studentService.getAllStudents().size() + " students");
                        }
                    }
                    case "4" -> {
                        Path file = askPath("export courses");

                        if (courseService.getAllCourses().isEmpty()) {
                            System.out.println("WARNING: No course data to export! Add courses first or use option 10 for sample data.");
                        } else {
                            ioService.exportCourses(file);
                            System.out.println("Courses exported to: " + file);
                            System.out.println("Exported " + courseService.getAllCourses().size() + " courses");
                        }
                    }
                    case "5" -> {
                        Path file = askPath("export enrollments");

                        if (enrollmentService.getAllEnrollments().isEmpty()) {
                            System.out.println("WARNING: No enrollment data to export! Enroll students in courses first.");
                        } else {
                            ioService.exportEnrollments(file);
                            System.out.println("Enrollments exported to: " + file);
                            System.out.println("Exported " + enrollmentService.getAllEnrollments().size() + " enrollments");
                        }
                    }
                    case "6" -> {
                        // FIXED: Use askDirectory instead of askPath for backup
                        System.out.print("Enter EXPORT directory to backup: ");
                        String exportDirPath = sc.nextLine().trim();
                        Path exportDir = Path.of(exportDirPath).toAbsolutePath();

                        System.out.print("Enter BACKUP root directory: ");
                        String backupRootPath = sc.nextLine().trim();
                        Path backupRoot = Path.of(backupRootPath).toAbsolutePath();

                        // Verify export directory exists and has files
                        if (!java.nio.file.Files.exists(exportDir)) {
                            System.out.println("Export directory doesn't exist: " + exportDir);
                            System.out.println("Please export some data first (options 3, 4, or 5)");
                            break;
                        }

                        try (java.util.stream.Stream<Path> files = java.nio.file.Files.list(exportDir)) {
                            long fileCount = files.filter(java.nio.file.Files::isRegularFile).count();
                            if (fileCount == 0) {
                                System.out.println("No files found in export directory. Export data first!");
                                break;
                            }
                            System.out.println("Found " + fileCount + " files to backup");
                        }

                        System.out.println("Starting backup...");
                        Path backupDir = backupService.backupData(exportDir, backupRoot);
                        System.out.println("✓ Backup created at: " + backupDir);
                    }
                    case "7" -> {
                        System.out.print("Enter directory to check size: ");
                        String dirPath = sc.nextLine().trim();
                        Path dir = Path.of(dirPath);
                        long size = backupService.computeFolderSize(dir);
                        System.out.println("Folder size: " + size + " bytes (" + backupService.formatSize(size) + ")");
                    }
                    case "8" -> { return; }
                    case "9" -> {
                        var cfg = edu.ccrm.config.AppConfig.get();
                        System.out.println("ImportDir: " + cfg.getImportDir());
                        System.out.println("ExportDir: " + cfg.getExportDir());
                        System.out.println("BackupRoot: " + cfg.getBackupRoot());
                    }
                    default -> System.out.println("Invalid choice.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    // ------------------ Reports ------------------
    private static void reportsMenu() {
        while (true) {
            System.out.println("\n--- Reports ---");
            System.out.println("1. Courses by Department");
            System.out.println("2. Courses by Semester");
            System.out.println("3. Show Transcript (by Student ID)");
            System.out.println("4. Top Courses by Enrollments");
            System.out.println("5. GPA Distribution");
            System.out.println("6. Back");
            System.out.print("Choice: ");
            String ch = sc.nextLine().trim();

            switch (ch) {
                case "1" -> searchByDepartment();
                case "2" -> searchBySemester();
                case "3" -> showTranscript();
                case "4" -> reportTopCourses();
                case "5" -> reportGpaDistribution();
                case "6" -> { return; }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    private static void reportTopCourses() {
        Map<String, Long> counts = enrollmentService.getAllEnrollments().stream()
                .collect(Collectors.groupingBy(e -> e.getCourse().getCode(), Collectors.counting()));
        counts.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .limit(5)
                .forEach(e -> System.out.println(e.getKey() + " -> " + e.getValue() + " enrollments"));
    }

    private static void reportGpaDistribution() {
        List<Student> students = studentService.getAllStudents();
        Map<String, Long> buckets = new java.util.HashMap<>();
        java.util.function.Function<Double,String> bucket = g -> {
            if (g >= 9.0) return "9-10";
            if (g >= 8.0) return "8-8.99";
            if (g >= 7.0) return "7-7.99";
            if (g >= 6.0) return "6-6.99";
            return "<6";
        };
        for (Student s : students) {
            Double gpa = transcriptService.computeGpa(s); //fixed name
            String key = bucket.apply(gpa);
            buckets.merge(key, 1L, Long::sum);
        }
        buckets.forEach((k,v) -> System.out.println(k + " -> " + v));
    }

    //Helper
    private static Path askPath(String operationType) {
        System.out.print("Enter directory path where you want to " + operationType + ": ");
        String directoryPath = sc.nextLine().trim();

        System.out.print("Enter filename: ");
        String filename = sc.nextLine().trim();

        if (!filename.toLowerCase().endsWith(".csv")) {
            filename += ".csv";
        }

        return Path.of(directoryPath, filename);
    }
}
