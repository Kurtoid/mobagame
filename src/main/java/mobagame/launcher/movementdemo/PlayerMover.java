package mobagame.launcher.movementdemo;

 class PlayerMover extends Thread {
	double x = 500;
	double y = 600;
	double speed = 1;
	double targetx = 500;
	double targety = 500;
	MapRenderer r;
	MovementDemo md;

	public PlayerMover(MovementDemo movementDemo, MapRenderer renderer) {
		setDaemon(true);
		md = movementDemo;
		r = renderer;
	}

	@Override
	public void run() {
		super.run();
		while (true) {
			try {
				Thread.sleep(1000 / 60);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			update();
		}
	}

	void update() {
		double angleRadians = Math.atan2(targety - y, targetx - x);
		double oldx = x;

		x = (x + (speed * Math.cos(angleRadians)));

		/*
		 * if (x < targetx) { x++; } if (x > targetx) { x--; } //
		 */
		if (r.upperMapPath.intersects(x, y, 5, 5)) {
			x = oldx;
		}
		double oldy = y;
		y = (y + (speed * Math.sin(angleRadians)));
		/*
		 * if (y > targety) { y--; } if (y < targety) { y++; } //
		 */
		if (r.upperMapPath.intersects(x, y, 5, 5)) {
			y = oldy;
		}

	}
}
