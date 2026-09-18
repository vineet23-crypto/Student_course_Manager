# Student Course Manager

## 📌 Project Overview
Student Course Manager is a **console-based Java application** for managing students, courses, and enrollments.  
It supports **CRUD operations**, transcripts with GPA calculation, **File I/O with NIO.2**, **Streams-based reports**, and **data backup with recursive folder size**.

## 🏛 Evolution of Java (Short Notes)
- **1991**: Oak language started at Sun Microsystems for embedded systems.
- **1995**: Officially released as Java (with "Write Once, Run Anywhere" promise).
- **2004 (Java 5)**: Introduced Generics, Enums, and Annotations.
- **2014 (Java 8)**: Major release with Streams, Lambdas, Optional API.
- **2017+**: Regular 6-month release cycle (Java 9 → modules, Java 17 → LTS).
- **Now**: Java powers enterprise apps, Android, big data, and cloud systems.

## ☕ JDK vs JRE vs JVM
- **JVM (Java Virtual Machine)**: Runs compiled `.class` bytecode. Platform dependent, but bytecode is portable.
- **JRE (Java Runtime Environment)**: JVM + core libraries, used to run Java apps.
- **JDK (Java Development Kit)**: JRE + compilers (javac), tools for developers. Needed for compiling and building apps.

## 🖥️ Java Editions
- **Java SE (Standard Edition)**: Core libraries for desktop & console apps (used in this project).
- **Java EE (Enterprise Edition)**: Adds APIs for web, distributed systems, databases (Servlets, EJB, etc.).
- **Java ME (Micro Edition)**: Optimized for mobile/embedded devices.

## 📈 Java Evolution Summary

### Java Platform Editions

| Edition | Purpose | Key Characteristics |
|---------|---------|---------------------|
| **Java SE** | Standard Edition | Core Java, desktop apps, foundations |
| **Java EE** | Enterprise Edition | Server-side, web applications, scalability |
| **Java ME** | Micro Edition | Mobile, embedded systems, IoT |

### Java Version Evolution Highlights

| Version | Year | Key Features |
|---------|------|--------------|
| Java 8 | 2014 | Lambdas, Streams, Optional |
| Java 11 | 2018 | LTS, HTTP Client, var keyword |
| Java 17 | 2021 | LTS, Sealed classes, Pattern matching |
| Java 21 | 2023 | Virtual threads, Record patterns |

# 🚀 Java and IntelliJ IDEA Installation Guide for macOS

This guide provides step-by-step instructions for installing the Java Development Kit (JDK) and IntelliJ IDEA on macOS using **Homebrew**.

---

##  Prerequisite: Install Homebrew

Homebrew is a package manager that simplifies installing software on macOS. If you don't have it, install it first.

1.  **Open Terminal** (You can find it in `Applications/Utilities/Terminal.app` or use Spotlight search: `Cmd + Space` and type "Terminal").
2.  **Run the Homebrew installation command:**
    ```bash
    /bin/bash -c "$(curl -fsSL [https://raw.githubusercontent.com/Homebrew/install/HEAD/install.sh](https://raw.githubusercontent.com/Homebrew/install/HEAD/install.sh))"
    ```
  * Follow the on-screen instructions, which may include entering your administrator password and installing Xcode Command Line Tools.
