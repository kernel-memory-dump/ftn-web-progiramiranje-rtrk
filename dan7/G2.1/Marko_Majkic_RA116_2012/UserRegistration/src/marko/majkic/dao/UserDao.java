package marko.majkic.dao;

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

import marko.majkic.model.User;

/**
 * Data layer for User
 * @author majki
 *
 */

public class UserDao {
	
	private static final String FILE_NAME = "D://Temp/users1.txt";
	private static List<User> users = new ArrayList<>();
	private static Object lock1 = new Object();
	
	public static void addUser(User user) throws FileNotFoundException {
		users.add(user);
		saveFile();
	}
	
	private static void saveFile() throws FileNotFoundException {
		synchronized (lock1) {
			File f = new File(FILE_NAME);
			//open in append if file already exists
			FileOutputStream outputStream = new FileOutputStream(f, true);
			PrintWriter printer = new PrintWriter(outputStream);
			User user = users.get(users.size() - 1);
			
			if(!user.getName().equals(""))
			{
				printer.print(user.getName() + ";" + user.getType() + "\n");
				printer.flush();
				printer.close();
			}
		}
	}
	
	public static List<User> getAllUsers() throws IOException {
		readFile();
		
		List<User> copyUser = new ArrayList<>(); 
		
		for (User user : users) {
			User newUser = new User(user.getName(), user.getType());
			copyUser.add(newUser);
		}
		
		return copyUser;
	}
	
	private static void readFile() throws IOException
	{
		synchronized (lock1) {
			List<User> newUsers = new ArrayList<>();
			File f = new File(FILE_NAME);
			FileInputStream inputStream = new FileInputStream(f);
			InputStreamReader reader = new InputStreamReader(inputStream);
			BufferedReader bufferedReader = new BufferedReader(reader);
			
			String line = null;
			
			while((line = bufferedReader.readLine()) != null) {
				//name;type
				String[] parts = line.split(";");
				if(parts.length != 2) {
					break; 
				}
				String name = parts[0];
				String type = parts[1];
				
				User newUser = new User(name, type);
				newUsers.add(newUser);
			}
			//replace old list with newly loaded one
			users = newUsers;
			bufferedReader.close();
		}
	}
}
