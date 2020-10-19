package com.management;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class Student extends User{
//	int id;
//	String name;
//	String password;
//	ArrayList<Book> booksDetail = new ArrayList<>();
//	
//	public Student(int id, String name, String password) {
//		this.id = id;
//		this.name = name;
//		this.password = password;
//		this.booksDetail = new ArrayList<>();
//	}

	public Student(int id, String name, String password) {
		super(id, name, password);
	}

	public static User convertStudentStringToObject(String studentDetail) {
		String[] lines = studentDetail.split("\\r?\\n", -1);
		User student = new User(0, null, null);
		String[] splitedId = lines[0].split(" - ");
		student.id = Integer.parseInt(splitedId[1]);
		String[] splitedName = lines[1].split(" - ");
		student.name = splitedName[1];
		String[] splitedBookDetails = lines[3].split(" - ");
		String onlyBookDetails = splitedBookDetails[1];
		if(!onlyBookDetails.equals("null")) {
			String[] multipleBook = onlyBookDetails.split(",");
			for(int i=0;i<multipleBook.length;i++) {
				String[] indigualBookDetail = multipleBook[i].split(" -> ");
				int bookid = Integer.parseInt(indigualBookDetail[0]);
				String bookname = indigualBookDetail[1];
				int bid = Integer.parseInt(indigualBookDetail[2]);
				LocalDateTime bon = LocalDateTime.parse(indigualBookDetail[3]);
				LocalDateTime duedate= LocalDateTime.parse(indigualBookDetail[4]);
				student.booksDetail.add(new Book(bookid,bookname,bid,bon,duedate));
			}
		}else {
			student.booksDetail = null;
		}
		return student;
	}
	
	public static BufferedReader getAllStudent(){
		File studentFile = new File(System.getProperty("user.dir")+"\\Library management\\Student.txt");
		FileReader fileReader;
		BufferedReader studentBufferedReader = null;
		try {
			fileReader = new FileReader(studentFile);
			studentBufferedReader = new BufferedReader(fileReader);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return studentBufferedReader;
	}
	
	public static void displayBook(User student) {
		if(student.booksDetail != null && student.booksDetail.size()>0) {
			String bookAsString = student.booksDetail.size() >1 ? "books":"book";
			System.out.println("You have borrowed "+student.booksDetail.size()+" "+ bookAsString);
			student.booksDetail.forEach(book -> {
				System.out.println("Book Name = "+book.name);
				System.out.println("Return the book before "+ book.dueDate.toLocalDate() );
				System.out.println("Valid only for "+ ChronoUnit.DAYS.between(LocalDateTime.now(), book.dueDate)+" days");
				System.out.println();
			});
		}else {
			System.out.println("No books to display..!");
		}
	}
	
	
	public static boolean checkStudentAlreadyPresent(User student) {
		File studentFile = new File(System.getProperty("user.dir")+"\\Library management\\Student.txt");
		FileReader filereader;
		try {
			filereader = new FileReader(studentFile);
			BufferedReader bufferedReader =  new BufferedReader(filereader);
			try {
				String line = bufferedReader.readLine();
				ArrayList<Integer> allStudentId = new ArrayList<>();
				while(line != null) {
					String[] word = line.split(" - ");
					if(line.contains("id")) {
						allStudentId.add(Integer.parseInt(word[1]));
					}
					line = bufferedReader.readLine();
				}
				filereader.close();
				if(allStudentId.contains(student.id)) {
					return true;
				}
				FileWriter fileWriter = new FileWriter(studentFile,true);
				BufferedWriter bufferedWriter  = new BufferedWriter(fileWriter);
				bufferedWriter.write("id - "+student.id + "\n");
				bufferedWriter.write("name - "+student.name + "\n");
				bufferedWriter.write("password - "+student.password + "\n");
				bufferedWriter.write("book - null"+"\n");
				bufferedWriter.flush();
				bufferedWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return false;
	}
}
