package mobagame.core.game;

import mobagame.core.game.maps.MainMap;
import mobagame.server.game.ServerGame;

import java.awt.geom.Point2D;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Random;

public class Lobby {
	public ArrayList<InGamePlayer> players;
	public boolean waitingForPlayers = true;
	long timeStarted;
	long timeToWait = 90 * 1000;
	protected int lobbyID;

	public Lobby() {
		this.players = new ArrayList<>();
		lobbyID = new Random().nextInt();
	}

	public boolean isFull() {
		return players.size() >= Game.MAX_PLAYERS;
	}

	public int getLobbyID() {
		return lobbyID;
	}

	public void notifyPlayerJoinedLobby(InGamePlayer p) {

	}

	public void tellClientAboutExistingPlayers(InGamePlayer p, SocketChannel socket) {
	}

	public ServerGame startGame() {
		MainMap m = new MainMap();
		m.setServerMode();
		m.makeMap();

		ServerGame g = new ServerGame(m);
		for (InGamePlayer p : players) {
			g.players.add(p);
//			p.team = GameTeams.lowTeam;
			p.pos = new Point2D.Double(p.team.spawnPoint.getX(), p.team.spawnPoint.getY());
			p.mover = new ObjectMover(g.map, p);
			p.game = g;

		}

		return g;
	}

	public Team assignTeam() {
		System.out.println("ASSIGNING TEAM");
		int highTeamPlayers = 0;
		int lowTeamPlayers = 0;
		for (InGamePlayer p : players) {
			if (p.team == null) {
				System.out.println("player skipped, there must be a problem");
			} else if (GameTeams.gameTeamsLookup.indexOf(p.team) == 0) {
				lowTeamPlayers++;
			} else {
				highTeamPlayers++;
			}
		}
		if (highTeamPlayers > lowTeamPlayers) {
			System.out.println("using low team");

			return GameTeams.lowTeam;
		} else {
			System.out.println("using high team");

			return GameTeams.highTeam;
		}
	}
}
