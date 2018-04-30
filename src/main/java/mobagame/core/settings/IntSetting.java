package mobagame.core.settings;

import java.awt.Component;

public class IntSetting extends Setting<Integer> {

    public IntSetting(String name, int value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public Component getWidget() {
        return null;
    }

}