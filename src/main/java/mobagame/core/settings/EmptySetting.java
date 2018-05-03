package mobagame.core.settings;

import java.util.HashMap;
import java.util.Map;

/**
 * setting class, doesnt hold a value, but describes a setting like
 * something.something.setting
 *
 * @author Kurt Wilson
 */
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

	/**
	 * for debug use, returns parent.parent.name
	 *
	 * @return the parents of this node, and this node's name
	 */
	String getHeritage() {
		if (parent != null) {
			return parent.getHeritage() + " " + name;
		}
		return name;
	}

	/**
	 * used for saving to file, gets the prefix for this node
	 *
	 * @return parents of this node, and it's name
	 */
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
