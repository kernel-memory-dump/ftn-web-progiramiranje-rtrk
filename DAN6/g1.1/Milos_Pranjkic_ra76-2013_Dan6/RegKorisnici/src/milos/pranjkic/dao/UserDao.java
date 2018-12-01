package milos.pranjkic.dao;

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

import com.sun.imageio.plugins.common.InputStreamAdapter;

import milos.pranjkic.model.User;
import sun.java2d.pipe.BufferedContext;

/**
 * Handles Data persistence related to user
 * 
 * @author student
 *
 */
public class UserDao {

	private static List<User> users = new ArrayList<>();

	private static final String FILE_NAME = "userFile.txt";

	private static Object lock = new Object();

	public static void addUser(User user) throws FileNotFoundException {
		users.add(user);
		writeFile();
	}

	public static List<User> getAllUsers() throws IOException {
		List <User> copyUser = new ArrayList<>();
		readFile();
		for(User user : users){
			User newUser = new User(user.getIme(), user.getTip());
			copyUser.add(newUser);
		}
		
		return copyUser;
	}

	private static void writeFile() throws FileNotFoundException {
		synchronized (lock) {
			File f = new File(FILE_NAME);
			FileOutputStream izlazniSteam = new FileOutputStream(f, true);
			PrintWriter printer = new PrintWriter(izlazniSteam);
			User user = users.get(users.size() - 1);
			printer.print(user.getIme() + ";" + user.getTip() + "\n");
			printer.close();
		}
	}

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
				if (parts.length != 2)
					continue;
				String ime = parts[0];
				String tip = parts[1];

				User user = new User(ime, tip);
				newUsers.add(user);
			}

			users = newUsers;

		}
	}

}
