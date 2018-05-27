package mobagame.launcher;

import java.awt.AWTException;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Robot;
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
import java.io.IOException;

import javax.swing.JPanel;

import mobagame.core.game.Game;
import mobagame.core.game.GameCharcters;
import mobagame.core.game.GameItems;
import mobagame.core.game.InGamePlayer;
import mobagame.core.game.Item;
import mobagame.core.game.maps.MainMap;
import mobagame.core.networking.packets.NotifyPlayerDisconnectedPacket;
import mobagame.core.networking.packets.NotifyPlayerJoinedGamePacket;
import mobagame.core.networking.packets.Packet;
import mobagame.core.networking.packets.PlayerPositionPacket;
import mobagame.core.networking.packets.PlayerStatusReport;
import mobagame.core.networking.packets.PlayerUseItemResponsePacket;
import mobagame.core.networking.packets.RequestPlayerBuyItemResponsePacket;
import mobagame.core.networking.packets.RequestPlayerMovementPacket;
import mobagame.core.networking.packets.RequestPlayerSellItemResponsePacket;
import mobagame.launcher.game.gamePlayObjects.ClickMarker;
import mobagame.launcher.networking.RspHandler;
import mobagame.launcher.networking.ServerConnection;

public class MapPanel extends JPanel implements Runnable {
	MainMap map;
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

	public MapPanel(Game g) {
		marker = new ClickMarker();
		marker.timeCreated = System.currentTimeMillis();
		marker.width = 20;
		marker.height = 20;
		map = g.map;
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

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D graphics = (Graphics2D) g;
		graphics.setColor(Color.black);
		graphics.setStroke(new BasicStroke(getHeight() / 100));
		Path2D tmp = (Path2D) map.getMap().clone();
		tmp.transform(getCurrentTransform());
		graphics.draw(tmp);
		// for (InGamePlayer player : game.players) {
		// graphics.fillRect((int) player.getX(), (int) player.getY(), 20, 20);
		// }

//		if (System.currentTimeMillis() - marker.timeCreated > 3000) {
		Point.Double p = new Point.Double(marker.x, marker.y);
		getCurrentTransform().transform(p, p);
		graphics.setColor(Color.GREEN);
		graphics.fillRect((int)p.getX(), (int)p.getY(), marker.width, marker.height);
		graphics.setColor(Color.RED);
			for(InGamePlayer player : game.players){
				Point.Double point = new Point2D.Double(player.getX(), player.getY());
				point.x = convertWidthFromServer(point.getX(), map.width)-convertWidthFromServer(20, map.width);
				point.y = convertHeightFromServer(point.getY(), map.height)-convertHeightFromServer(20, map.height);
				getCurrentTransform().transform(point, point);
				graphics.fillRect((int) point.getX(), (int)point.getY(), (int)(convertWidthFromServer(40, map.width))*scaleX, (int)(convertHeightFromServer(40, map.height))*scaleY);
			}
//		}
	}

	private AffineTransform getCurrentTransform() {
		AffineTransform tx = new AffineTransform();
		double centerX = map.width;
		double centerY = map.height;
		tx.translate(centerX, centerY);
		tx.scale(scaleX, scaleY);
		tx.translate(-centerX, -centerY);

		tx.translate(translateX, translateY);
		return tx;
	}

	void resetPanAndZoom() {
		System.out.println();
		translateX = getWidth() / 2 - map.width / 2;
		translateY = getHeight() / 2 - map.height / 2;
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

					while (processed < 5) {
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
								player.setX(pkt.x);
								player.setY(pkt.y);
//						System.out.println(p.x + " " + p.y);

							}
						} else if ( NotifyPlayerJoinedGamePacket.class.isInstance(p)) {
							NotifyPlayerJoinedGamePacket pkt = (NotifyPlayerJoinedGamePacket) p;
							System.out.println("new player!");
							if(game.getPlayer(pkt.playerID) == null) {
								game.players.add(new InGamePlayer(pkt.playerID,  GameCharcters.reaper));
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
							player.setCurrentHealth(rpt.playerHealth);
							player.setCurrentMana(rpt.playerMana);
							player.setGoldAmount(rpt.playerGold);
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
						}
					}

					if(getWidth()-mouseX<20) {
						translateX-=getWidth()/50;
					}
					if(mouseX<20) {
						translateX+=getWidth()/50;
					}
					if(getHeight()-mouseY<20) {
						translateY-=getHeight()/50;
					}
					if(mouseY<20) {
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
	public static double convertHeightFromServer(double input, double width) {
		return (input / 1000) * width;
	}
	public static double convertWidthFromServer(double input, double width) {
		return (input / 1000) * width;
	}

	private double convertHeightToServer(double input) {
		return ((input / (double) map.height) * 1000);
	}

	private double convertWidthToServer(double input) {
		return  ((input / map.height) * 1000);
	}

}
