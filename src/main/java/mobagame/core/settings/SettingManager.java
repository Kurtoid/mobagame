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

public class SettingManager {
    Setting root;
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
                System.out.println("Parts: " + Arrays.toString(parts));

                if (parts.length > 2) {
                    System.out.println("setting error on line " + l);
                    continue;
                }

                String[] keyNames = parts[0].split("\\.");
                System.out.println("keys: " + Arrays.toString(keyNames));
                String settingName = keyNames[keyNames.length - 1];

                Setting s;
                try {
                    int val = Integer.parseInt(parts[0]);
                    s = new IntSetting(settingName, val);
                } catch (NumberFormatException e) {
                    s = new StringSetting(settingName, parts[1]);
                }

                int settingKeys = keyNames.length - 1;
                Setting nodeParent = root;
                if (settingKeys > 0) {
                    for (int i = 0; i < settingKeys; i++) {
                        Setting parentSearch = null;
                        Iterator iter = nodeParent.children.iterator();
                        System.out.println("looking for " + keyNames[i]);
                        while (iter.hasNext()) {
                           Setting tmp = (Setting) iter.next();
                            if (tmp.name.equals(keyNames[i])) {
                                System.out.println("found " + tmp.getHeritage());
                                parentSearch = tmp;
                                break;
                            }
                        }
                        if (parentSearch == null) {
                            parentSearch = new EmptySetting();
                            parentSearch.name = keyNames[i];
                            parentSearch.parent = nodeParent;
                            nodeParent.children.add(parentSearch);
                            System.out.println("created node: " + parentSearch.getHeritage());
                        }
                        nodeParent = parentSearch;
                    }

                }
                nodeParent.children.add(s);
                s.parent = nodeParent;
                System.out.println("line done: " + s.getSettingLine());
                l++;
            }
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        }
    }

    public void writeSettings() {
        try {
            BufferedWriter writer = Files.newBufferedWriter(file, StandardCharsets.UTF_8);
            Queue<Setting> parentQueue = new ArrayDeque<>();
            Set<Setting> settings = new HashSet();
            parentQueue.add(root);
            while (!parentQueue.isEmpty()) {
                Setting s = parentQueue.poll();
                System.out.println("Checking " + s.getHeritage());
                Iterator<Setting> children = s.children.iterator();
                while (children.hasNext()) {
                    Setting child = children.next();
                    parentQueue.add(child);
                    System.out.println("Adding " + child.getHeritage());
                }

                if(s.value!=null){
                    writer.write(s.getSettingLine()+"\n");
                }
            }
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        SettingManager m = new SettingManager();
        try {
            System.out.println("OPENING FILE");
            m.openFile(Paths.get("settings.conf"));
            System.out.println("\nREADING");
            m.readSettings();
            System.out.println("RESULT");
            System.out.println(Arrays.toString(m.root.children.toArray()));
            System.out.println("\n WRITING");
            m.writeSettings();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
