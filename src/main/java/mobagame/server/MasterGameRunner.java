package mobagame.server;

import java.nio.channels.SocketChannel;
import java.sql.SQLException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import mobagame.core.game.*;
import mobagame.core.game.maps.MainMap;
import mobagame.core.networking.packets.CharacterSelectShowPlayer;
import mobagame.core.networking.packets.NotifyPlayerEnterCharacterSelect;
import mobagame.core.networking.packets.RequestEnterGameResponsePacket;
import mobagame.server.database.PlayerAccount;
import mobagame.server.database.PlayerAccountDBO;
import mobagame.server.game.ServerGame;

public class MasterGameRunner extends Thread {
	public Map<InGamePlayer, Lobby> playerToLobby = new HashMap<>();
	Logger logger = Logger.getLogger(this.getClass().getName());

	Set<ServerGame> games;
	Set<Lobby> lobbies;
	Map<Integer, InGamePlayer> connectionToPlayer = new HashMap<>();

	Map<InGamePlayer, ServerGame> playerToGame = new HashMap<>();
	boolean running = false;
	double fps;
	int frameCount = 0;
	public ConnectionListener conn;
	/**
	 * almost never used, but there if we need it
	 */
	boolean paused = false;

	public MasterGameRunner() {
		games = new HashSet<>();
		lobbies = new HashSet<>();
		running = true;
		this.setDaemon(true);
		this.start();
		ServerGame.runner = this;
	}

	/**
	 * http://www.java-gaming.org/index.php?topic=24220.0 <br>
	 * modified by me for server tick counting
	 */
	@Override
	public void run() {
		super.run();
		// This value would probably be stored elsewhere.
		final double GAME_HERTZ = 20;
		// Calculate how many ns each frame should take for our target game hertz.
		final double TIME_BETWEEN_UPDATES = 1000000000 / GAME_HERTZ;
		// We will need the last update time.
		double lastUpdateTime = System.nanoTime();
		// Store the last time we rendered.

		// Simple way of finding FPS.
		int lastSecondTime = (int) (lastUpdateTime / 1000000000);
		int updateCount = 0;

		while (running) {
			double now = System.nanoTime();

			if (!paused) {
				// Do as many game updates as we need to, potentially playing catchup.
				while (now - lastUpdateTime > TIME_BETWEEN_UPDATES) {
					updateGame();
					updateLobbies();
					lastUpdateTime += TIME_BETWEEN_UPDATES;
					updateCount++;
					lastUpdateTime = now;

				}

				// If for some reason an update takes forever, we don't want to do an insane
				// number of catchups.
				// If you were doing some sort of game that needed to keep EXACT time, you would
				// get rid of this.
				if (now - lastUpdateTime > TIME_BETWEEN_UPDATES) {
					lastUpdateTime = now - TIME_BETWEEN_UPDATES;
				}

				// Update the frames we got.
				int thisSecond = (int) (lastUpdateTime / 1000000000);
				if (thisSecond > lastSecondTime) {
					// System.out.println("NEW SECOND " + thisSecond + " " + updateCount);
					sendStatusReports();
					incrementGold();
					fps = updateCount;
					updateCount = 0;
					lastSecondTime = thisSecond;
				}

				// Yield until it has been at least the target time between renders. This saves
				// the CPU from hogging.
				while (now - lastUpdateTime < TIME_BETWEEN_UPDATES) {
					Thread.yield();

					// This stops the app from consuming all your CPU. It makes this slightly less
					// accurate, but is worth it.
					// You can remove this line and it will still work (better), your CPU just
					// climbs on certain OSes.
					// FYI on some OS's this can cause pretty bad stuttering. Scroll down and have a
					// look at different peoples' solutions to this.
					try {
						Thread.sleep(1);
					} catch (Exception e) {
					}

					now = System.nanoTime();
				}
			}
		}

	}

