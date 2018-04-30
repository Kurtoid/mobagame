package mobagame.core.settings;

import java.util.HashMap;
import java.util.Map;

public class EmptySetting {
    String name;
    EmptySetting parent;

    Map<String, EmptySetting> children;

    public EmptySetting() {
        children = new HashMap<String, EmptySetting>();

    }

    public EmptySetting getParent() {
        return parent;
    }

    public void setParent(EmptySetting parent) {
        this.parent = parent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    String getHeritage() {
        if (parent != null) {
            return parent.getHeritage() + " " + name;
        }
        return name;
    }

    String getNameChain() {
        if (name.equals("root")) {
            return null;
        }
        if (parent != null) {
            String parents = parent.getNameChain();
            if (parents == null) {
                return name;
            }
            return parent.getNameChain() + "." + name;
        }
        return name;
    }

}
