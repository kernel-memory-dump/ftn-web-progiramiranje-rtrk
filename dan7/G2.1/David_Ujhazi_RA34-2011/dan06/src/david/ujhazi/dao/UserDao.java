package david.ujhazi.dao;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.apache.jasper.tagplugins.jstl.core.ForEach;

import david.ujhazi.model.User;

public class UserDao {

	private static final String FILE_NAME = "users.txt";
	private static List<User> users = new ArrayList<>();
	private static Object lock1 = new Object();
	private static InputStream fileOutputStream;

	public static void addUser(User user) throws FileNotFoundException {
		users.add(user);
		saveFile();
	}

	private static void saveFile() throws FileNotFoundException {
		synchronized (lock1) {
			File f = new File(FILE_NAME);
			FileOutputStream fileOutputStream = new FileOutputStream(f, true);
			PrintWriter printWriter = new PrintWriter(fileOutputStream);
			
			User user = users.get(users.size()-1);
			
			printWriter.print(user.getIme() + ";" + user.getTip() + "\r\n");
			
			printWriter.flush();
			printWriter.close();
		}

	}

	/**
	 * Returns a copy of the list of users
	 * */
	public static List<User> getAllUsers() throws IOException {
		readFile();
		
		List<User> copyUsers = new ArrayList<>();
		for (User user : users) {
			copyUsers.add(new User(user.getIme(),user.getTip()));
		}
		
		return copyUsers;
	}

	private static void readFile() throws IOException {
		synchronized (lock1) {
			List<User> newUsers = new ArrayList<>();
			File f = new File(FILE_NAME);
			FileInputStream fileInputStream = new FileInputStream(f);
			InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

			String line = null;

			while ((line = bufferedReader.readLine()) != null) {
				String[] parts = line.split(";");
				if (parts.length != 2)
					break;

				String ime = parts[0];
				String tip = parts[1];
				User newUser = new User(ime, tip);
				newUsers.add(newUser);
			}

			users = newUsers;
			bufferedReader.close();

		}

	}
}
