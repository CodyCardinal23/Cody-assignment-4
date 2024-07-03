package com.coderscampus.assignment4;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

public class StudentManager {

	public static void main(String[] args) {
		String csvFile = "src/student-master-list.csv";

		// Read and parse CSV file.
		Student[] students = parseCSV(csvFile);

		System.out.println("Total students parsed: " + students.length);

		// Separate students by course and sort by grade.
		separateAndSortStudents(students);
	}

	private static Student[] parseCSV(String csvFile) {
		Student[] students = new Student[100]; // There are 100 students.
		int index = 0;

		try (BufferedReader reader = new BufferedReader(new FileReader(csvFile))) {
			reader.readLine();

			String line;
			while ((line = reader.readLine()) != null) {
				String[] data = line.split(",");

				// Parse CSV fields.
				int studentId = Integer.parseInt(data[0]);
				String studentName = data[1];
				String course = data[2];
				int grade = Integer.parseInt(data[3]);

				// Create Student object and add to array.
				students[index] = new Student(studentId, studentName, course, grade);
				index++;
			}
		} catch (IOException | NumberFormatException e) {
			System.err.println("Error parsing CSV file: " + e.getMessage());
		}

		return students;
	}

	private static Student[] filterStudentsByCourse(Student[] students, String coursePrefix) {
		int count = 0;
		for (Student student : students) {
			if (student != null && student.getCourse().startsWith(coursePrefix)) {
				count++;
			}
		}

		Student[] filteredStudents = new Student[count];
		int index = 0;
		for (Student student : students) {
			if (student != null && student.getCourse().startsWith(coursePrefix)) {
				filteredStudents[index++] = student;
			}
		}
		return filteredStudents;
	}

	private static void separateAndSortStudents(Student[] students) {
		// Separate students into courses.
		Student[] course1Students = filterStudentsByCourse(students, "COMPSCI");
		Student[] course2Students = filterStudentsByCourse(students, "APMTH");
		Student[] course3Students = filterStudentsByCourse(students, "STAT");

		System.out.println("COMPSCI students: " + course1Students.length);
		System.out.println("APMTH students: " + course2Students.length);
		System.out.println("STAT students: " + course3Students.length);

		// Sort students in each course by grade in descending order.
		Arrays.sort(course1Students);
		Arrays.sort(course2Students);
		Arrays.sort(course3Students);

		// Write sorted students to CSV files.
		writeStudentsToCSV(course1Students, "course1.csv");
		writeStudentsToCSV(course2Students, "course2.csv");
		writeStudentsToCSV(course3Students, "course3.csv");
	}

	private static void writeStudentsToCSV(Student[] students, String fileName) {
		try (FileWriter writer = new FileWriter(fileName)) {
			// Write header.
			writer.write("Student ID,Student Name,Course,Grade\n");

			// Write each student.
			for (Student student : students) {
				writer.write(student.toCSVString() + "\n");
			}

			System.out.println("Successfully wrote " + students.length + " students to " + fileName);
		} catch (IOException e) {
			System.err.println("Failed to write " + fileName + ": " + e.getMessage());
		}
	}
}