package mobagame.core.game;

import mobagame.core.game.Team;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Arrays;

public class GameTeams {
	public static Team highTeam = new Team("Red", Color.RED, new Point2D.Double(1000-90, 90));
	public static Team lowTeam = new Team("Blue", Color.BLUE, new Point2D.Double(90,1000-90));
//	public static Team nAT = new Team("Yellow", Color.YELLOW, new Point2D.Double(500,500));

	public static Team getOppositeTeam(Team team) {
		if(gameTeamsLookup.indexOf(team)==1){
			return gameTeams[0];
		}else{
			return gameTeams[1];
		}
	}

	static public Team[] gameTeams = {lowTeam,highTeam};
	static public ArrayList<Team> gameTeamsLookup = new ArrayList<Team>(Arrays.asList(gameTeams));
}
