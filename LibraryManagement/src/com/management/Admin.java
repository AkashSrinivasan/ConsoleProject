package com.management;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class Admin {
	final static int ADMIN_ID = 123;
	final static String ADMIN_PASSWORD = "root";
	
	public static void addBook() {
		Scanner scanner = new Scanner(System.in);
		Book book = new Book();
		System.out.println("Book Id = ");
		book.id = scanner.nextInt();
		System.out.println("book name = ");
		book.name = scanner.next();
		File file = new File(System.getProperty("user.dir")+"\\Library management\\AllBooks.txt");
		try {
			BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file,true));
			bufferedWriter.write("bookId - "+book.id+"\n");
			bufferedWriter.write("bookName - "+book.name+"\n");
			bufferedWriter.write("barrowerId - "+book.barrowerId+"\n");
			bufferedWriter.write("borrowedOn - "+book.borrowedOn+"\n");
			bufferedWriter.write("dueDate - "+book.dueDate+"\n");
			bufferedWriter.flush();
			bufferedWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Book added sucessfully");
	}
	
	public static void createAllFile(String path){
		File studentFile = new File(path+"\\Student.txt");
		try {
			studentFile.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		File staffFile = new File(path+"\\Staff.txt");
		try {
			staffFile.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		File allBooksFile = new File(path+"\\AllBooks.txt");
		try {
			allBooksFile.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void addBookToFile(ArrayList<Book> allBooks) {
		File file = new File(System.getProperty("user.dir")+"\\Library management\\AllBooks.txt");
		try {
			file.delete();
			file.createNewFile();
			BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
			for(Book i:allBooks) {
				try {
					bufferedWriter.write("bookId - "+i.id+"\n");
					bufferedWriter.write("bookName - "+i.name+"\n");
					bufferedWriter.write("barrowerId - "+i.barrowerId+"\n");
					bufferedWriter.write("borrowedOn - "+i.borrowedOn+"\n");
					bufferedWriter.write("dueDate - "+i.dueDate+"\n");
					
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			bufferedWriter.flush();
			bufferedWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void displayAllUser() {
		display(Student.getAllStudent(),"student");
		System.out.println("\n");
		display(Staff.getAllStaff(),"staff");
	}
	
	public static void displayAllBooks() {
		File file = new File(System.getProperty("user.dir")+"\\Library management\\AllBooks.txt");
		try {
			BufferedReader allBooks = new BufferedReader(new FileReader(file));
			display(allBooks,"book");
			allBooks.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static ArrayList<Book> getAllBooks() {
		File file = new File(System.getProperty("user.dir")+"\\Library management\\AllBooks.txt");
		ArrayList<Book> allBooks = null;
		try {
			BufferedReader BookReader = new BufferedReader(new FileReader(file));			
			allBooks= Book.convertAllBookToObject(BookReader);
			BookReader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return allBooks;
	}
	
	public static void display(BufferedReader bufferedReader,String string) {
		String line;
		try {
			line = bufferedReader.readLine();
			if(line != null) {
				System.out.println("Displaying All "+string+" list");
				while(line != null) {
					if(!line.contains("password")) {
						System.out.println(line);
					}
					line = bufferedReader.readLine();
				}
				bufferedReader.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void deleteUserFromBookDetails(int id) {
//		System.out.println("id==="+id);
		
		ArrayList<Book> allBooks = getAllBooks();
		
		for(Book book:allBooks) {
			System.out.println("id+"+book.id);
			if(id == book.barrowerId) {
				book.barrowerId = 0;
				book.borrowedOn = null;
				book.dueDate = null;
				break;
			}
		}
		
		for(Book book:allBooks) {
			System.out.println("id+"+book.id);
			System.out.println("barrowerId"+book.barrowerId);
			System.out.println("borrowedOn"+book.borrowedOn);
			System.out.println("duedate"+book.dueDate);
			System.out.println("");
		}
		addBookToFile(allBooks);
		
	}
}
