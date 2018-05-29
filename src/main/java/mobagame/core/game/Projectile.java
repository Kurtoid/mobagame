package mobagame.core.game;

import mobagame.core.game.maps.MainMap;

import java.awt.geom.Point2D;
import java.util.Random;

/**
 * a projectile fired by a player or tower
 *
 * @author Kurt Wilson
 */
public class Projectile extends GameObject {
	double damage;
	public GameObject firedBy;
	public ObjectMover mover;
	public boolean active = true;
	public int projectileID = new Random().nextInt();
	public Projectile(MainMap map) {
		speed = 100;
		mover = new ObjectMover(map, this);
		mover.hitTowers = false;
	}

	/**
	 * may be null
	 */
	public void update() {
		mover.update();
//		if (new Point2D.Double(target.getX(), target.getY()).distance(pos) < speed) {
//			active = false;
//		}
	}

	public Point2D.Double target;

	public Point2D.Double firedFrom;
}
