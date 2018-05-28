package mobagame.core.game;

import mobagame.core.game.maps.MainMap;
import mobagame.server.database.PlayerAccount;

import java.awt.geom.Point2D;

/**
 * a projectile fired by a player or tower
 *
 * @author Kurt Wilson
 */
public class Projectile extends GameObject {
	double damage;
	public GameObject firedBy;
	public PlayerMover mover;
	public boolean active = true;

	public Projectile(MainMap map) {
		speed = 50;
		mover = new PlayerMover(map, this);
		mover.hitTowers = false;
	}

	/**
	 * may be null
	 */
	public void update() {
		mover.update();
		if (Math.sqrt((target.getX() - pos.getX()) * (target.getX() - pos.getX()) + (target.getY() - pos.getY()) * (target.getY() - pos.getY())) < speed) {
			active = false;
		}
	}

	public Point2D.Double target;

	public Point2D.Double firedFrom;
}
