package mobagame.core.game;

import java.awt.geom.Point2D;
import java.util.*;

import mobagame.core.game.maps.MainMap;

public abstract class Game {
	public ArrayList<InGamePlayer> players;
	public ArrayList<Projectile> projectiles;

	private InGamePlayer playerPlayer;
	public MainMap map;
	final static int MAX_PLAYERS = 10;
	protected int gameID;

	public Game() {
		map = new MainMap();
		map.setServerMode();
		map.makeMap();
		for(Tower t : map.towers){
			t.map = map;
		}
		players = new ArrayList<>();
		gameID = new Random().nextInt();
		projectiles = new ArrayList<>();
	}

	public Game(MainMap m) {
		map = m;
		map.setServerMode();
		map.makeMap();
		players = new ArrayList<>();
		gameID = new Random().nextInt();
		projectiles = new ArrayList<>();
	}
	public InGamePlayer getClosestPlayer(Point2D.Double pos, int i, Team oppositeTeam) {
		if(players.size()<1){
			return null;
		}
		double minDist = Double.MAX_VALUE;
		InGamePlayer player = null;
		for (InGamePlayer p : players) {
			if (p.pos.distance(pos) <= i && p.team == oppositeTeam) {
				double d = p.pos.distance(pos);
				if (minDist > d) {
					player = p;
					minDist = d;
				}
			}
		}
		return player;
	}

	public boolean isFull() {
		return players.size() >= MAX_PLAYERS;
	}

	public int getGameID() {
		return gameID;
	}

	public abstract void update();

	public InGamePlayer getPlayerPlayer() {
		return playerPlayer;
	}

	public void setPlayerPlayer(InGamePlayer playerPlayer) {
		this.playerPlayer = playerPlayer;
	}


	public InGamePlayer getPlayer(int playerID) {
		Iterator<InGamePlayer> iter = players.iterator();
		while (iter.hasNext()) {
			InGamePlayer p = iter.next();
			if (p.getPlayerID() == playerID) {
				return p;
			}
		}
		return null;
	}
}
