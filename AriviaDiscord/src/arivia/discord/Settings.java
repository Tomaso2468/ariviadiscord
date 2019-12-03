package arivia.discord;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.util.List;

public class Settings {
	public static synchronized boolean allowMaths(long id) {
		try {
			List<String> ids = Files.readAllLines(new File("maths.txt").toPath());
			for (String s : ids) {
				try {
					if (Long.valueOf(s) == id) {
						return false;
					}
				} catch (NumberFormatException e) {
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			return true;
		}
		return true;
	}
	public static synchronized boolean allowBees(long id) {
		try {
			List<String> ids = Files.readAllLines(new File("bees.txt").toPath());
			for (String s : ids) {
				try {
					if (Long.valueOf(s) == id) {
						return false;
					}
				} catch (NumberFormatException e) {
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			return true;
		}
		return true;
	}
	public static synchronized void banMaths(long id) {
		try {
			File f = new File("maths.txt");
			if (!f.exists()) {
				f.createNewFile();
			}
			PrintStream ps = new PrintStream(new FileOutputStream(f, true));
			ps.println(id + "");
			ps.flush();
			ps.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static synchronized void banBees(long id) {
		try {
			File f = new File("bees.txt");
			if (!f.exists()) {
				f.createNewFile();
			}
			PrintStream ps = new PrintStream(new FileOutputStream(f, true));
			ps.println(id + "");
			ps.flush();
			ps.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
