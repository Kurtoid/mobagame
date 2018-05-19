package mobagame.server.game;

import mobagame.core.game.Game;
import mobagame.core.game.InGamePlayer;
import mobagame.core.game.maps.MainMap;
import mobagame.core.networking.packets.PlayerPositionPacket;
import mobagame.server.ConnectionListener;
import mobagame.server.MasterGameRunner;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerGame extends Game {
	Logger logger = Logger.getLogger(ServerGame.class.getName());
	public static MasterGameRunner runner;

	public ServerGame(MainMap m) {
		this.map = m;
	}

	@Override
	public void update() {
//		System.out.println("updating game");
		for (InGamePlayer player : players) {
			player.mover.update();
			if (player.mover.atTarget()) {
				logger.log(Level.INFO, "player reached target");
			}
		}
	}

	public void sendToClients(ConnectionListener conn) {
		for (InGamePlayer p : players) {
			PlayerPositionPacket posPak = new PlayerPositionPacket();
			posPak.x = p.getX();
			posPak.y = p.getY();
			posPak.playerID = p.getPlayerID();
			for (InGamePlayer player : players) {
				logger.log(Level.INFO, "sending state" + posPak.toString());
				conn.send(runner.conn.playerToConnection.get(player), posPak.getBytes().array());
			}
		}
	}
}
