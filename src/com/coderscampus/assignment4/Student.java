package com.coderscampus.assignment4;

public class Student implements Comparable<Student> {
	private int studentId;
	private String studentName;
	private String course;
	private int grade;

	public Student(int studentId, String studentName, String course, int grade) {
		this.studentId = studentId;
		this.studentName = studentName;
		this.course = course;
		this.grade = grade;
	}

	public int getStudentId() {
		return studentId;
	}

	public String getStudentName() {
		return studentName;
	}

	public String getCourse() {
		return course;
	}

	public int getGrade() {
		return grade;
	}

	@Override
	public int compareTo(Student other) {
		// Sort by grade in descending order.
		// I prefer "other" over using "that".
		return Integer.compare(other.grade, this.grade);
	}

	public String toCSVString() {
		return studentId + "," + studentName + "," + course + "," + grade;
	}
}
