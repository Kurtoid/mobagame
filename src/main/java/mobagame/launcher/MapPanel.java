package mobagame.launcher;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.awt.image.FilteredImageSource;
import java.io.IOException;
import java.util.Iterator;

import javax.swing.JPanel;

import mobagame.core.game.Game;
import mobagame.core.game.GameCharcters;
import mobagame.core.game.GameItems;
import mobagame.core.game.InGamePlayer;
import mobagame.core.game.Item;
import mobagame.core.game.*;
import mobagame.core.game.maps.MainMap;
import mobagame.core.networking.packets.*;
import mobagame.launcher.game.gamePlayObjects.ClickMarker;
import mobagame.launcher.networking.RspHandler;
import mobagame.launcher.networking.ServerConnection;

/**
 * @author Kurt Wilson
 * @author Katelynn Morrison
 */
public class MapPanel extends JPanel implements Runnable {
	Game game;

	int translateX = 0;
	int translateY = 0;
	int scaleX = 1;
	int scaleY = 1;
	boolean running = true;
	boolean paused = false;
	double fps;
	long frameCount = 0;

	int mouseX;
	int mouseY;
	ServerConnection conn;
	ClickMarker marker;
	static Image RED_TOWER_PROJECTILE_IMAGE;
	static Image BLUE_TOWER_PROJECTILE_IMAGE;
	static Image RED_TOWER_IMAGE;
	static Image BLUE_TOWER_IMAGE;
	static Image DESTROYED_TOWER_IMAGE;


	public MapPanel(Game g) {
		setUpImages();
		marker = new ClickMarker();
		marker.timeCreated = System.currentTimeMillis();
		marker.width = 20;
		marker.height = 20;
		game = g;
		try {
			conn = ServerConnection.getInstance(ServerConnection.ip, ServerConnection.port);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {

			}

			@Override
			public void keyPressed(KeyEvent e) {

			}

			@Override
			public void keyReleased(KeyEvent e) {

			}
		});
		addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Point p = e.getPoint();
				try {
					getCurrentTransform().inverseTransform(p, p);
				} catch (NoninvertibleTransformException e1) {
					e1.printStackTrace();
				}
				marker.x = p.x;
				marker.y = p.y;
				marker.timeCreated = System.currentTimeMillis();

