package eleonora.nan.dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import org.apache.jasper.tagplugins.jstl.core.ForEach;

import eleonora.nan.model.User;

/**
 * Handles Data Persistence related to user
 * 
 * @author student
 *
 */
public class UserDao {

	/**
	 * Current DAO state
	 */
	private static List<User> users = new ArrayList<>();
	
	private static Object lock = new Object();
	
	/**
	 * File containing list of users, each row presents single user
	 * in the following format:
	 * ime;tip\n
	 * ime;tip\n
	 * ime;tip\n
	 */
	private static final String FILE_NAME = "usersFile.txt";
	
	/**
	 * Persists current user to file
	 * 
	 * @param user
	 *            - New user to be added
	 * @throws FileNotFoundException 
	 */
	

	
	public static void addUser(User user) throws FileNotFoundException {
		users.add(user);
		writeFile();
	}

	/**
	 * Retrieves list of users from file
	 * @return - List of users from file
	 * @throws IOException 
	 */
	public static List<User> getAllUsers() throws IOException{
		
		readFile();
		List<User> copyUsers = new ArrayList<>();
		
		for(User user : users){
			User newUser = new User(user.getIme(), user.getTip());
			copyUsers.add(newUser);
		}
		
		return copyUsers;
	}
	
	/**
	 * Write list to file
	 * @throws FileNotFoundException
	 */
	private static void writeFile() throws FileNotFoundException {
		
		synchronized (lock) {
			
			File f = new File(FILE_NAME);
			FileOutputStream izlazniStream = new FileOutputStream(f, true);
			PrintWriter printer = new PrintWriter(izlazniStream);
			User user = users.get(users.size() - 1);
			printer.print(user.getIme() + ";" + user.getTip() + "\r\n");
			printer.close();
			
		}
	}

	/**
	 * Read list from file
	 * @throws IOException
	 */
	private static void readFile() throws IOException {
		
		synchronized (lock) {
			File f = new File(FILE_NAME);
			List<User> newUser = new ArrayList<>();

			FileInputStream ulazniStream = new FileInputStream(f);
			InputStreamReader reader = new InputStreamReader(ulazniStream);
			BufferedReader bufferedReader = new BufferedReader(reader);
			
			String line = null;
			
			while( (line = bufferedReader.readLine()) != null ){
				
				String[] parts = line.split(";");
				
				if(parts.length != 2){
					continue;
				}
				
				String ime = parts[0];
				String tip = parts[1];
				User user = new User(ime, tip);
				newUser.add(user);
			}
			
			users = newUser;
			
		}
	}
}
