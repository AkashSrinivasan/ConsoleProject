package com.management;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class Book{
	int id;
	String name;
	int barrowerId;
	LocalDateTime borrowedOn;
	LocalDateTime dueDate;
	
	Book(int id,String name,int barrowerId,LocalDateTime borrowedOn,LocalDateTime dueDate ){
		this.id = id;
		this.name = name;
		this.barrowerId = barrowerId;
		this.borrowedOn = borrowedOn;
		this.dueDate = dueDate;
	}
	Book(){
		
	}
	
	public static ArrayList<Book> convertAllBookToObject(BufferedReader bookReader){
		ArrayList<Book> allBookList = new ArrayList<>();
		String line;
		try {
			line = bookReader.readLine();
			while(line != null) {
				String[] word = line.split(" - ");
				if(line.contains("bookId")) {
					Book book = new Book();
					book.id =Integer.parseInt(word[1]) ;
					line = bookReader.readLine();
					word = line.split(" - ");
					book.name = word[1];
					line = bookReader.readLine();
					word = line.split(" - ");
					book.barrowerId =Integer.parseInt(word[1]);
					line = bookReader.readLine();
					word = line.split(" - ");
					if(!word[1].equals("null")) {
						book.borrowedOn = LocalDateTime.parse(word[1]);
					}else {
						book.borrowedOn = null;
					}
					line = bookReader.readLine();
					word = line.split(" - ");
					if(!word[1].equals("null")) {
						book.dueDate =LocalDateTime.parse(word[1]);
					}else {
						book.dueDate = null;
					}
					allBookList.add(book);
				}
				line = bookReader.readLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return allBookList;
	}
	
	
	public static boolean displayAvailableBook(ArrayList<Book> allBooks) {
		ArrayList<Book> availableBooks = new ArrayList<>();
		allBooks.stream().forEach((i)-> {
			if(i.barrowerId == 0) {
				availableBooks.add(i);
			}
		});
		if(availableBooks.size()==0) {
			System.out.println("No book are available right now.!");
			return false;
		}else {
			System.out.println("Only these books are available right now.!");
			int[] bookCount = {0};
			allBooks.stream().forEach((i)-> {
				if(i.barrowerId == 0) {
					System.out.println("Book - "+ (bookCount[0]+1));
					bookCount[0] = bookCount[0]+1;
					System.out.println("----------------------------------------");
					availableBooks.add(i);
					System.out.println("Book id = "+i.id);
					System.out.println("Book Name = "+i.name);
					System.out.println("");
				}
			});
		}
		return true;
	}
	
	public static String convertBookDetailsToString(ArrayList<Book> bookDetails,Book newlyAddedBook) {
		String detailsAsString = "book - ";
		if(bookDetails!=null) {
			for(Book i: bookDetails) {
			detailsAsString+=i.id+" -> "+i.name+" -> "+i.barrowerId+" -> "+i.borrowedOn+" -> "+i.dueDate+",";
			}
		}
		if(newlyAddedBook != null) {
			detailsAsString+=newlyAddedBook.id+" -> "+newlyAddedBook.name+" -> "+newlyAddedBook.barrowerId+" -> "+newlyAddedBook.borrowedOn+" -> "+newlyAddedBook.dueDate+",";
		}
		if(bookDetails !=null && newlyAddedBook == null ) {
			detailsAsString+="null";
		}
		return detailsAsString;
	}
	
	
	public static  void setVariable(int lineNumber, String data, String filepath) throws IOException {
	    Path path = Paths.get(filepath);
	    List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
	    lines.set(lineNumber, data);
	    Files.write(path, lines, StandardCharsets.UTF_8);
	}
	
	
	public static void  getBook(User user) {
		ArrayList<Book> allBooks = Admin.getAllBooks();
		
		if(allBooks==null) {
			System.out.println("No book are available right now.!");
//			return allBooks;
		}
		else {
			if(Book.displayAvailableBook(allBooks)) {
				boolean validBook = true;
				while(validBook){
					System.out.println("which book you need, please enter the book id = ");
					Scanner scanner = new Scanner(System.in);
					int id = scanner.nextInt();
					Book book= null;
					for(Book b : allBooks) {
						if(b.id==id ) {
							book = b;
							break;
						}
					}
					if(book != null) {
						allBooks.remove(book);
						book.barrowerId = user.id;
						book.borrowedOn = LocalDateTime.now();
						book.dueDate = book.borrowedOn.plusDays(15);
						Iterator<Book> itr = allBooks.iterator();
						while (itr.hasNext()) {
				            Book bookToDelete = itr.next();
				            if (bookToDelete.id == user.id) {
				                itr.remove();
				                break;
				            }
				        }
						allBooks.add(book);
						Admin.addBookToFile(allBooks);
						repleceInStudentFile(user.id,user.booksDetail,book);
						System.out.println("repleced sucessfully");
						validBook = false;
					}else {
						System.out.println("please enter the vaild book id");
					}
				}
			}
			user.booksDetail = allBooks;
		}
		
	}
	
	public static void returnBook(User user){
		ArrayList<Book> allbooks = user.booksDetail;
		if(user.booksDetail != null) {
			user.booksDetail.forEach(i -> {
				System.out.print("Book Id = "+i.id+" || ");
				System.out.println("Valid till = "+ ChronoUnit.DAYS.between(LocalDateTime.now(), i.dueDate));
			});
			boolean validBookId = true;
			while(validBookId) {
				System.out.println("Which book you need to give back, enter the id of the book = ");
				Scanner scanner = new Scanner(System.in);
				int id = scanner.nextInt();
				Book book= null;
				for(Book b : allbooks) {
					if(b.id==id ) {
						book = b;
						break;
					}
				}
				if(book!=null) {
					allbooks.remove(book);
					repleceInStudentFile(user.id,allbooks,null);
					Admin.deleteUserFromBookDetails(user.id);
					System.out.println("updated sucessfully");
					validBookId = false;
				}else {
					System.out.println("This book id is not valid");
				}
			}
		}else {
			System.out.println("no books in you stack");
		}
		user.booksDetail = allbooks;
	}
	
	
	public static void repleceInStudentFile(int id,ArrayList<Book> bookDetails,Book newlyAddedBook) {
		String newString = convertBookDetailsToString(bookDetails,newlyAddedBook);
		File studentFile = new File(System.getProperty("user.dir")+"\\Library management\\Student.txt");
		FileReader filereader;
		try {
			filereader = new FileReader(studentFile);
			BufferedReader bufferedReader =  new BufferedReader(filereader);
			try {
				String line = bufferedReader.readLine();
				String oldString = "";
				int linenumber = 0;
				while(line != null) {
					String[] word = line.split(" - ");
					if(line.contains("id") && Integer.parseInt(word[1])==id) {
						linenumber++;
						line = bufferedReader.readLine();
						linenumber++;
						line = bufferedReader.readLine();
						linenumber++;
						line = bufferedReader.readLine();
						oldString = line;
						setVariable(linenumber,newString,System.getProperty("user.dir")+"\\Library management\\Student.txt");
						break;
					}
					linenumber++;
					line = bufferedReader.readLine();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}
}
