package mobagame.core.game;

import java.util.Arrays;
import java.util.List;

public class GameCharcters {

	public final static Character reaper = new Character("reaper", "resources/Character/reaper.pmg", 1, 50, 20, 100, 20, 100,
			5, 25, 5, 25, 2, 10, 2, 10);
	public static final Character[] allGameCharacter = {reaper};
	public static final List<Character> allGameCharacterLookup = Arrays.asList(allGameCharacter);
}