package mobagame.core.settings;

import java.awt.Component;
import java.util.HashMap;
import java.util.Map;

public class Setting extends EmptySetting {
	String value;
	Component widget;

	// TODO: store as map
	// Set<Setting> children;
	public Setting(String name, String value) {
		this.value = value;
		this.name = name;
	}

	public void setValue(String v) {
		value = v;
	}

	public String getValue() {
		return value;
	}

	public Component getWidget() {
		return widget;
	}

	@Override
	public String toString() {
		return "Setting{" + "name='" + name + '\'' + ", value=" + value + ", parent=" + parent.name + ", children="
				+ children + '}';
	}

	public static void main(String[] args) {
		// System.out.println(new IntSetting("Age", 4).toString());
	}

	public String getSettingLine() {
		return getNameChain() + "=" + value;
	}
}