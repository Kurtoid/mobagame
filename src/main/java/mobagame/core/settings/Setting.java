package mobagame.core.settings;

import java.awt.Component;
import java.util.HashMap;
import java.util.Map;

/**
 * represents a setting stored on the client or server settings are
 * machine-specific, but may, later on, be sent over network
 *
 * @author Kurt Wilson
 */
public class Setting extends EmptySetting {
	/**
	 * the value the setting represents
	 */
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

	/**
	 * if the setting is something shown to the user, render it out might remove
	 * this...
	 *
	 * @return componet to be rendered in swing
	 */
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

	/**
	 * this is whats saved to the settings file
	 *
	 * @return line to settings file
	 */
	public String getSettingLine() {
		return getNameChain() + "=" + value;
	}
}