3. **Run the post-installation steps** suggested by the script, which often involves adding Homebrew to your system's PATH. For example, it might instruct you to run:
    ```bash
    echo 'eval "$(/opt/homebrew/bin/brew shellenv)"' >> ~/.zprofile
    eval "$(/opt/homebrew/bin/brew shellenv)"
    ```
    *(Note: This step can vary based on your macOS chip (Intel/Apple Silicon) and your shell (zsh/bash). Follow the Terminal's output precisely.)*
2. **Verify Homebrew installation:**
    ```bash
    brew --version
    ```
    You should see the version number.

---

## Step 1: Install Java Development Kit (JDK)

We will install a recent Long-Term Support (LTS) version of OpenJDK using Homebrew. **Java 21** is a good current choice.

1.  **Install OpenJDK (e.g., version 21):**
    ```bash
    brew install openjdk@21
    ```
  * If you need a different version (like Java 17 or 11), replace `openjdk@21` with the desired version (e.g., `openjdk@17`).
2.  **Follow Homebrew's instructions to link the JDK:**
    Homebrew will usually print instructions on how to set the `JAVA_HOME` environment variable and create a symlink for the system to find the JDK. Look for a message similar to:
    ```
    For the system Java wrappers to find this JDK, symlink it with
      sudo ln -sfn /opt/homebrew/opt/openjdk@21/libexec/openjdk.jdk /Library/Java/JavaVirtualMachines/openjdk-21.jdk
    ```
    Run the recommended `sudo ln -sfn ...` command.
3.  **Verify Java installation:**
    ```bash
    java -version
    ```
    The output should show the version you just installed (e.g., `openjdk version "21.0.1"`).

---

## Step 2: Install IntelliJ IDEA

IntelliJ IDEA comes in two main editions: **Community Edition** (free, open-source, excellent for pure Java/Kotlin development) and **Ultimate Edition** (paid, adds support for web development, enterprise frameworks, databases, etc.).

1.  **Install IntelliJ IDEA using Homebrew Cask:**
  * **For Community Edition (Recommended for beginners and pure Java):**
      ```bash
      brew install --cask intellij-idea-ce
      ```
  * **For Ultimate Edition (If you have a license or student email):**
      ```bash
      brew install --cask intellij-idea
      ```
2.  **Launch IntelliJ IDEA:**
  * Once the installation is complete, you can find **IntelliJ IDEA** in your `Applications` folder or launch it from Terminal:
      ```bash
      open -a "IntelliJ IDEA CE" # For Community Edition
      # OR
      open -a "IntelliJ IDEA"    # For Ultimate Edition
      ```
3.  **Initial Setup:**
  * The first time you launch it, you'll be prompted to accept the user agreement, configure initial settings (like UI theme), and activate your license (for Ultimate Edition).

---

## 🛠️ Optional: Manage Multiple Java Versions with `jenv`

If you need to switch between different Java versions easily for different projects (e.g., Java 8, 17, 21), you can use `jenv`.

1.  **Install jenv:**
    ```bash
    brew install jenv
    ```
2.  **Configure your shell to load jenv** (Follow the instructions printed in the Terminal, typically):
    ```bash
    echo 'export PATH="$HOME/.jenv/bin:$PATH"' >> ~/.zshrc
    echo 'eval "$(jenv init -)"' >> ~/.zshrc
    source ~/.zshrc # Or ~/.bash_profile if you use bash
    ```
3.  **Add your installed Java versions to jenv:**
    ```bash
    jenv add $(/usr/libexec/java_home)
    ```
    *(Repeat for each version you want to manage if Homebrew didn't automatically install them in the standard location)*
4.  **Set a global Java version:**
    ```bash
    jenv global 21.0
    ```
5.  **Set a project-specific Java version** (navigate into your project directory first):
    ```bash
    jenv local 17.0
    ```
6.  **Check which Java is active:**
    ```bash
    jenv version
    ```
# 🪟 Windows & JDK Setup Guide

This section outlines the detailed steps for setting up your **Java Development Kit (JDK)** and configuring your system environment on Windows.

---

### Step 1: Install Java Development Kit (JDK) 📥

1.  **Download the JDK Installer:** Download the official **Windows x64 Installer** for a recent **Long-Term Support (LTS) JDK** version (e.g., Java 21) from Oracle or an OpenJDK distribution site (like Adoptium).
2.  **Run the Installer:** Double-click the downloaded `.exe` file and follow the installation wizard's default prompts.
3.  **Set Environment Variables (Crucial Step):** This tells your operating system where the Java tools are located.
  * Search for "Environment Variables" in the Windows Start menu and select **Edit the system environment variables**.
  * In the System Properties window, click the **Environment Variables...** button.
  * **Set `JAVA_HOME`:** Under "System variables," click **New**.
    * Set **Variable name** to `JAVA_HOME`.
    * Set **Variable value** to your JDK installation path (e.g., `C:\Program Files\Java\jdk-21`).
  * **Update `Path`:** Find the **`Path`** variable, click **Edit**, and add a new entry: `%JAVA_HOME%\bin`. This ensures you can run Java commands from any directory.

     ****

4.  **Verify Java Installation:**
  * Open a **new** Command Prompt (`cmd`) window.
  * Run the following commands to confirm both the Runtime (`java`) and the Compiler (`javac`) are accessible:

    ```bash
    java -version
    javac -version
    ```

---

## 📂 Project Structure
- **src/**
- **└── edu/ccrm/**
    - **├── domain/ # Entities (Student, Course, Enrollment, Person, Enums)**
    - **├── service/ # Services (StudentService, CourseService, EnrollmentService, TranscriptService)**
    - **├── io/ # ImportExportService, BackupService**
    - **├── config/ # AppConfig (paths for import/export/backup)**
    - **└── cli/ # Main.java (menu-driven CLI)**

## 🔑 Features
- **Student Management**: Add, list, update, deactivate students.
- **Course Management**: Add, list, search/filter, update, deactivate courses.
- **Enrollment Management**: Enroll/unenroll students, record grades.
- **Transcript**: View all courses, grades, and compute GPA.
- **Reports (Streams)**:
    - Courses by Department
    - Courses by Semester
    - Top Courses by Enrollments
    - GPA Distribution (buckets: 9–10, 8-8.99, etc.)
- **File I/O (NIO.2 + Streams)**:
    - Import/Export students, courses, enrollments from CSV
    - Configurable directories
- **Backup**:
    - Export folder backed up into timestamped directory
    - Recursive folder size computation

### TABlE MAPPING
| Syllabus Topic | File/Class/Method Demonstration |
|---------|----|
| Inheritance & Abstraction | domain/Grade.java, domain/Semester.java |
| Polymorphism | service/CourseService.java (searchCourses() method with predicates) |
| Enums with fields/constructor | io/BackupService.java (createTimestampedBackup() using Files.copy/move) |
| NIO.2 (Copy/Move/Path)	 | config/AppConfig.java (static getInstance() method) |
| Design Pattern: Singleton	 | domain/Course.Builder (nested static class) |
| Design Pattern: Builder | util/FileUtil.java (calculateDirectorySize() method) |
| Recursion Utility | util/Comparators.java (e.g., Comparator<Student> for sorting by GPA) |

### Key Design Patterns Used

| Pattern | Location | Purpose |
|---------|----------|---------|
| **Singleton** | `AppConfig` | Single configuration instance |
| **Builder** | `Student.Builder` | Flexible object creation |
| **Service Layer** | `*Service.java` | Business logic separation |
| **DAO Pattern** | Service classes | Data access abstraction |
## 🧪 Testing the Application

### Sample Test Data
Create these CSV files in `test_data/import/` directory:

**students.csv**
```csv
id,regNo,fullName,email,status
S001,2023001,John Doe,john.doe@uni.edu,ACTIVE
S002,2023002,Jane Smith,jane.smith@uni.edu,ACTIVE
S003,2023003,Mike Johnson,mike.j@uni.edu,INACTIVE
S004,2023004,Sarah Wilson,sarah.w@uni.edu,ACTIVE
```
**courses.csv**
```csv
code,title,credits,instructor,semester,department
CS101,Introduction to Programming,3,Dr. Adams,SPRING,COMPUTER_SCIENCE
MATH201,Calculus I,4,Dr. Brown,FALL,MATHEMATICS
PHY101,Physics Fundamentals,3,Dr. Clark,SPRING,PHYSICS
ENG101,English Composition,3,Dr. Davis,SUMMER,ENGLISH
```
## ▶️ How to Compile & Run
### Prerequisites
- **JDK 17+** installed. Verify with:
  ```bash
  java -version
  javac -version
- **Compile** 
  ```bash
  javac -d bin src/edu/ccrm/**/*.java
- **Run** 
  ```bash
  java -cp bin edu.ccrm.cli.Main
  ```
---
