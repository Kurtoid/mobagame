package mobagame.server.game;

import mobagame.core.game.Game;
import mobagame.core.game.GameItems;
import mobagame.core.game.InGamePlayer;
import mobagame.core.game.Item;
import mobagame.core.game.maps.MainMap;
import mobagame.core.networking.packets.NotifyPlayerDisconnectedPacket;
import mobagame.core.networking.packets.NotifyPlayerJoinedGamePacket;
import mobagame.core.networking.packets.PlayerPositionPacket;
import mobagame.core.networking.packets.RequestPlayerBuyItemResponsePacket;
import mobagame.server.ConnectionListener;
import mobagame.server.MasterGameRunner;

import java.net.Socket;
import java.nio.channels.SocketChannel;
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
		// System.out.println("updating game");
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

	public void notifyPlayerJoinedGame(InGamePlayer newPlayer) {
		NotifyPlayerJoinedGamePacket p = new NotifyPlayerJoinedGamePacket();
		p.playerID = newPlayer.getPlayerID();
		for (InGamePlayer player : players) {
			if (!player.equals(newPlayer)) {
				runner.conn.send(runner.conn.playerToConnection.get(player), p.getBytes().array());
			}
		}

	}

	public void tellClientAboutExistingPlayers(InGamePlayer newPlayer, SocketChannel socket) {
		for (InGamePlayer player : players) {
			if (!player.equals(newPlayer)) {
				NotifyPlayerJoinedGamePacket p = new NotifyPlayerJoinedGamePacket();
				p.playerID = player.getPlayerID();
				runner.conn.send(socket, p.getBytes().array());
			}
		}

	}

	private void removePlayer(InGamePlayer p) {
		players.remove(p);
	}

	public void notifyPlayersAboutDisconnect(InGamePlayer p) {
		NotifyPlayerDisconnectedPacket pkt = new NotifyPlayerDisconnectedPacket();
		pkt.playerID = p.getPlayerID();
		pkt.disconnectReason = NotifyPlayerDisconnectedPacket.MANUAL_DISCONNECT;
		for (InGamePlayer player : players) {
			runner.conn.send(runner.conn.playerToConnection.get(player), pkt.getBytes().array());
		}
	}

	public int buyItem(InGamePlayer user, int itemID) {
		Item i = GameItems.allGameItems[itemID];
		for (int y = 0; y < user.inventory.length; y++) {
			for (int x = 0; x < user.inventory[y].length; x++) {
				if (user.inventory[y][x] == GameItems.empty) {
					if (user.getGoldAmount() >= i.getPrice()) {
						user.setGoldAmount(user.getGoldAmount() - i.getPrice());
						user.inventory[y][x] = i;
						if (!i.isConsumable()) {
							for (int z = 0; z < i.getType().length; z++) {
								switch (i.getType()[z]) {
								case Health:
									user.setMaxHealth(user.getMaxHealth() + i.getEffectPoints()[z]);
									break;
								case Mana:
									user.setMaxMana(user.getMaxMana() + i.getEffectPoints()[z]);
									break;
								case PhysicalPower:
									user.setMaxHealth(user.getMaxHealth() + i.getEffectPoints()[z]);
									break;
								case AbilityPower:
									user.setPhyPow(user.getPhyPow() + i.getEffectPoints()[z]);
									break;
								case Speed:
									user.setSpeed(user.getSpeed() + i.getEffectPoints()[z]);
									break;
								case AttackSpecial:
									user.setAbiPow(user.getAbiPow() + i.getEffectPoints()[z]);
									break;
								case Armor:
									user.setArmor(user.getArmor() + i.getEffectPoints()[z]);
									break;
								case MagicResistance:
									user.setMagicResist(user.getMagicResist() + i.getEffectPoints()[z]);
									break;
								default:
									System.out.println("ERROR: Unknown item type");
									break;
								}
							}
						}
						System.out.println("You bought a " + i.getName());
						return RequestPlayerBuyItemResponsePacket.SUCCESSFUL;
					} else {
						System.out.println("Not enought gold to buy " + i.getName() + "\n\t" + "You need "
								+ (i.getPrice() - user.getGoldAmount()) + " more gold");
						return RequestPlayerBuyItemResponsePacket.NOT_ENOUGH_GOLD;
					}
				}
			}
		}
		System.out.println("No space in inventory to buy " + i.getName());
		return RequestPlayerBuyItemResponsePacket.NO_INVENTORY_SPACE;
	}

	public void incrementGold() {
		for (InGamePlayer p : players) {
			p.setGoldAmount(p.getGoldAmount() + 2);
		}
	}
}
