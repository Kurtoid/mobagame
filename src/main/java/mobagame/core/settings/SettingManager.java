package mobagame.core.settings;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;

/**
 * used to read and write local settings, IE default server, window size etc
 *
 * @author Kurt Wilson
 */
public class SettingManager {
	final public static String DEFAULT_SERVER_SETTINGS_FILE = "default_server_settings.conf";
	final public static String DEFAULT_CLIENT_SETTINGS_FILE = "default_client_settings.conf";

	public enum SettingFile {
		CLIENT_SETTINGS, SERVER_SETTINGS
	}



	EmptySetting root;
	private Path file;

	public SettingManager() {
		root = new EmptySetting();
		root.setName("root");
	}

	public SettingManager(SettingFile mode) throws FileNotFoundException{
		switch (mode) {
			case CLIENT_SETTINGS:
				file = Paths.get(DEFAULT_CLIENT_SETTINGS_FILE);
				break;
			case SERVER_SETTINGS:
				file = Paths.get(DEFAULT_SERVER_SETTINGS_FILE);
				break;
		}
		File f = file.toFile();
		if(!f.isFile()){
			throw new FileNotFoundException();
		}

		root = new EmptySetting();
		root.setName("root");
		readSettings();
	}

	public void openFile(Path p)  {
		file = p;
	}

	/**
	 * using the file set by openFile(Path), read it, and assign the settings to root
	 */
	public void readSettings() {
		Charset charset = StandardCharsets.UTF_8;
		try (BufferedReader reader = Files.newBufferedReader(file, charset)) {
			String line = null;
			int l = 0;
			while ((line = reader.readLine()) != null) {
				System.out.println("line: " + line);
				int splitPosition = line.indexOf('=');
				if (splitPosition <= 0) {
					continue;
				}
				String firstPart = line.substring(0, splitPosition);
				String secondPart = line.substring(splitPosition + 1, line.length());
				// System.out.println(firstPart);
				// System.out.println(secondPart);
				writeSetting(firstPart, secondPart);
				// System.out.println("line done: " + s.getSettingLine());
				l++;
			}
		} catch (IOException x) {
			System.err.format("IOException: %s%n", x);
		}
	}

	/**
	 * writes all settings under root to file set by openFile(Path)
	 */
	public void writeSettings() {
		try {
			BufferedWriter writer = Files.newBufferedWriter(file, StandardCharsets.UTF_8);
			Queue<EmptySetting> parentQueue = new ArrayDeque<>();
			// Set<Setting> settings = new HashSet();
			parentQueue.add(root);
			while (!parentQueue.isEmpty()) {
				EmptySetting s = parentQueue.poll();
				System.out.println("Checking " + s.getHeritage());
				Iterator It = s.children.entrySet().iterator();
				while (It.hasNext()) {
					Map.Entry<String, EmptySetting> pair = (Entry<String, EmptySetting>) It.next();
					parentQueue.add(pair.getValue());
				}
				if (s instanceof Setting && ((Setting) s).value != null) {
					writer.write(((Setting) s).getSettingLine() + "\n");
				}
			}
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * saves a single setting, and creates parents if needed
	 * doesnt actually right to file, so call writeSettings later
	 *
	 * @param name
	 * @param value
	 */
	public void writeSetting(String name, String value) {
		writeSetting(name, value, true);
	}


	private void writeSetting(String name, String value, boolean replace) {
		if (doesSettingExist(name)) {
			EmptySetting s = getSetting(name);
			if (s instanceof Setting) {
				((Setting) s).setValue(value);
			}
			return;
		}

		String[] keyNames = name.split("\\.");
		// System.out.println("keys: " + Arrays.toString(keyNames));
		String settingName = keyNames[keyNames.length - 1];

		Setting s;
		s = new Setting(settingName, value);

		int settingKeys = keyNames.length - 1;
		EmptySetting nodeParent = root;
		if (settingKeys > 0) {
			for (int i = 0; i < settingKeys; i++) {
				EmptySetting parentSearch = (nodeParent.children).get(keyNames[i]);
				if (parentSearch == null) {
					parentSearch = new EmptySetting();
					parentSearch.name = keyNames[i];
					parentSearch.parent = nodeParent;
					nodeParent.children.put(parentSearch.name, parentSearch);
					// System.out.println("created node: " + parentSearch.getHeritage());
				}
				nodeParent = parentSearch;
			}

		}
		nodeParent.children.put(s.name, s);
		s.parent = nodeParent;
		// System.out.println("node done: " + s.getSettingLine());

	}

	/**
	 * grab a setting using a fully qualified name
	 * call <b>after<b> writeSetting or readSettings
	 *
	 * @param name
	 * @return
	 */
	public Setting getSetting(String name) {
		String[] keyNames = name.split("\\.");
		// System.out.println("keys: " + Arrays.toString(keyNames));
		EmptySetting s = root;
		for (int i = 0; i < keyNames.length; i++) {
			// System.out.println("eval " + keyNames[i]);
			EmptySetting tmp = s.children.get(keyNames[i]);
			if (tmp == null) {
				return null;
			}
			s = tmp;
		}
		if (s instanceof Setting)
			return (Setting) s;
		else
			return null;
	}

	public boolean getBoolean(String name){
		Setting s = getSetting(name);
		return Boolean.parseBoolean(s.value);
	}

	boolean doesSettingExist(String name) {
		String[] keyNames = name.split("\\.");
		// System.out.println("keys: " + Arrays.toString(keyNames));
		String settingName = keyNames[keyNames.length - 1];
		EmptySetting s = root;
		for (int i = 0; i < keyNames.length; i++) {
			// System.out.println("eval " + keyNames[i]);
			EmptySetting tmp = s.children.get(keyNames[i]);
			if (tmp == null) {
				return false;
			}
			s = tmp;
		}
		return true;

	}

	void writeDefaultSettings() {

	}

	public static void main(String[] args) {
		SettingManager m = new SettingManager();
			System.out.println("OPENING FILE");
			m.openFile(Paths.get("settinds.conf"));
			System.out.println("\nREADING");
			m.readSettings();

			System.out.println("RESULT");
			System.out.println((m.root.children.toString()));
			if (!m.doesSettingExist("player.test"))
				m.writeSetting("player.test", Integer.toString(5));
			System.out.println("\n WRITING");
			m.writeSettings();

	}

}
