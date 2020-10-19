package com.management;

import java.util.Scanner;

public class Signin {
	boolean addStudent(int id, String userName, String password) {
//		Student student = new Student(id, userName, password);
		User student = new User(id,userName,password);
		if(Student.checkStudentAlreadyPresent(student)) {
			return false;
		}
		return true;
	}
	
	boolean addStaff(int id, String userName, String password) {
		User staff = new User(id,userName,password);
		if(Staff.checkStaffAlreadyPresent(staff)) {
			return false;
		}
		return true;
	}
	
	Signin(){
		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter the Id = ");
		int id = scanner.nextInt();
		System.out.println("user name = ");
		String userName = scanner.next();
		System.out.println("Enter the password = ");
		String password = scanner.next();
		System.out.println("student / staff");
		switch(scanner.next()) {
		case "student":
			if(addStudent(id,userName,password)) {
				System.out.println("student added sucessfully");
			}else {
				System.out.println("student already present, please login");
			}
			break;
		case "staff":
			if(addStaff(id,userName,password)) {
				System.out.println("staff added sucessfully");
			}else {
				System.out.println("staff already present, please login");
			}
			break;
		}
	}

}
