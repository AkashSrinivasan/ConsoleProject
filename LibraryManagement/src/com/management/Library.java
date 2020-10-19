package com.management;

import java.io.File;
import java.io.IOException;
import java.time.temporal.ChronoUnit;
import java.util.Scanner;

public class Library {

	public static void main(String[] args) {
		boolean mainPage = true;
		String path = System.getProperty("user.dir")+"\\Library management";	
		File f = new File(path);
		f.mkdir();
		Admin.createAllFile(path);
		
		while(mainPage) {
			System.out.println("------------------------------------------------------");
			System.out.println("Welcome to Library");
			System.out.println("------------------------------------------------------");
			System.out.println("1 - student login");
			System.out.println("2 - staff login");
			System.out.println("3 - admin login");
			System.out.println("4 - signin");
			System.out.println("5 - exit");
			System.out.println("------------------------------------------------------");
			Scanner scanner = new Scanner(System.in);
				switch(scanner.nextByte()) {
				case 1:
					File StudentFile = new File(path+"\\Student.txt");
					boolean loggedIn = true;
					while(loggedIn) {
						String studentDetailsAsString = Login.LogingIn(StudentFile);
						if(studentDetailsAsString != "") {
							System.out.println("sucessfully logged in");
							
							System.out.println("HI User..!");
							System.out.println("************************************************");
							
							User student = Student.convertStudentStringToObject(studentDetailsAsString);
							boolean userIn = true;
							while(userIn) {
								System.out.println("1 - Display barrowed books");
								System.out.println("2 - Take a new book form library");
								System.out.println("3 - give a book back to library");
								System.out.println("4 - logout");
								switch(scanner.nextInt()) {
								case 1:
									Student.displayBook(student);
									break;
								case 2:
									Book.getBook(student);
									break;
								case 3:
									Book.returnBook(student);
									break;
								case 4:
									userIn = false;
								}
							}
							loggedIn = false;
						}else {
							System.out.println("please enter the correct id and password");
							System.out.println("1 - Home Page");
							System.out.println("2 - Login again");
							int checkUserInput = scanner.nextInt();
							if(checkUserInput == 1) {
								loggedIn = false;
							}else {
								loggedIn = true;
							}
						}
					}
					break;
				case 2:
					File staffFile = new File(path+"\\Staff.txt");
					boolean staffLoggedIn = true;
					while(staffLoggedIn) {
						String staffDetailsAsString = Login.LogingIn(staffFile);
						if(staffDetailsAsString != "") {
							System.out.println("sucessfully logged in");
							
							System.out.println("HI User..!");
							System.out.println("************************************************");
							
							User staff = Staff.convertStaffStringToObject(staffDetailsAsString);
							System.out.println(staffDetailsAsString);
							boolean userIn = true;
							while(userIn) {
								System.out.println("1 - Display barrowed books");
								System.out.println("2 - Take a new book form libriary");
								System.out.println("3 - give a book back to libriary");
								System.out.println("4 - logout");
								switch(scanner.nextInt()) {
								case 1:
									Staff.displayBook(staff);
									break;
								case 2:
									Book.getBook(staff);
									break;
								case 3:
									Book.returnBook(staff);
									break;
								case 4:
									userIn = false;
								}
							}
							staffLoggedIn = false;
						}else {
							System.out.println("please enter the correct id and password");
							System.out.println("1 - Home Page");
							System.out.println("2 - Login again");
							int checkUserInput = scanner.nextInt();
							if(checkUserInput == 1) {
								staffLoggedIn = false;
							}else {
								staffLoggedIn = true;
							}
						}
					}
					break;
				case 3:
					System.out.println("Enter the id = ");
					int id = scanner.nextInt();
					System.out.println("Enter the password = ");
					String password = scanner.next();
					if(id == Admin.ADMIN_ID && password.equals(Admin.ADMIN_PASSWORD)) {
						System.out.println("Welocme Admin");
						boolean adminLoggedIn = true;
						while(adminLoggedIn) {
							System.out.println("1 - Add new book");
							System.out.println("2 - display all student and staff list");
							System.out.println("3 - display Book List");
							System.out.println("4 - Logout");
							switch (scanner.nextInt()) {
							case 1:
								Admin.addBook();
								break;
							case 2:
								Admin.displayAllUser();
								break;
							case 3:
								Admin.displayAllBooks();
								break;
							case 4:
								adminLoggedIn = false;
							}
						}
					}
					break;
				case 4:
					new Signin();
					break;
				case 5:
					mainPage = false;
					break;
				}
//			}
		}
		
	}
}
