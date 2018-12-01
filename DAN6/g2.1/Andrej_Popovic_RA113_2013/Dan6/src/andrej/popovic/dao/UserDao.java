package andrej.popovic.dao;

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

import andrej.popovic.model.User;

/**
 * Data layer for User
 * @author student
 *
 */
public class UserDao
{
	private static List<User> users = new ArrayList<>();
	private static final String FILE_NAME = "users.txt";
	
	static Object lock1 = new Object();
	
	public static void addUser(User user) throws FileNotFoundException
	{
		users.add(user);
		saveFile();
	}
	
	private static void saveFile() throws FileNotFoundException
	{
		synchronized (lock1)
		{
			File f = new File(FILE_NAME);
			FileOutputStream outStream = new FileOutputStream(f, true);
			PrintWriter writer = new PrintWriter(outStream);
			
			User user = users.get(users.size() - 1);
			
			writer.print(user.ime + ";" + user.type + "\r\n");
			writer.flush();
			writer.close();
		}
	}

	public static List<User> getAllUsers() throws IOException
	{
		readFile();
		List<User> tmp = new ArrayList<>();
		
		for (User user : users)
		{
			User newUser = new User(user.ime, user.type);
			tmp.add(newUser);
		}
		
		return tmp;
	}

	private static void readFile() throws IOException
	{
		synchronized (lock1)
		{
			List<User> newUsers = new ArrayList<>();
			File f = new File(FILE_NAME);
			FileInputStream inStream = new FileInputStream(f);
			InputStreamReader reader = new InputStreamReader(inStream);
			BufferedReader bufReader = new BufferedReader(reader);
			
			String line = null;
			while ( (line = bufReader.readLine()) != null)
			{
				String[] parts = line.split(";");
				if (parts.length != 2)
				{
					break;
				}
				String ime = parts[0];
				String tip = parts[1];
				User newUser = new User(ime, tip);
				newUsers.add(newUser);
			}
			//replace old list with loaded one
			users = newUsers;
			bufReader.close();
		}
		
	}


}
