package mobagame.server;

import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

import mobagame.core.game.*;
import mobagame.core.networking.packets.*;
import mobagame.server.database.PlayerAccount;
import mobagame.server.database.PlayerAccountDBO;
import mobagame.server.game.ServerGame;

public class ResponseWorker implements Runnable {
	Logger logger = Logger.getLogger(this.getClass().getName());

	MasterGameRunner runner;
	private List queue = new LinkedList();
	public Queue<Packet> gameEvents = new LinkedBlockingQueue<Packet>();

	public void processData(ConnectionListener server, SocketChannel socket, byte[] data, int count, int connectionID) {
		byte[] dataCopy = new byte[count];
		System.arraycopy(data, 0, dataCopy, 0, count);
		synchronized (queue) {
			queue.add(new ServerDataEvent(server, socket, dataCopy, connectionID));
			queue.notify();
		}
	}

	public void run() {
		ServerDataEvent dataEvent;

		while (true) {
			// Wait for data to become available
			synchronized (queue) {
				while (queue.isEmpty()) {
					try {
						queue.wait();
					} catch (InterruptedException e) {
					}
				}
				dataEvent = (ServerDataEvent) queue.remove(0);
			}
			ByteBuffer chunkBuf = ByteBuffer.wrap(dataEvent.data);
			byte packetID = Packet.getPacketID(chunkBuf);
			System.out.println(packetID);
			switch (packetID) {
			case Packet.PK_ID_AUTH_LOGIN:
				logger.log(Level.INFO, "login");
				// System.out.println(new LoginPacket(chunkBuf));
				LoginPacket p = new LoginPacket(chunkBuf);
				handleLoginPacket(p, dataEvent);
				break;
			case Packet.PK_ID_AUTH_SIGNUP:
				// System.out.println(new SignupPacket(chunkBuf));
				logger.log(Level.INFO, "Signup");
				SignupPacket packet = new SignupPacket(chunkBuf);
				handleSignupPacket(packet, dataEvent);
				break;
			case Packet.PK_ID_INIT:
				logger.log(Level.INFO, "Connection init");
				handleInitPacket(new InitPacket(chunkBuf), dataEvent);
				break;
			case Packet.PK_ID_CONN_DISCONNECT:
				logger.log(Level.INFO, "disconnect");
				handleDisconnectPacket(new DisconnectPacket(chunkBuf), dataEvent);
				break;
			case Packet.PK_ID_RANDOM_BS_PACKET:
				logger.log(Level.INFO, "BULLSHIT MODE");
				handleBullshitPacket(new SendRandomDataPacket(chunkBuf), dataEvent);
				break;
			case Packet.PK_ID_PLAYER_REQUEST_ENTER_LOBBY:
				logger.log(Level.INFO, "request enter lobby");
				handleRequestEnterLobbyPacket(new RequestEnterLobbyPacket(chunkBuf), dataEvent);
				break;
			case Packet.PK_ID_PLAYER_REQUEST_ENTER_GAME:
				logger.log(Level.INFO, "request enter game");
				handleRequestEnterGamePacket(new RequestEnterGamePacket(chunkBuf), dataEvent);
				break;
			case Packet.PK_ID_PLAYER_REQUEST_MOVEMENT:
				logger.log(Level.INFO, "player movement");
				handleRequestMovementPacket(new RequestPlayerMovementPacket(chunkBuf), dataEvent);
				break;
			case Packet.PK_ID_PLAYER_REQUEST_BUY_ITEM:
				logger.log(Level.INFO, "Player buy item");
				handleBuyItemRequestPacket(new RequestPlayerBuyItemPacket(chunkBuf), dataEvent);
				break;
			case Packet.PK_ID_PLAYER_REQUEST_SELL_ITEM:
				logger.log(Level.INFO, "player sell item");
				handleSellItemRequestPacket(new RequestPlayerSellItemPacket(chunkBuf), dataEvent);
				break;
			case Packet.PK_ID_PLAYER_USE_ITEM_REQUEST:
				logger.log(Level.INFO, "player use item");
				handleUseItemRequestPacket(new PlayerUseItemRequestPacket(chunkBuf), dataEvent);
				break;
			case Packet.PK_ID_DEBUG_CLIENT_FORCE_START_GAME:
				logger.log(Level.WARNING, "forcing start of game");
				handleDEBUGClientForceStartGame(new DEBUG_ClientForceStartGame(chunkBuf), dataEvent);
				break;
				case Packet.PK_ID_DEBUG_THROW_ME_IN_A_GAME:
					logger.log(Level.INFO, "throwing in a game");
					handleThrowMeInAGamePacket(new DEBUG_JustJoinToAGame(chunkBuf), dataEvent);
					break;
			default:
				logger.log(Level.WARNING, "bad pkt");
				break;
			}

			// Return to sender
			// dataEvent.server.send(dataEvent.socket, dataEvent.data);
		}
	}

