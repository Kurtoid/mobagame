package mobagame.core.game;

import mobagame.core.game.maps.MainMap;
import mobagame.core.networking.packets.RequestEnterGameResponsePacket;
import mobagame.launcher.networking.ServerConnection;
import mobagame.server.game.ServerGame;

import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Random;

public class Lobby {
	public ArrayList<InGamePlayer> players;
	public boolean waitingForPlayers=true;
	long timeStarted;
	long timeToWait = 90*1000;
	protected int lobbyID;

	public Lobby() {
		this.players = new ArrayList<>();
		lobbyID = new Random().nextInt();
	}

	public boolean isFull() {
		return /*players.size() >= Game.MAX_PLAYERS;*/ true;
	}

	public int getLobbyID() {
		return  lobbyID;
	}

	public void notifyPlayerJoinedLobby(InGamePlayer p) {
		
	}

	public void tellClientAboutExistingPlayers(InGamePlayer p, SocketChannel socket) {
	}

	public ServerGame startGame() {
		MainMap m = new MainMap();
		m.setServerMode();
		m.makeMap();

		ServerGame g= new ServerGame(m);
		for(InGamePlayer p : players){
			g.players.add(p);
			p.team = GameTeams.lowTeam;
			p.setX(90);
			p.setY(870);
			p.mover = new PlayerMover(g.map, p);

		}

		return g;
	}
}
