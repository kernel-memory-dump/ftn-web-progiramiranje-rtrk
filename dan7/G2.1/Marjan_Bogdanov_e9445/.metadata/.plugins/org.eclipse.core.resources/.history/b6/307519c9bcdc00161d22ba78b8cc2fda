package isidor.rimski.dao;

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

import isidor.rimski.model.User;

/**
 * Handles Data Persistence related to User
 * 
 * @author student
 *
 */
public class UserDao {
	/**
	 * Current DAO state
	 */
	private static List<User> users = new ArrayList<>();
	/**
	 * File containing list of users, each row presents a single user in the
	 * following format: ime;tip ime;tip ime;tip
	 */
	private static final String FILE_NAME = "userFile.txt";

	private static Object lock = new Object();

	/**
	 * Persists current user to file
	 * 
	 * @param user
	 *            - new user to be added
	 * @throws FileNotFoundException
	 */
	public static void addUser(User user) throws FileNotFoundException {
		users.add(user);
		writeFile();
	}

	/**
	 * Retrieves list of users from file
	 * 
	 * @return - List of users from file
	 * @throws IOException 
	 */
	public static List<User> getAllUsers() throws IOException {
		readFile();
		List<User> coppyUsers = new ArrayList<>();
		// coppyUsers.addAll(users); //ovo pravi samo kopiju al moze da nam obrise
		
		for (User user : users) {
			User newUser = new User(user.getIme(), user.getTip());
			coppyUsers.add(newUser);
		}
		return coppyUsers;
	}
	/**
	 * 
	 * @throws FileNotFoundException
	 */
	private static void writeFile() throws FileNotFoundException {
		synchronized (lock) {
			File f = new File(FILE_NAME);
			FileOutputStream izlazniStream = new FileOutputStream(f, true); // true
																			// ce
																			// biti
																			// u
																			// append
																			// rezimu
																			// (dodaje
																			// se
																			// samo
																			// u
																			// fajl)
			PrintWriter printer = new PrintWriter(izlazniStream);
			User user = users.get(users.size() - 1);
			printer.print(user.getIme() + ";" + user.getTip() + "\r\n");
			printer.close();

		}

	}
	/**
	 * 
	 * @throws IOException
	 */
	private static void readFile() throws IOException {
		synchronized (lock) {
			File f = new File(FILE_NAME);
			FileInputStream ulazniStream = new FileInputStream(f);
			InputStreamReader reader = new InputStreamReader(ulazniStream);
			BufferedReader bufferedReader = new BufferedReader(reader);

			List<User> newUsers = new ArrayList<>();
			String line = null;
			while ((line = bufferedReader.readLine()) != null) {
				String[] parts = line.split(";");
				if (parts.length != 2) {
					continue;
				}
				String ime = parts[0];
				String tip = parts[1];
				User user = new User(ime, tip);
				newUsers.add(user);
			}
			users = newUsers;
		}

	}
}
