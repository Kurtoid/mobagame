package mobagame.core.settings;
import java.awt.Component;

public class StringSetting extends Setting<String> {

	public StringSetting(String name, String value) {
		this.name = name;
		this.value = value;
	}



	@Override
	public Component getWidget() {
		return null;
	}

}