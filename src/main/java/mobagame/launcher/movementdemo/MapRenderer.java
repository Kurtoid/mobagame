package mobagame.launcher.movementdemo;

import java.awt.*;
import java.awt.geom.Arc2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;

import javax.swing.JPanel;

public class MapRenderer extends JPanel {
	Path2D upperMapPath;
	Path2D lowerMapPath;

	Path2D mapOuterBounds;
	public MapRenderer(int width, int height) {
		setSize(width, height);
		upperMapPath = new Path2D.Double();
		upperMapPath.moveTo(0, 0);
		upperMapPath.lineTo(normalizeWidth(90), 0);
//		upperMapPath.moveTo(normalizeWidth(90), 0);
		upperMapPath.lineTo(normalizeWidth(52.289), normalizeHeight(100 - 62.289));
		upperMapPath.append(new Arc2D.Double(normalizeWidth(37.5), normalizeHeight(100 - 62.5), normalizeWidth(25), normalizeHeight(25), 79.45, 111.1, Arc2D.OPEN), true);
		upperMapPath.lineTo(0, normalizeHeight(90));
		upperMapPath.lineTo(0, 0);

		lowerMapPath = new Path2D.Double();
		lowerMapPath.moveTo(normalizeWidth(10), normalizeHeight(100));
		lowerMapPath.lineTo(normalizeWidth(47.711), normalizeHeight(100 - 37.711));
		lowerMapPath.append(new Arc2D.Double(normalizeWidth(37.5), normalizeHeight(100 - 62.5), normalizeWidth(25), normalizeHeight(25), -100.55, 111.1, Arc2D.OPEN), true);
		// TODO: one of these is misaligned. fix me
		lowerMapPath.lineTo(normalizeWidth(100), normalizeHeight(10));
		lowerMapPath.lineTo(normalizeWidth(100), normalizeHeight(100));
		lowerMapPath.lineTo(0, normalizeHeight(100));

		upperMapPath.append(lowerMapPath, false);

		mapOuterBounds =new Path2D.Double();
		mapOuterBounds.moveTo(0,0);
		mapOuterBounds.lineTo(normalizeWidth(100), normalizeHeight(0));
		mapOuterBounds.moveTo(normalizeWidth(100), normalizeHeight(0));
		mapOuterBounds.lineTo(normalizeWidth(100), normalizeHeight(100));
		mapOuterBounds.moveTo(normalizeWidth(100), normalizeHeight(100));
		mapOuterBounds.lineTo(normalizeWidth(0), normalizeHeight(100));
		mapOuterBounds.moveTo(normalizeWidth(0), normalizeHeight(100));
		mapOuterBounds.lineTo(0,0);
		// mapPath.closePath();
		// mapPath.closePath();
	}

	private double normalizeWidth(double input) {
		return (input / 100) * getWidth();
	}

	private double normalizeHeight(double input) {
		return (input / 100) * getHeight();
	}

	@Override
	public void paint(Graphics graphics) {
		super.paint(graphics);
		Graphics2D g = (Graphics2D) graphics;
		g.setColor(Color.black);
		g.draw(upperMapPath);
		g.setColor(Color.RED);
		g.setStroke(new BasicStroke(10));
		g.draw(mapOuterBounds);

//		g.drawOval((int)normalizeWidth(50-12.5), (int)normalizeHeight(50-12.5), 250, 250);
	}
}
