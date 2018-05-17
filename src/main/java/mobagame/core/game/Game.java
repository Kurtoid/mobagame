package mobagame.core.game;

import java.util.ArrayList;
import java.util.Random;

import mobagame.core.game.maps.MainMap;

public abstract class Game {
	public ArrayList<InGamePlayer> players;
	private InGamePlayer playerPlayer;
	public MainMap map;
	final static int MAX_PLAYERS = 10;
	private int gameID;

	public Game() {
		map = new MainMap();
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

}
