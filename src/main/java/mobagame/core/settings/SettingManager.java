package mobagame.core.settings;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.Map.Entry;

public class SettingManager {
    EmptySetting root;
    private Path file;

    public SettingManager() {
        root = new EmptySetting();
        root.setName("root");
    }

    public void openFile(Path p) throws FileNotFoundException {
        file = p;
    }

    public void readSettings() {
        Charset charset = StandardCharsets.UTF_8;
        try (BufferedReader reader = Files.newBufferedReader(file, charset)) {
            String line = null;
            int l = 0;
            while ((line = reader.readLine()) != null) {
                System.out.println("line: " + line);
                String[] parts = line.split("=");
                if (parts.length < 2) {
                    continue;
                }
                writeSetting(parts[0], parts[1]);
                // System.out.println("line done: " + s.getSettingLine());
                l++;
            }
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        }
    }

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

    void writeSetting(String name, String value) {
        writeSetting(name, value, true);
    }

    void writeSetting(String name, String value, boolean replace) {
        if (doesSettingExist(name)) {

        }

        String[] keyNames = name.split("\\.");
        System.out.println("keys: " + Arrays.toString(keyNames));
        String settingName = keyNames[keyNames.length - 1];

        Setting s;
        try {
            int val = Integer.parseInt(value);
            s = new IntSetting(settingName, val);
        } catch (NumberFormatException e) {
            s = new StringSetting(settingName, value);
        }

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
                    System.out.println("created node: " + parentSearch.getHeritage());
                }
                nodeParent = parentSearch;
            }

        }
        nodeParent.children.put(s.name, s);
        s.parent = nodeParent;
        System.out.println("node done: " + s.getSettingLine());

    }

    EmptySetting getSetting(String name) {
        String[] keyNames = name.split("\\.");
        System.out.println("keys: " + Arrays.toString(keyNames));
        EmptySetting s = root;
        for (int i = 0; i < keyNames.length; i++) {
            // System.out.println("eval " + keyNames[i]);
            EmptySetting tmp = s.children.get(keyNames[i]);
            if (tmp == null) {
                return null;
            }
            s = tmp;
        }
        return s;

    }

    boolean doesSettingExist(String name) {
        String[] keyNames = name.split("\\.");
        System.out.println("keys: " + Arrays.toString(keyNames));
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
        try {
            System.out.println("OPENING FILE");
            m.openFile(Paths.get("settings.conf"));
            System.out.println("\nREADING");
            m.readSettings();

            System.out.println("RESULT");
            System.out.println((m.root.children.toString()));
            if (!m.doesSettingExist("player.test"))
                m.writeSetting("player.test", Integer.toString(5));
            System.out.println("\n WRITING");
            m.writeSettings();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
