package com.management;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class Staff extends User {


public Staff(int id, String name, String password) {
		super(id, name, password);
	}
	
	public static User convertStaffStringToObject(String staffDetail) {
		String[] lines = staffDetail.split("\\r?\\n", -1);
		User staff = new User(0, null, null);
		String[] splitedId = lines[0].split(" - ");
		staff.id = Integer.parseInt(splitedId[1]);
		String[] splitedName = lines[1].split(" - ");
		staff.name = splitedName[1];
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
				staff.booksDetail.add(new Book(bookid,bookname,bid,bon,duedate));
			}
		}else {
			staff.booksDetail = null;
		}
		return staff;
	}
	
	public static BufferedReader getAllStaff(){
		File staffFile = new File(System.getProperty("user.dir")+"\\Library management\\Staff.txt");
		FileReader fileReader;
		BufferedReader staffBufferedReader = null;
		try {
			fileReader = new FileReader(staffFile);
			staffBufferedReader = new BufferedReader(fileReader);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return staffBufferedReader;
	}
	
	
	public static void displayBook(User staff) {
		if(staff.booksDetail != null && staff.booksDetail.size()>0) {
			String bookAsString = staff.booksDetail.size() >1 ? "books":"book";
			System.out.println("You have borrowed "+staff.booksDetail.size()+" "+ bookAsString);
			staff.booksDetail.forEach(book -> {
				System.out.println("Book Name = "+book.name);
				System.out.println("Return the book before "+ book.dueDate.toLocalDate() );
				System.out.println("Valid only for "+ ChronoUnit.DAYS.between(LocalDateTime.now(), book.dueDate)+" days");
				System.out.println();
			});
		}else {
			System.out.println("No books to display..!");
		}
	}
	
	
	public static boolean checkStaffAlreadyPresent(User staff){
		File staffFile = new File(System.getProperty("user.dir")+"\\Library management\\Staff.txt");
		FileReader filereader;
		try {
			filereader = new FileReader(staffFile);
			BufferedReader bufferedReader =  new BufferedReader(filereader);
			try {
				String line = bufferedReader.readLine();
				ArrayList<Integer> allStafftId = new ArrayList<>();
				while(line != null) {
					String[] word = line.split(" - ");
					if(line.contains("id")) {
						allStafftId.add(Integer.parseInt(word[1]));
					}
					line = bufferedReader.readLine();
				}
				filereader.close();
				if(allStafftId.contains(staff.id)) {
					return true;
				}
				FileWriter fileWriter = new FileWriter(staffFile,true);
				BufferedWriter bufferedWriter  = new BufferedWriter(fileWriter);
				bufferedWriter.write("id - "+staff.id + "\n");
				bufferedWriter.write("name - "+staff.name + "\n");
				bufferedWriter.write("password - "+staff.password + "\n");
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
