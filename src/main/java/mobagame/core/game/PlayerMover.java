package mobagame.core.game;

import mobagame.core.game.maps.MainMap;

public class PlayerMover {
	double x = 500;
	double y = 600;
	double speed = 1;
	double targetx = 500;
	double targety = 500;
	InGamePlayer player;
	MainMap map;

	public PlayerMover(MainMap map, InGamePlayer player) {
		this.map = map;
		this.player = player;
	}

	public void update() {
		double angleRadians = Math.atan2(targety - y, targetx - x);
		double oldx = x;

		x = (x + (speed * Math.cos(angleRadians)));

		/*
		 * if (x < targetx) { x++; } if (x > targetx) { x--; } //
		 */
		if (map.getMap().intersects(x, y, 5, 5)) {
			x = oldx;
		}
		double oldy = y;
		y = (y + (speed * Math.sin(angleRadians)));
		/*
		 * if (y > targety) { y--; } if (y < targety) { y++; } //
		 */
		if (map.getMap().intersects(x, y, 5, 5)) {
			y = oldy;
		}

	}
	public void setTarget(int newX, int newY) {
		targetx = newX;
		targety = newY;
	}
}
