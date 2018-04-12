package Core.Settings;

import java.awt.Component;

public class IntSetting extends Setting<Integer> {

	public IntSetting(String name, int value) {
		this.name = name;
		this.value = value;
	}

	@Override
	public Component getWidget() {
		// TODO Auto-generated method stub
		return null;
	}


}
