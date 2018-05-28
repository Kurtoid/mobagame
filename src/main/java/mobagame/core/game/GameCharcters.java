package mobagame.core.game;

import java.util.Arrays;
import java.util.List;

public class GameCharcters {

	public final static Character reaper = new Character("reaper", "resources/Character/Reaper.png", 1, 50, 20, 500, 20, 375,
			5, 25, 5, 25, 2, 10, 2, 10);//Still need to be balanced
	public final static Character jack = new Character("Jack", "resources/Character/Jack.png", 150, 50, 20, 575, 25, 370, 7, 64, 0, 0, 5, 5, 7, 7);
	public static final Character[] allGameCharacter = {reaper, jack};
	public static final List<Character> allGameCharacterLookup = Arrays.asList(allGameCharacter);
}