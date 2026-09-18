# USAGE.md

## 📌 Overview
Student Course Manager is a simple **console-based Java application** that lets an institute manage:
- Students (add, list, update, deactivate)
- Courses (add, list, search/filter, update, deactivate)
- Enrollments (enroll, unenroll, record grades, transcripts with GPA)
- File I/O (import/export CSV, backup data with folder size)

---

## ▶️ How to Run
1. Compile the project:
   javac -d bin src/edu/ccrm/**/*.java

2. Run the main program:
   java -cp bin edu.ccrm.cli.Main

---

## 📖 Main Menu
When you start the program, you’ll see:

=== Campus Course & Records Manager ===
1. Manage Students
2. Manage Courses
3. Manage Enrollments
4. Exit

Later, additional options for Import/Export and Backup will also appear.

---

## 👩‍🎓 Student Management
--- Student Management ---
1. Add Student
2. List Students
3. Update Student
4. Deactivate Student
5. Back

**Example – Add Student:**
Enter ID: S01
Enter Full Name: John Doe
Enter Email: john@example.com
Enter Phone: 1234567890
Enter RegNo: REG2025

---

## 📚 Course Management
--- Course Management ---
1. Add Course
2. List Courses
3. Search by Department
4. Search by Semester
5. Update Course
6. Deactivate Course
7. Back

**Example – Add Course:**
Enter Code: CS101
Enter Title: Intro to CS
Enter Credits: 3
Enter Instructor: Dr. Kumar
Enter Department: CS
Enter Semester (SPRING/SUMMER/FALL/WINTER): SPRING

---

## 📝 Enrollment Management
--- Enrollment Management ---
1. Enroll Student
2. Unenroll Student
3. Record Grade
4. Show Transcript
5. Back

**Example – Enroll Student:**
Enter Student ID: S01
Enter Course Code: CS101
Enter Semester: SPRING

**Example – Record Grade:**
Enter Student ID: S01
Enter Course Code: CS101
Enter Semester: SPRING
Enter Grade (S/A/B/C/D/F): A

**Example – Show Transcript:**
Transcript for John Doe (REG2025)
CS101 - Intro to CS | Semester: SPRING | Grade: A
GPA: 9.00

---

## 📂 File I/O (after integration)
--- Data Management ---
1. Import Students from CSV
2. Import Courses from CSV
3. Export Students
4. Export Courses
5. Export Enrollments
6. Back

CSV format is simple, comma-separated values. Example:

**students.csv**
id,fullName,email,phone,regNo
S01,John Doe,john@example.com,1234567890,REG2025
S02,Jane Smith,jane@example.com,9876543210,REG2026

**courses.csv**
code,title,credits,instructor,department,semester
CS101,Intro to CS,3,Dr. Kumar,CS,SPRING
MA101,Calculus I,4,Dr. Rao,Math,FALL

---

## 💾 Backup & Reports (after integration)
- **Backup** copies exported files into a timestamped folder like:
  backup_20250925_112233/
  students.csv
  courses.csv
  enrollments.csv

- **Recursive folder size** is printed after backup.

---

## ✅ Notes
- Input is case-insensitive for `Semester` and `Grade` (SPRING/spring both work).
- Invalid inputs default to safe values or show error messages.
- All data is stored **in-memory** while the app is running. To persist, use **Export → Backup**.  
