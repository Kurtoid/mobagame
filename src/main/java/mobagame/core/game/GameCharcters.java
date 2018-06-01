package mobagame.core.game;

import mobagame.core.game.characters.*;

import java.util.Arrays;
import java.util.List;

public class GameCharcters {

	public final static Character reaper = new Character("reaper", "resources/Character/Reaper.png", 1, 50, 20, 500, 20, 375,
			5, 25, 5, 25, 2, 10, 2, 10, 2, 2, 1000);//Still need to be balanced
	public final static Character jack = new Jack();
	public static final Character[] allGameCharacter = {reaper, jack};
	public static final List<Character> allGameCharacterLookup = Arrays.asList(allGameCharacter);
}