	private void sendStatusReports() {
		for(ServerGame g : games) {
			g.sendStatusReport();
		}

	}

	private void incrementGold() {
		for(ServerGame g : games) {
			g.incrementGold();
		}
	}
	public ServerGame findGame(int playerID) {
		for (ServerGame g : games) {
			if (!g.isFull()) {
				logger.log(Level.INFO, "using existing game");
				return g;
			}
		}
		MainMap m = new MainMap();
		m.setServerMode();
		m.makeMap();
		ServerGame g = new ServerGame(m);
		games.add(g);
		logger.log(Level.INFO, "new game created");
		return g;
	}


	private void updateGame() {
		for (ServerGame g : games) {
			g.update();
			System.out.println("projectiles: " + g.projectiles.size());
			g.sendToClients(conn);
		}
	}

	private void updateLobbies(){
		for(Lobby l : lobbies){
			if(l.isFull()&&l.waitingForPlayers){
				logger.log(Level.INFO,"lobby full, starting character select");
				l.waitingForPlayers = false;
				/*for(InGamePlayer p : l.players){
					playerToLobby.remove(p);
				}*/
//				ServerGame g = l.startGame();
//				games.add(g);
				PlayerAccountDBO playerDB = new PlayerAccountDBO();
				for(InGamePlayer p : l.players){
					CharacterSelectShowPlayer charNotif = new CharacterSelectShowPlayer();
					charNotif.playerID = p.getPlayerID();
					try {
						charNotif.playerUsername = playerDB.getUsernameByID(p.getPlayerID());
					}catch(SQLException e){
						logger.log(Level.SEVERE, "couldn't find player id " + p.getPlayerID());
					}
					for(InGamePlayer p2 : l.players){
						conn.send(conn.playerToConnection.get(p2), charNotif.getBytes().array());
					}

				}
				NotifyPlayerEnterCharacterSelect pkt = new NotifyPlayerEnterCharacterSelect();
				for(InGamePlayer p : l.players){
					pkt.teamID = GameTeams.gameTeamsLookup.indexOf(p.team);
					conn.send(conn.playerToConnection.get(p), pkt.getBytes().array());
				}

//				lobbies.remove(l);
			}
		}
	}
	public Lobby findLobby(int playerID) {
		for (Lobby l : lobbies) {
			if (!l.isFull()) {
				logger.log(Level.INFO, "using existing lobby");
				return l;
			}
		}
/*
		MainMap m = new MainMap();
		m.setServerMode();
		m.makeMap();
		ServerGame g = new ServerGame(m);
*/
		Lobby l = new Lobby();
		lobbies.add(l);
		logger.log(Level.INFO, "new lobby created " + l.getLobbyID());
		return l;
	}

	public InGamePlayer getPlayer(int connectionID) {
		return connectionToPlayer.get(connectionID);
	}

	public void addToGame(ServerGame g, InGamePlayer p, int connectionID) {
		g.players.add(p);
		p.setGoldAmount(5000);
		connectionToPlayer.put(connectionID, p);
		playerToGame.put(p, g);

	}

	public void addToLobby(Lobby lobby, InGamePlayer p, int connectionID) {
		lobby.players.add(p);
		playerToLobby.put(p, lobby);
		int playersInTop = 0;
		int playersInBottom = 0;
		for(InGamePlayer player : lobby.players){
			if(GameTeams.gameTeamsLookup.indexOf(player.team)==0){
				playersInTop++;
			}else{
				playersInBottom++;
			}
		}
		p.team = playersInBottom > playersInTop ? GameTeams.highTeam : GameTeams.lowTeam;
//		p.setGoldAmount(5000);
		connectionToPlayer.put(connectionID, p);
//		playerToGame.put(p, g);


	}

	public Lobby getLobby(int lobbyID) {
		for(Lobby l : lobbies) {
			if (l.getLobbyID() == lobbyID)
				return l;
		}
	return null;
	}
}
