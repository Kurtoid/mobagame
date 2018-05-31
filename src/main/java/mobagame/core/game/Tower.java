package mobagame.core.game;

import mobagame.core.game.maps.MainMap;

import java.awt.geom.Point2D;

public class Tower extends GameObject {
	public TowerType type;
	public int health;
	MainMap map;
	public enum TowerType {
		CORE, RESPAWN, NORMAL
	}

	public Tower(int i) {
		super();
		health = i;
		lastFiredTime = System.currentTimeMillis();
		coolDownTime = 10000;
	}

	long lastFiredTime;

	/**
	 * in milliseconds
	 */
	long coolDownTime;

	public boolean canFire() {
		return System.currentTimeMillis() - lastFiredTime > coolDownTime;
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