				System.out.println("mouseclick " + p.toString());
				game.getPlayerPlayer().mover.setTarget(e.getX(), e.getY());
				RequestPlayerMovementPacket move = new RequestPlayerMovementPacket();
				move.x = convertHeightToServer(p.x);
				move.y = convertWidthToServer(p.y);
				System.out.println("sent to server " + move.x + "  " + move.y);
				try {
					conn.send(move.getBytes().array());
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					System.out.println("failed to send movement update");
				}
			}

			@Override
			public void mousePressed(MouseEvent e) {

			}

			@Override
			public void mouseReleased(MouseEvent e) {

			}

			@Override
			public void mouseEntered(MouseEvent e) {

			}

			@Override
			public void mouseExited(MouseEvent e) {

			}
		});
		addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseMoved(MouseEvent e) {
				mouseX = e.getX();
				mouseY = e.getY();
			}

			@Override
			public void mouseDragged(MouseEvent e) {
			//	translateX += ((e.getX() - mouseX) / scaleX);
			//	translateY += ((e.getY() - mouseY) / scaleY);
			//	mouseX = e.getX();
			//	mouseY = e.getY();
			//	System.out.println("mouse dragged ");
			//	repaint();
			}
		});
		addMouseWheelListener(new MouseWheelListener() {

			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {
				scaleX -= e.getWheelRotation();
				scaleY -= e.getWheelRotation();

				scaleX = Math.max(1, scaleX);
				scaleY = Math.max(1, scaleY);
				System.out.print("mouse wheel moved ");

				System.out.println(scaleX + " " + scaleY);
				repaint();
			}
		});
	}

	private void setUpImages() {
		Image i = Toolkit.getDefaultToolkit().getImage("resources/Projectiles/TowerProjectile.png");
		RED_TOWER_PROJECTILE_IMAGE = Toolkit.getDefaultToolkit().createImage(new FilteredImageSource(i.getSource(), new TeamColorFilter(GameTeams.highTeam.color)));
		BLUE_TOWER_PROJECTILE_IMAGE = Toolkit.getDefaultToolkit().createImage(new FilteredImageSource(i.getSource(), new TeamColorFilter(GameTeams.lowTeam.color)));
		RED_TOWER_IMAGE = Toolkit.getDefaultToolkit().createImage("resources/Towers/REDTOWER.PNG");
		BLUE_TOWER_IMAGE = Toolkit.getDefaultToolkit().createImage("resources/Towers/BlueTower.png");
		DESTROYED_TOWER_IMAGE = Toolkit.getDefaultToolkit().createImage("resources/Towers/DestroyedTower.png");

	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D graphics = (Graphics2D) g;
		graphics.setColor(Color.black);
		graphics.setStroke(new BasicStroke(getHeight() / 100));
		Path2D tmp = (Path2D) game.map.getMap().clone();
		tmp.transform(getCurrentTransform());
		graphics.draw(tmp);
		// for (InGamePlayer player : game.players) {
		// graphics.fillRect((int) player.getX(), (int) player.getY(), 20, 20);
		// }

//		if (System.currentTimeMillis() - marker.timeCreated > 3000) {
		{
			Point.Double p = new Point.Double(marker.x, marker.y);
			getCurrentTransform().transform(p, p);
			graphics.setColor(Color.GREEN);
			graphics.fillRect((int) p.getX(), (int) p.getY(), marker.width, marker.height);
		}
		graphics.setColor(Color.RED);
		for(InGamePlayer player : game.players){
			Point.Double point = new Point2D.Double(player.getX(), player.getY());
			double pWidth = convertWidthFromServer(10, game.map.width) * scaleX;
			double pHeight = convertHeightFromServer(10, game.map.height) * scaleY;
//				double pWidth = 0;
//				double pHeight = 0;

			point.x = point.getX();
			point.y = point.getY();
			getCurrentTransform().transform(point, point);
			graphics.fillRect((int)(point.getX()-pWidth/2), (int)(point.getY()-pHeight/2), (int)pWidth, (int)pHeight);
		}
//		}

		for(Tower t : game.map.towers){
			Point.Double p = new Point2D.Double(t.getX(), t.getY());
			getCurrentTransform().transform(p, p);
			int towerSize=2*(game.map.width/100);
			if(t.type == Tower.TowerType.CORE){
				towerSize = 4*(game.map.width/100);
			}else if(t.type == Tower.TowerType.RESPAWN){
				towerSize = 3*(game.map.width/100);
			}
			towerSize *= scaleX;
//			graphics.fillOval((int)p.x-towerSize/2, (int)p.y-towerSize/2, towerSize, towerSize);
			if(t.health>0) {
				if (t.team == GameTeams.lowTeam) {
					graphics.drawImage(RED_TOWER_IMAGE, (int) p.x - towerSize / 2, (int) p.y - towerSize / 2, towerSize, towerSize, null, null);
				} else {
					graphics.drawImage(BLUE_TOWER_IMAGE, (int) p.x - towerSize / 2, (int) p.y - towerSize / 2, towerSize, towerSize, null, null);
				}
			}else{
				graphics.drawImage(DESTROYED_TOWER_IMAGE, (int) p.x - towerSize / 2, (int) p.y - towerSize / 2, towerSize, towerSize, null, null);
			}
			HealthBarDrawer.draw((Graphics2D) g, t.health,t.maxHealth,Color.RED,  p, getSize());
		}

		for(Projectile proj : game.projectiles){
			graphics.setColor(proj.team.color);
//			graphics.setColor(Color.GREEN);
			Point.Double p = new Point2D.Double(proj.getX(), proj.getY());
			getCurrentTransform().transform(p, p);
			int towerSize=1*(game.map.width/100);
			towerSize *= scaleX;
//			graphics.fillOval((int)p.x-towerSize/2, (int)p.y-towerSize/2, towerSize, towerSize);
			graphics.drawImage(RED_TOWER_PROJECTILE_IMAGE, (int)p.x-towerSize/2, (int)p.y-towerSize/2, towerSize, towerSize, null, null);


		}
	}

	private AffineTransform getCurrentTransform() {
		AffineTransform tx = new AffineTransform();
		double centerX = game.map.width;
		double centerY = game.map.height;
		tx.translate(centerX, centerY);
		tx.scale(scaleX, scaleY);
		tx.translate(-centerX, -centerY);

		tx.translate(translateX, translateY);
		return tx;
	}

	void resetPanAndZoom() {
		System.out.println();
		translateX = getWidth() / 2 - game.map.width / 2;
		translateY = getHeight() / 2 - game.map.height / 2;
		scaleX = 1;
		scaleY = 1;
	}

	@Override
	public void run() {
		System.out.println("running mapPanel");
		RspHandler h = conn.getHandler();
		// Only run this in another Thread!
		// This value would probably be stored elsewhere.
		final double GAME_HERTZ = 20;
		// Calculate how many ns each frame should take for our target game hertz.
		final double TIME_BETWEEN_UPDATES = 1000000000 / GAME_HERTZ;
		// At the very most we will update the game this many times before a new render.
		// If you're worried about visual hitches more than perfect timing, set this to
		// 1.
		final int MAX_UPDATES_BEFORE_RENDER = 5;
		// We will need the last update time.
		double lastUpdateTime = System.nanoTime();
		// Store the last time we rendered.
		double lastRenderTime = System.nanoTime();

		// If we are able to get as high as this FPS, don't render again.
		final double TARGET_FPS = 60;
		final double TARGET_TIME_BETWEEN_RENDERS = 1000000000 / TARGET_FPS;

		// Simple way of finding FPS.
		int lastSecondTime = (int) (lastUpdateTime / 1000000000);

		while (running) {
			double now = System.nanoTime();
			int updateCount = 0;

			if (!paused) {
				// Do as many game updates as we need to, potentially playing catchup.
				while (now - lastUpdateTime > TIME_BETWEEN_UPDATES && updateCount < MAX_UPDATES_BEFORE_RENDER) {
					// updateGame();
					// do server pings here
					int processed = 0;
//					System.out.println("updating from server");
					/*if(game.projectiles.size()>0){
						System.out.println(game.projectiles.get(0).pos.toString());
					}*/
					System.out.println("projectiles: " + game.projectiles.size());
					while (processed < 20) {
						Packet p = h.getResponse(Packet.class);
						processed++;
						if (p == null) {
//							System.out.println("no resposnes");
							break;
						}
//						System.out.println("responses to process!");
						System.out.println(p.getClass());
						if (PlayerPositionPacket.class.isInstance(p)) {
//							System.out.println("new player position");
							PlayerPositionPacket pkt = (PlayerPositionPacket) p;
							if (pkt != null) {
								InGamePlayer player = game.getPlayer(pkt.playerID);
								if(player==null){
									System.out.println("Skipped player " + pkt.playerID);
									continue;
								}
//								System.out.println("found a player");
								pkt.x = convertWidthFromServer(pkt.x, game.map.width);
								pkt.y = convertHeightFromServer(pkt.y, game.map.height);

								player.setX(pkt.x);
								player.setY(pkt.y);
//						System.out.println(p.x + " " + p.y);

							}
						} else if ( NotifyPlayerJoinedGamePacket.class.isInstance(p)) {
							NotifyPlayerJoinedGamePacket pkt = (NotifyPlayerJoinedGamePacket) p;
							System.out.println("new player!");
							if(game.getPlayer(pkt.playerID) == null) {
								InGamePlayer plr = new InGamePlayer(pkt.playerID,  GameCharcters.reaper);
								plr.team = GameTeams.lowTeam;
								game.players.add(plr);
								System.out.println("new player added");
							}
						}else if(NotifyPlayerDisconnectedPacket.class.isInstance(p)) {
							NotifyPlayerDisconnectedPacket pkt = (NotifyPlayerDisconnectedPacket) p;
							game.players.remove(game.getPlayer(pkt.playerID));
						}else if(RequestPlayerBuyItemResponsePacket.class.isInstance(p)) {
							RequestPlayerBuyItemResponsePacket pkt = (RequestPlayerBuyItemResponsePacket) p;
							if(pkt.status == pkt.SUCCESSFUL) {
								Item i = GameItems.allGameItems[pkt.itemID];
								boolean foundSlot = false;
									for (int x = 0; x < game.getPlayerPlayer().inventory.length && !foundSlot; x++) {
										if (game.getPlayerPlayer().inventory[x] == GameItems.empty) {
												game.getPlayerPlayer().setGoldAmount(game.getPlayerPlayer().getGoldAmount() - i.getPrice());
												game.getPlayerPlayer().inventory[x] = i;
												System.out.println("You bought a " + i.getName());
												foundSlot = true;
										}
									}
								System.out.println("No space in inventory to buy " + i.getName());

							}
						}else if(RequestPlayerSellItemResponsePacket.class.isInstance(p)) {
							RequestPlayerSellItemResponsePacket pkt = (RequestPlayerSellItemResponsePacket) p;
							if (pkt.status == pkt.SUCCESSFUL) {
								Item i = GameItems.allGameItems[pkt.itemID];
								boolean foundSlot = false;
								for (int x = 0; x < game.getPlayerPlayer().inventory.length && !foundSlot; x++) {
									if (game.getPlayerPlayer().inventory[x] == i) {
										game.getPlayerPlayer().setGoldAmount(game.getPlayerPlayer().getGoldAmount() + i.getPrice());
										game.getPlayerPlayer().inventory[x] = GameItems.empty;
										System.out.println("You sold a " + i.getName());
										foundSlot = true;
									}
								}

								System.out.println("No space in inventory to sell " + i.getName());

							}
						}else if(PlayerStatusReport.class.isInstance(p)) {
							PlayerStatusReport rpt = (PlayerStatusReport) p;
							InGamePlayer player = game.getPlayer(rpt.playerID);
							if(player!=null) {
								player.setCurrentHealth(rpt.playerHealth);
								player.setCurrentMana(rpt.playerMana);
								player.setGoldAmount(rpt.playerGold);
							}
						}else if(PlayerUseItemResponsePacket.class.isInstance(p)) {
							PlayerUseItemResponsePacket res = (PlayerUseItemResponsePacket) p;
							int itemID = res.itemID;
							InGamePlayer player = game.getPlayerPlayer();
							boolean used = false;
							if (res.used != 0) {
								for(int x = 0; x < player.inventory.length && !used; x++) {
									if (GameItems.allGameItemsLookup.indexOf(player.inventory[x]) == itemID) {
										player.setItem(x, GameItems.empty);
										used = true;
									}
								}
							}
						}else if(NotifyProjectileFiredPacket.class.isInstance(p)){
							System.out.println("firing projectile");
							NotifyProjectileFiredPacket pkt = (NotifyProjectileFiredPacket) p;
							SeekingProjectile proj = new SeekingProjectile(game.map);
							proj.team = GameTeams.gameTeams[pkt.teamIDFiredFrom];
							proj.projectileID = pkt.projectileID;
							System.out.println("projectile " + proj.projectileID + " added to array ");
							game.projectiles.add(proj);
						}else if(ProjectilePositionPacket.class.isInstance(p)){
							ProjectilePositionPacket pkt = (ProjectilePositionPacket) p;
//							System.out.println("looking for projectile " + pkt.projectileID);
							for(Projectile proj : game.projectiles){
								if(proj.projectileID - pkt.projectileID==0){
									proj.setX(convertWidthFromServer(pkt.x, game.map.width));
									proj.setY(convertHeightFromServer(pkt.y, game.map.height));
//									System.out.println("found projectile");
								}else{
//									System.out.println(proj.projectileID + " is not " + pkt.projectileID);
								}
							}
						}else if(NotifyProjectileRemovedPacket.class.isInstance(p)){
//							System.out.println("removing projectile");
							NotifyProjectileRemovedPacket pkt = (NotifyProjectileRemovedPacket) p;
							game.projectiles.removeIf(projectile -> projectile.projectileID == pkt.projectileID);
						}else if(NotifyTowerHealth.class.isInstance(p)){
							NotifyTowerHealth pkt = (NotifyTowerHealth) p;
							for(Tower t : game.map.towers){
								if(t.id  == pkt.towerID){
									t.health = pkt.health;
								}
							}
						}

					}

					if(getWidth()-mouseX<50) {
						translateX-=getWidth()/50;
					}
					if(mouseX<50) {
						translateX+=getWidth()/50;
					}
					if(getHeight()-mouseY<50) {
						translateY-=getHeight()/50;
					}
					if(mouseY<50) {
						translateY+=getHeight()/50;
					}


					lastUpdateTime += TIME_BETWEEN_UPDATES;
					updateCount++;
				}

				// If for some reason an update takes forever, we don't want to do an insane
				// number of catchups.
				// If you were doing some sort of game that needed to keep EXACT time, you would
				// get rid of this.
				if (now - lastUpdateTime > TIME_BETWEEN_UPDATES) {
					lastUpdateTime = now - TIME_BETWEEN_UPDATES;
				}

				// Render. To do so, we need to calculate interpolation for a smooth render.
				float interpolation = Math.min(1.0f, (float) ((now - lastUpdateTime) / TIME_BETWEEN_UPDATES));
				// drawGame(interpolation);
				repaint();
				frameCount++;

				lastRenderTime = now;

				// Update the frames we got.
				int thisSecond = (int) (lastUpdateTime / 1000000000);
				if (thisSecond > lastSecondTime) {
					System.out.println("NEW SECOND " + thisSecond + " " + frameCount);
					fps = frameCount;
					frameCount = 0;
					lastSecondTime = thisSecond;
				}

				// Yield until it has been at least the target time between renders. This saves
				// the CPU from hogging.
				while (now - lastRenderTime < TARGET_TIME_BETWEEN_RENDERS
						&& now - lastUpdateTime < TIME_BETWEEN_UPDATES) {
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

	private Point2D.Double convertPointFromServer(Point2D.Double point, int width, int height) {
		point.x = convertWidthFromServer(point.x, width);
		point.y = convertHeightFromServer(point.y, height);
		return point;
	}

	public static double convertHeightFromServer(double input, double height) {
		return (input / 1000) * height;
	}
	public static double convertWidthFromServer(double input, double width) {
		return (input / 1000) * width;
	}

	private double convertHeightToServer(double input) {
		return ((input / (double) game.map.height) * 1000);
	}

	private double convertWidthToServer(double input) {
		return  ((input / game.map.height) * 1000);
	}

}
