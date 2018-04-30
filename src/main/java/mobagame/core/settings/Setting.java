package mobagame.core.settings;

import java.awt.Component;
import java.util.HashMap;
import java.util.Map;

public abstract class Setting<E> extends EmptySetting {
	E value;

	// TODO: store as map
	// Set<Setting> children;

	public void setValue(E v) {
		value = v;
	}

	public E getValue() {
		return value;
	}

	public abstract Component getWidget();

	@Override
	public String toString() {
		return "Setting{" + "name='" + name + '\'' + ", value=" + value + ", parent=" + parent.name + ", children="
				+ children + '}';
	}

	public static void main(String[] args) {
		System.out.println(new IntSetting("Age", 4).toString());
	}

	public String getSettingLine() {
		return getNameChain() + "=" + value;
	}
}