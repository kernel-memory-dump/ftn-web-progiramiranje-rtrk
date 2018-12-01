package uros.visekruna.dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import uros.visekruna.model.User;

/**
 * Data layer for User
 * @author student
 *
 */



public class UserDao {
	
	 private static final String FILE_NAME = "users.txt";
	 private static List<User> users = new ArrayList<>();
	 private static Object lock1 = new Object(); 
	 
	 
	 public static void addUser(User user) throws FileNotFoundException{
		 users.add(user);
		 saveFile();
		 		 
	 }
	 
	 /**
	  * ime; tip
	  * pera;free
	  * 
	  * 
	  * @throws FileNotFoundException
	  */
	 
	 
	 private static void saveFile() throws FileNotFoundException {
		 synchronized (lock1) {
		
			 File f = new File(FILE_NAME);
			 //open in append if file exists
			 FileOutputStream outputStream = new FileOutputStream(f, true);			 
			 PrintWriter printer = new PrintWriter(outputStream);
			 
			 User user = users.get(users.size()-1);		 			 			
			 printer.print(user.getIme() + ";" + user.getTip() );
			 printer.print("\n");
			 printer.flush();
			 printer.close();
			 
		}
		
	}
	public static List<User> getAllUsers() throws IOException{
		 
		 readFile();
		 List<User> copyUsers =new ArrayList<>();
		 
		 for (User user : users) {
			User newUser = new User(user.getIme(), user.getTip());
			copyUsers.add(newUser);
		}
		 
		 
		 return copyUsers;
		 
	 }

	private static void readFile() throws IOException {
	synchronized (lock1) {
		List<User> newUsers = new ArrayList<>();
		File f = new File(FILE_NAME);
		FileInputStream input = new FileInputStream(f);
		InputStreamReader reader = new InputStreamReader(input);
		BufferedReader bufferedReader = new BufferedReader(reader);
		String line = null;
		
		while((line = bufferedReader.readLine()) != null){
			
			String[] parts = line.split(";");
			if(parts.length !=2){
				
				return;
				
			}
			
			String ime = parts[0];
			String tip = parts[1];
			
			User newUser = new User(ime,tip);
			newUsers.add(newUser);
			
		}
		users = newUsers;
		bufferedReader.close();
		
		
	}
		
		
	}
}