	private void handleThrowMeInAGamePacket(DEBUG_JustJoinToAGame debug_justJoinToAGame, ServerDataEvent dataEvent) {
		ServerGame g = runner.findGame(debug_justJoinToAGame.playerID);
		logger.log(Level.INFO, "finding a game for " + debug_justJoinToAGame.playerID);
		InGamePlayer p = new InGamePlayer(debug_justJoinToAGame.playerID, GameCharcters.reaper);
		p.team = GameTeams.lowTeam;
		runner.conn.playerToConnection.put(p, dataEvent.socket);
		runner.conn.connectionToPlayerID.put(dataEvent.socket, p.getPlayerID());
		runner.connectionToPlayer.put(dataEvent.connectionID, p);

		p.setX(90);
		p.setY(870);
		p.mover = new ObjectMover(g.map, p);
		p.game = g;

		runner.addToGame(g, p, dataEvent.connectionID);

		logger.log(Level.INFO, "resp with gameid " + g.getGameID() + " and player id " + p.getPlayerID());
		RequestEnterGameResponsePacket resp = new RequestEnterGameResponsePacket(g,p);
		resp.playerID = p.getPlayerID();
//		System.out.println(Arrays.toString(resp.getBytes().array()));
		dataEvent.server.send(dataEvent.socket, resp.getBytes().array());
		g.notifyPlayerJoinedGame(p);
		g.tellClientAboutExistingPlayers(p, dataEvent.socket);

	}

	private void handleDEBUGClientForceStartGame(DEBUG_ClientForceStartGame debug_clientForceStartGame, ServerDataEvent dataEvent) {
		System.out.println("force starting lobby " + debug_clientForceStartGame.lobbyID);
		Lobby l = runner.getLobby(debug_clientForceStartGame.lobbyID);
		ServerGame g = l.startGame();

		for(InGamePlayer p : l.players){
			runner.playerToLobby.remove(p);
			runner.playerToGame.put(p,g);
			p.game = g;
		}
		runner.games.add(g);
		RequestEnterGameResponsePacket pkt = new RequestEnterGameResponsePacket();
		pkt.gameID = g.gameID;
		for(InGamePlayer p : g.players){
			pkt.playerID = p.getPlayerID();

			g.tellClientAboutExistingPlayers(p, dataEvent.server.playerToConnection.get(p));

			pkt.playerID = p.getPlayerID();
			dataEvent.server.send(dataEvent.server.playerToConnection.get(p), pkt.getBytes().array());
			PlayerPositionPacket pos = new PlayerPositionPacket();
			pos.x = p.getX();
			pos.y = p.getY();
			pos.teamID = GameTeams.gameTeamsLookup.indexOf(p.team);
			pos.playerID = p.getPlayerID();
			dataEvent.server.send(dataEvent.server.playerToConnection.get(p), pos.getBytes().array());
			NotifyPlayerJoinedGamePacket npjgp = new NotifyPlayerJoinedGamePacket();
			npjgp.playerID = p.getPlayerID();
			npjgp.teamID = GameTeams.gameTeamsLookup.indexOf(p.team);
			for(InGamePlayer p2 : g.players){
				dataEvent.server.send(dataEvent.server.playerToConnection.get(p2), npjgp.getBytes().array());
			}
		}


	}

