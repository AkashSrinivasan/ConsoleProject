package com.management;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class Login {
	public static String LogingIn(File file){
		FileReader fileReader;
		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter the id = ");
		int id = scanner.nextInt();
		System.out.println("Enter the password = ");
		String password = scanner.next();
		String result = "";
		try {
			fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			try {
				String line = bufferedReader.readLine();
				while(line != null) {
					String[] word = line.split(" - ");
					if(line.contains("id") && Integer.parseInt(word[1])==id) {
						result +=line+"\n";
						line = bufferedReader.readLine();
						result +=line+"\n";
						line = bufferedReader.readLine();
						word = line.split(" - ");
						if(line.contains("password") && word[1].equals(password)) {
							result +=line+"\n";
							line = bufferedReader.readLine();
							result +=line+"\n";
							return result;
						}
						return "";
						
					}
					line = bufferedReader.readLine();
				}
				fileReader.close();
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			bufferedReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
}
