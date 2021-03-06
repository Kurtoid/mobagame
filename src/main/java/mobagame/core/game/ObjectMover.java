package mobagame.core.game;

import mobagame.core.game.maps.MainMap;

import java.awt.geom.Ellipse2D;

public class ObjectMover {
	// double speed = 4;
	double targetx = 500;
	double targety = 500;
	GameObject player;
	MainMap map;
	boolean hitTowers = true;

	public ObjectMover(MainMap map, GameObject movableObject) {
		this.map = map;
		this.player = movableObject;
		targetx = movableObject.getX();
		targety = movableObject.getY();
	}

	public void update() {
		double speed = player.getSpeed() / 50;
		double angleRadians = Math.atan2(targety - player.getY(), targetx - player.getX());
		double oldx = player.getX();
		double oldy = player.getY();
		double y = (oldy + (speed * Math.sin(angleRadians)));
		double x = (oldx + (speed * Math.cos(angleRadians)));
		if (Math.sqrt((targetx - oldx) * (targetx - oldx) + (targety - oldy) * (targety - oldy)) < speed) {
			x = targetx;
			y = targety;
			player.setX(x);
			player.setY(y);
			return;
		}
		if (Math.abs(targetx - x) < Math.abs(speed * Math.cos(angleRadians))
				&& Math.abs(targety - y) < Math.abs(speed * Math.sin(angleRadians))
				&& !doesCollide(targetx, targety, 10, 10)) {
			x = targetx;
			y = targety;
		} else {

			/*
			 * if (x < targetx) { x++; } if (x > targetx) { x--; } //
			 */
			if (doesCollide(x, player.getY(), 10, 10)) {
				x = oldx;
			}

			/*
			 * if (y > targety) { y--; } if (y < targety) { y++; } //
			 */
			if (doesCollide(x, y, 10, 10)) {
				y = oldy;
			}
		}
		player.setX(x);
		player.setY(y);
//		 System.out.println(x + "\t" + y);
	}

	public void setTarget(double newX, double newY) {
		targetx = newX;
		targety = newY;
	}

	boolean doesCollide(double x, double y, double playerWidth, double playerHeight) {
		boolean collides = map.getMap().intersects(x - playerHeight / 2, y - playerHeight / 2, playerHeight, playerHeight);
		if (hitTowers) {
//		if(false){
			for (int i = 0; i < map.towers.size() && !collides; i++) {
				double towerSize = 2 * (map.width / 100);
				if (map.towers.get(i).type == Tower.TowerType.CORE) {
					towerSize = 4 * (map.width / 100);
				} else if (map.towers.get(i).type == Tower.TowerType.RESPAWN) {
					towerSize = 3 * (map.width / 100);
				}
				Ellipse2D.Double footprint = new Ellipse2D.Double(map.towers.get(i).getX() - towerSize / 2, map.towers.get(i).getY() - towerSize / 2, towerSize, towerSize);
				if (footprint.intersects(x - playerHeight / 2, y - playerHeight / 2, playerHeight, playerHeight)) {
					collides = true;
				}
			}
		}
		if (player instanceof InGamePlayer) {
			System.out.println("collides " + collides);
		}
		return collides;
	}

	public boolean atTarget() {
		return targetx == player.getX() && targety == player.getY();
	}

	public void setMap(MainMap map) {
		this.map = map;
	}
}