	private void handleRequestEnterGamePacket(RequestEnterGamePacket requestEnterGamePacket, ServerDataEvent dataEvent) {
	}

	private void handleUseItemRequestPacket(PlayerUseItemRequestPacket playerUseItemRequestPacket,
			ServerDataEvent dataEvent) {
		// TODO Auto-generated method stub
		InGamePlayer player = runner.connectionToPlayer.get(dataEvent.connectionID);
		//
		PlayerUseItemResponsePacket pkt = new PlayerUseItemResponsePacket();
		for (int i = 0; i < player.inventory.length && pkt.used == 0; i++) {
			if(player.inventory[i].equals(GameItems.allGameItemsLookup.get(playerUseItemRequestPacket.itemID))) {
				pkt.used = player.inventory[i].use(player);
				if (pkt.used == 1) {
					player.inventory[i] = GameItems.empty;
				}
			}
		}
		pkt.itemID = playerUseItemRequestPacket.itemID;
		dataEvent.server.send(dataEvent.socket, pkt.getBytes().array());
	}


	private void handleSellItemRequestPacket(RequestPlayerSellItemPacket requestPlayerSellItemPacket, ServerDataEvent dataEvent) {
		int status = runner.playerToGame.get(runner.connectionToPlayer.get(dataEvent.connectionID)).sellItem(runner.connectionToPlayer.get(dataEvent.connectionID), requestPlayerSellItemPacket.itemID);
		RequestPlayerSellItemResponsePacket resp = new RequestPlayerSellItemResponsePacket();
		resp.status = status;
		resp.itemID = requestPlayerSellItemPacket.itemID;
		dataEvent.server.send(dataEvent.socket, resp.getBytes().array());

	}

	private void handleBuyItemRequestPacket(RequestPlayerBuyItemPacket requestPlayerBuyItemPacket, ServerDataEvent dataEvent) {
		int status = runner.playerToGame.get(runner.connectionToPlayer.get(dataEvent.connectionID)).buyItem(runner.connectionToPlayer.get(dataEvent.connectionID), requestPlayerBuyItemPacket.itemID);
		RequestPlayerBuyItemResponsePacket resp = new RequestPlayerBuyItemResponsePacket();
		resp.status = status;
		resp.itemID = requestPlayerBuyItemPacket.itemID;
		dataEvent.server.send(dataEvent.socket, resp.getBytes().array());

	}

	private void handleRequestMovementPacket(RequestPlayerMovementPacket requestPlayerMovementPacket,
			ServerDataEvent dataEvent) {
		InGamePlayer ply = runner.getPlayer(dataEvent.connectionID);
		logger.log(Level.INFO, "movement for conn id " + dataEvent.connectionID + " and player " + ply.getPlayerID());
		ply.mover.setTarget(requestPlayerMovementPacket.x,
				requestPlayerMovementPacket.y);
	}

