package mobagame.core.game;

import mobagame.core.game.maps.MainMap;

import java.awt.geom.Point2D;

/**
 * @author Kurt Wilson
 */
public class Tower extends GameObject {
	public TowerType type;
	public int health;
	public double maxHealth;
	MainMap map;
	public int id;

	public enum TowerType {
		CORE, RESPAWN, NORMAL
	}

	public Tower(int id, int i) {
		super();
		this.id = id;
		health = i;
		maxHealth = i;
		lastFiredTime = System.currentTimeMillis();
		coolDownTime = 10000;
	}

	long lastFiredTime;

	/**
	 * in milliseconds
	 */
	long coolDownTime;

	public boolean canFire() {
		return System.currentTimeMillis() - lastFiredTime > coolDownTime && health > 0;
	}

	public Projectile fire(GameObject player, Game g) {
		lastFiredTime = System.currentTimeMillis();
		SeekingProjectile p = new SeekingProjectile(g.map);
		p.target = player.pos;
		p.firedBy = this;
		p.firedFrom = new Point2D.Double(pos.getX(), pos.getY());
		p.team = this.team;
		p.pos = new Point2D.Double(pos.getX(), pos.getY());
		p.mover.setTarget(p.target.getX(), p.target.getY());
		p.targetObject = player;
		return p;
	}
}
