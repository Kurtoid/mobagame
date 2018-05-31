package mobagame.core.game;

import mobagame.core.game.maps.MainMap;

public class SeekingProjectile extends Projectile {
	public GameObject targetObject;

	public SeekingProjectile(MainMap map) {
		super(map);
	}

	public void update() {
		target = targetObject.pos;
		mover.setTarget(target.getX(), target.getY());
		super.update();
	}

}
