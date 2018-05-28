package mobagame.core.game;

import mobagame.core.game.Team;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class GameTeams {
	public static Team highTeam = new Team("Red", Color.RED);
	public static Team lowTeam = new Team("Blue", Color.BLUE);

	static public Team[] gameTeams = {highTeam, lowTeam};
	static public ArrayList<Team> gameTeamsLookup = new ArrayList<Team>(Arrays.asList(gameTeams));
}
