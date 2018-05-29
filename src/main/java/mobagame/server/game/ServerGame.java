package mobagame.server.game;

import mobagame.core.game.*;
import mobagame.core.game.maps.MainMap;
import mobagame.core.networking.packets.*;
import mobagame.server.ConnectionListener;
import mobagame.server.MasterGameRunner;

import java.awt.geom.Point2D;
import java.net.Socket;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import static mobagame.core.game.ItemType.*;

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
		for(Tower t : map.towers) {
			if (t.canFire()) {
				InGamePlayer player = getClosestPlayer(t.pos, map.width/20, GameTeams.getOppositeTeam(t.team));
				if (player != null) {
					Projectile p = (t.fire(player, this));
					p.update();
					System.out.println("projectile fired " + p.projectileID);
					projectiles.add(p);
					notifyPlayersAboutProjectileFired(p);
				}
			}
		}
		{
			Iterator<Projectile> iter = projectiles.iterator();
			while (iter.hasNext()) {
				Projectile p = iter.next();
				p.update();
				if (!p.active) {
					iter.remove();
					System.out.println("deactivated projectile " + p.projectileID);
					notifyPlayersAboutProjectileRemoved(p);
				}

			}
		}
	}

	private void notifyPlayersAboutProjectileRemoved(Projectile p) {
		NotifyProjectileRemovedPacket pkt = new NotifyProjectileRemovedPacket();
		pkt.projectileID = p.projectileID;
		pkt.teamIDFiredFrom = GameTeams.gameTeamsLookup.indexOf(p.team);
		for(InGamePlayer player : players){
//			System.out.println("sending projectile fire to player " + player.getPlayerID());
			runner.conn.send(runner.conn.playerToConnection.get(player), pkt.getBytes().array());
		}

	}

	private void notifyPlayersAboutProjectileFired(Projectile p) {
		NotifyProjectileFiredPacket pkt = new NotifyProjectileFiredPacket();
		pkt.projectileID = p.projectileID;
		pkt.teamIDFiredFrom = GameTeams.gameTeamsLookup.indexOf(p.team);
		for(InGamePlayer player : players){
//			System.out.println("sending projectile fire to player " + player.getPlayerID());
			runner.conn.send(runner.conn.playerToConnection.get(player), pkt.getBytes().array());
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
		for(Projectile p : projectiles){
			ProjectilePositionPacket posPak = new ProjectilePositionPacket();
			posPak.x = p.getX();
			posPak.y = p.getY();
			posPak.projectileID = p.projectileID;
			for (InGamePlayer player : players) {
//				logger.log(Level.INFO, "sending projectile packet" + posPak.toString());
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
		for (int x = 0; x < user.inventory.length; x++) {
			if (user.inventory[x] == GameItems.empty) {
				if (user.getGoldAmount() >= i.getPrice()) {
					user.setGoldAmount(user.getGoldAmount() - i.getPrice());
					user.inventory[x] = i;
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
		System.out.println("No space in inventory to buy " + i.getName());
		return RequestPlayerBuyItemResponsePacket.NO_INVENTORY_SPACE;
	}

	public int sellItem(InGamePlayer user, int itemID) {
		Item item = GameItems.allGameItemsLookup.get(itemID);
		for (int x = 0; x < user.inventory.length; x++) {
			if (user.inventory[x] == item) {
				if (user.inventory[x] != GameItems.empty) {
					user.setGoldAmount(user.getGoldAmount() + (int) (item.getPrice() * .70));
					user.inventory[x] = GameItems.empty;
					if (!item.isConsumable()) {
						for (int z = 0; z < item.getType().length; z++) {
							switch (item.getType()[z]) {
							case Health:
								user.setMaxHealth(user.getMaxHealth() - item.getEffectPoints()[z]);
								break;
							case Mana:
								user.setMaxMana(user.getMaxMana() - item.getEffectPoints()[z]);
								break;
							case PhysicalPower:
								user.setMaxHealth(user.getMaxHealth() - item.getEffectPoints()[z]);
								break;
							case AbilityPower:
								user.setPhyPow(user.getPhyPow() - item.getEffectPoints()[z]);
								break;
							case Speed:
								user.setSpeed(user.getSpeed() - item.getEffectPoints()[z]);
								break;
							case AttackSpecial:
								user.setAbiPow(user.getAbiPow() - item.getEffectPoints()[z]);
								break;
							case Armor:
								user.setArmor(user.getArmor() - item.getEffectPoints()[z]);
								break;
							case MagicResistance:
								user.setMagicResist(user.getMagicResist() - item.getEffectPoints()[z]);
								break;
							default:
								System.out.println("ERROR: Unknown item type");
								break;
							}
						}
					}
					System.out.println("You sold a " + item.getName());
					// sold
					return RequestPlayerSellItemResponsePacket.SUCCESSFUL;
				} else {
					System.out.println("You cannot sell nothing");
					// failed
					return RequestPlayerSellItemResponsePacket.NO_INVENTORY_SPACE;
				}
			}
		}
		return RequestPlayerSellItemResponsePacket.NO_INVENTORY_SPACE;

	}

	public void incrementGold() {
		for (InGamePlayer p : players) {
			p.setGoldAmount(p.getGoldAmount() + 2);
		}
	}

	public void sendStatusReport() {
		for (InGamePlayer p : players) {
			PlayerStatusReport rpt = new PlayerStatusReport();
			rpt.playerID = p.getPlayerID();
			rpt.playerHealth = p.getCurrentHealth();
			rpt.playerGold = p.getGoldAmount();
			rpt.playerMana = p.getCurrentMana();
			System.out.println("status report about player " + p.getPlayerID());
			for (InGamePlayer p2 : players) {
				runner.conn.send(runner.conn.playerToConnection.get(p2), rpt.getBytes().array());
			}
		}
	}
}
