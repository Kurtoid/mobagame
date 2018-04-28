package mobagame.core.settings;

import java.awt.Component;
import java.util.HashSet;
import java.util.Set;

public abstract class Setting<E> {
	String name;
	E value;
	Setting<?> parent;
	Set<Setting<? extends Setting<?>>> children;

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



	@Override
	public String toString() {
		return "Setting [getName()=" + getName() + ", getValue()=" + getValue() + ", getWidget()=" + getWidget() + "]";
	}

	public static void main(String[] args) {
		System.out.println(new IntSetting("Age", 4).toString());
	}
}