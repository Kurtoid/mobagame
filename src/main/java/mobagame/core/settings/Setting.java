package mobagame.core.settings;

import java.awt.Component;
import java.util.HashSet;
import java.util.Set;

public abstract class Setting<E> {
    String name;
    E value;
    Setting<?> parent;
    Set<Setting> children;

    public Setting() {
        children = new HashSet<>();
    }

    public Setting<?> getParent() {
        return parent;
    }

    public void setParent(Setting<?> parent) {
        this.parent = parent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setValue(E v) {
        value = v;
    }

    public E getValue() {
        return value;
    }

    public abstract Component getWidget();

    String getHeritage() {
        if (parent != null) {
            return parent.getHeritage() + " " + name;
        }
        return name;
    }

    @Override
    public String toString() {
        return "Setting{" +
                "name='" + name + '\'' +
                ", value=" + value +
                ", parent=" + parent.name +
                ", children=" + children +
                '}';
    }

    public static void main(String[] args) {
        System.out.println(new IntSetting("Age", 4).toString());
    }

    private String getNameChain() {
//        if(name.equals("root")){
//            return null;
//        }
        if (parent != null) {
            return parent.getNameChain() + "." + name;
        }
        return name;
    }

    public String getSettingLine() {
        return getNameChain() + "=" + value;
    }
}