package mobagame.core.game;

import mobagame.core.game.maps.MainMap;

public class PlayerMover {
	double speed = 1;
	double targetx = 500;
	double targety = 500;
	InGamePlayer player;
	MainMap map;

	public PlayerMover(MainMap map, InGamePlayer player) {
		this.map = map;
		this.player = player;
		targetx = player.getX();
		targety = player.getY();
	}

	public void update() {
		double angleRadians = Math.atan2(targety - player.getY(), targetx - player.getX());
		double oldx = player.getX();

		double x = (oldx + (speed * Math.cos(angleRadians)));

		/*
		 * if (x < targetx) { x++; } if (x > targetx) { x--; } //
		 */
		if (map.getMap().intersects(x, player.getY(), 5, 5)) {
			x = oldx;
		}
		double oldy = player.getY();
		double y = (oldy + (speed * Math.sin(angleRadians)));
		/*
		 * if (y > targety) { y--; } if (y < targety) { y++; } //
		 */
		if (map.getMap().intersects(x, y, 5, 5)) {
			y = oldy;
		}
		player.setX(x);
		player.setY(y);
//		System.out.println(x + "\t" + y);
	}

	public void setTarget(int newX, int newY) {
		targetx = newX;
		targety = newY;
	}

	public boolean atTarget() {
		return targetx == player.getX() && targety == player.getY();
	}
}
