package mobagame.core.game;

import java.util.*;

import mobagame.core.game.maps.MainMap;

public abstract class Game {
	public ArrayList<InGamePlayer> players;
	Map<Integer, InGamePlayer> playerMap;
	private InGamePlayer playerPlayer;
	public MainMap map;
	final static int MAX_PLAYERS = 10;
	protected int gameID;

	public Game() {
		map = new MainMap();
		playerMap = new HashMap<>();
		map.setServerMode();
		map.makeMap();
		players = new ArrayList<>();
		gameID = new Random().nextInt();
	}

	public Game(MainMap m) {
		map = m;
		playerMap = new HashMap<>();
		map.setServerMode();
		map.makeMap();
		players = new ArrayList<>();
		gameID = new Random().nextInt();
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
