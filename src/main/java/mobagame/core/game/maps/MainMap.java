package mobagame.core.game.maps;

import java.awt.geom.Arc2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;

public class MainMap {
	private Path2D map;
	int height;
	int width;

	public MainMap() {
	}

	public Path2D getMap() {
		return map;
	}

	public void makeMap() {
		if (width == 0 || height == 0) {
			System.err.println("set the size dumbass");
			return;
		}
		/***********
		 * * H* * * ( ) * H *
		 ***********
		 */
		Path2D upperMapPath = new Path2D.Double();
		upperMapPath.moveTo(0, 0);
		upperMapPath.lineTo(normalizeWidth(90), 0);
		// upperMapPath.moveTo(normalizeWidth(90), 0);
		upperMapPath.lineTo(normalizeWidth(52.289), normalizeHeight(100 - 62.289));
		upperMapPath.append(new Arc2D.Double(normalizeWidth(37.5), normalizeHeight(100 - 62.5), normalizeWidth(25),
				normalizeHeight(25), 79.45, 111.1, Arc2D.OPEN), true);
		upperMapPath.lineTo(0, normalizeHeight(90));
		upperMapPath.lineTo(0, 0);

		Path2D lowerMapPath = new Path2D.Double();
		lowerMapPath.moveTo(normalizeWidth(10), normalizeHeight(100));
		lowerMapPath.lineTo(normalizeWidth(47.711), normalizeHeight(100 - 37.711));
		lowerMapPath.append(new Arc2D.Double(normalizeWidth(37.5), normalizeHeight(100 - 62.5), normalizeWidth(25),
				normalizeHeight(25), -100.55, 111.1, Arc2D.OPEN), true);
		lowerMapPath.lineTo(normalizeWidth(100), normalizeHeight(10));
		lowerMapPath.lineTo(normalizeWidth(100), normalizeHeight(100));
		lowerMapPath.lineTo(0, normalizeHeight(100));

		upperMapPath.append(lowerMapPath, false);

		Path2D innerCircle = new Path2D.Double();
		innerCircle.moveTo(normalizeWidth(50), normalizeHeight(50));
		innerCircle.append(
				new Ellipse2D.Double(normalizeWidth(45), normalizeHeight(45), normalizeWidth(10), normalizeHeight(10)),
				false);

		upperMapPath.append(innerCircle, false);

		/*
		 * Path2D mapOuterBounds = new Path2D.Double(); mapOuterBounds.moveTo(0, 0);
		 * mapOuterBounds.lineTo(normalizeWidth(100), normalizeHeight(0));
		 * mapOuterBounds.moveTo(normalizeWidth(100), normalizeHeight(0));
		 * mapOuterBounds.lineTo(normalizeWidth(100), normalizeHeight(100));
		 * mapOuterBounds.moveTo(normalizeWidth(100), normalizeHeight(100));
		 * mapOuterBounds.lineTo(normalizeWidth(0), normalizeHeight(100));
		 * mapOuterBounds.moveTo(normalizeWidth(0), normalizeHeight(100));
		 * mapOuterBounds.lineTo(0, 0); //
		 */
		map = upperMapPath;

	}

	public void setServerMode() {
		width = 1000;
		height = 1000;
	}

	public void setSize(int w, int h) {
		width = w;
		height = h;
	}

	private double normalizeWidth(double input) {
		return (input / 100) * height;
	}

	private double normalizeHeight(double input) {
		return (input / 100) * height;
	}

	public static double normalizeWidth(double input, double width) {
		return (input / 100) * width;
	}

	public static double normalizeHeight(double input, double height) {
		return (input / 100) * height;
	}

}
