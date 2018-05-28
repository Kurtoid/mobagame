package mobagame.core.game;

import mobagame.core.game.maps.MainMap;

import java.awt.geom.Point2D;

public class Tower extends GameObject {
	public TowerType type;
	int health;
	MainMap map;
	public enum TowerType {
		CORE, RESPAWN, NORMAL
	}

	public Tower(int i) {
		super();
		health = i;
		lastFiredTime = System.currentTimeMillis();
		coolDownTime = 1000;
	}

	long lastFiredTime;

	/**
	 * in milliseconds
	 */
	long coolDownTime;

	public boolean canFire() {
		return System.currentTimeMillis() - lastFiredTime > coolDownTime;
	}

	public Projectile fire(Point2D.Double pos, Game g) {
		lastFiredTime = System.currentTimeMillis();
		Projectile p = new Projectile(g.map);
		p.target = pos;
		p.firedBy = this;
		p.firedFrom = this.pos;
		p.team = this.team;
		p.pos = this.pos;
		p.mover.setTarget(p.target.getX(), p.target.getY());
		return p;
	}
}