	private void handleRequestEnterLobbyPacket(RequestEnterLobbyPacket requestEnterLobbyPacket,
			ServerDataEvent dataEvent) {
		int playerID = dataEvent.server.connectionToPlayerID(dataEvent.socket);
		Lobby lobby = runner.findLobby(playerID);
		InGamePlayer p = new InGamePlayer(playerID, GameCharcters.reaper);
		p.team = lobby.assignTeam();
		runner.conn.playerToConnection.put(p, dataEvent.socket);
		runner.conn.connectionToPlayerID.put(dataEvent.socket, p.getPlayerID());
		runner.connectionToPlayer.put(dataEvent.connectionID, p);
/*
		p.team = GameTeams.lowTeam;
		runner.conn.playerToConnection.put(p, dataEvent.socket);
		p.setX(90);
		p.setY(870);
		p.mover = new ObjectMover(lobby.map, p);
*/


		logger.log(Level.INFO, "resp with gameid " + lobby.getLobbyID() + " and player id " +playerID);
		RequestEnterLobbyResponsePacket resp = new RequestEnterLobbyResponsePacket(lobby,p);
		resp.lobbyID = lobby.getLobbyID();
		resp.playerID = p.getPlayerID();
//		System.out.println(Arrays.toString(resp.getBytes().array()));
		dataEvent.server.send(dataEvent.socket, resp.getBytes().array());

		runner.addToLobby(lobby, p, dataEvent.connectionID);

//		lobby.notifyPlayerJoinedLobby(p);
//		lobby.tellClientAboutExistingPlayers(p, dataEvent.socket);

	}

	private void handleBullshitPacket(SendRandomDataPacket packet, ServerDataEvent dataEvent) {
		dataEvent.server.send(dataEvent.socket, new SendRandomDataPacket().getBytes().array());
		dataEvent.server.send(dataEvent.socket, new SendRandomDataPacket().getBytes().array());
		dataEvent.server.send(dataEvent.socket, new SendRandomDataPacket().getBytes().array());
	}

	private void handleDisconnectPacket(DisconnectPacket disconnectPacket, ServerDataEvent dataEvent) {
	}

	private void handleInitPacket(InitPacket initPacket, ServerDataEvent dataEvent) {
	}

	private void handleSignupPacket(SignupPacket packet, ServerDataEvent dataEvent) {
		PlayerAccountDBO dbo = new PlayerAccountDBO();
		SignupResponsePacket response = new SignupResponsePacket();
		// SignupResponsePacket resp = new
		// SignupResponsePacket(SignupResponsePacket.FA);
		try {
			dbo.createAccount(packet.getUsername(), packet.getPassword(), packet.getEmailAddress(),
					packet.getSecurityQuestionID(), packet.getSecurityQuestionAnswer());
			response.status = SignupResponsePacket.SUCCESSFUL;
		} catch (SQLException e) {
			e.printStackTrace();
			PlayerAccount tmp = null;
			try {
				tmp = dbo.getAccountByEmail(packet.getEmailAddress());
			} catch (SQLException e1) {
				logger.log(Level.INFO, "not email");
				// skip
			}
			if (tmp != null) {
				logger.log(Level.INFO, "bad email");
				response.status = SignupResponsePacket.FAILED_EMAIL;
			}
			tmp = null;
			try {
				tmp = dbo.getAccountByUsername(packet.getUsername());
			} catch (SQLException e1) {
				logger.log(Level.INFO, "not username");
				// skip
			}
			if (tmp != null) {
				logger.log(Level.INFO, "bad username");
				response.status = SignupResponsePacket.FAILED_USERNAME;
			}
		}
		dataEvent.server.send(dataEvent.socket, response.getBytes().array());
	}

	void handleLoginPacket(LoginPacket p, ServerDataEvent dataEvent) {
		// if (serverEnabled) {
		PlayerAccountDBO dbo = new PlayerAccountDBO();
		PlayerAccount player = null;
		try {
			player = dbo.loginAccount(p.getUsername(), p.getPassword());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		LoginStatusPacket loginPak = new LoginStatusPacket();
		loginPak.success = player != null;
		dataEvent.server.send(dataEvent.socket, loginPak.getBytes().array());
		if (player != null) {
			dataEvent.server.connectionToPlayerID.put(dataEvent.socket, player.id);
			logger.log(Level.INFO, "logged in player " + player.id);
			dataEvent.server.send(dataEvent.socket, new PublicPlayerDataPacket(player).getBytes().array());
		}
		// }

	}
}