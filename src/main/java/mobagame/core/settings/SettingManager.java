package mobagame.core.settings;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Iterator;

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

    public void readSettings() throws IOException {
        Charset charset = StandardCharsets.UTF_8;
        try (BufferedReader reader = Files.newBufferedReader(file, charset)) {
            String line = null;
            int l = 0;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
                String[] parts = line.split("=");
                System.out.println(Arrays.toString(parts));

                if (parts.length > 2) {
                    System.out.println("setting error on line " + l);
                    continue;
                }

                String[] keyNames = parts[0].split("\\.");
                System.out.println(Arrays.toString(keyNames));
                String settingName = keyNames[keyNames.length - 1];

                Setting s;
                try {
                    int val = Integer.parseInt(parts[0]);
                    s = new IntSetting(settingName, val);
                } catch (NumberFormatException e) {
                    s = new StringSetting(settingName, parts[1]);
                }

                int settingKeys = keyNames.length - 1;
                Setting tmp = root;
                if (settingKeys > 0) {
                    Setting tmpParent = root;
                    Setting tmpHolder = null;
                    for (int i = 0; i < settingKeys; i++) {
                        Iterator iter = tmpParent.children.iterator();
                        System.out.println("looking for " + keyNames[i]);
                        while (iter.hasNext()) {
                            tmpHolder = (Setting) iter.next();
                            if (tmpHolder.name.equals(keyNames[i])) {
                                System.out.println("found " + tmpHolder.getHeritage());
                                break;
                            }
                        }
                        if (tmpHolder == null) {
                            tmpHolder = new EmptySetting();
                            tmpHolder.name = keyNames[i];
                            tmpHolder.parent = tmpParent;
                            tmpParent.children.add(tmp);
                            System.out.println("created node: " + tmp.getHeritage());
                        }
                        tmpParent = tmpHolder;
                        tmp = tmpHolder;
                        tmpHolder = null;
                    }

                }
                tmp.children.add(s);
                s.parent = tmp;
                System.out.println(s.getHeritage());
                l++;
            }
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        }
    }

    public static void main(String[] args) {
        SettingManager m = new SettingManager();
        try {
            m.openFile(Paths.get("settings.conf"));
            m.readSettings();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
