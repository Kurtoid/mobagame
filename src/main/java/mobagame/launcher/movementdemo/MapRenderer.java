package mobagame.launcher.movementdemo;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Arc2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;

import javax.swing.JPanel;

public class MapRenderer extends JPanel {
	Path2D upperMapPath;
	Path2D lowerMapPath;

	public MapRenderer() {
		upperMapPath = new Path2D.Double();
		upperMapPath.moveTo(0, 0);
		upperMapPath.lineTo(90, 0);
		upperMapPath.moveTo(90, 0);
		upperMapPath.lineTo(52.289, 100 - 62.289);
		upperMapPath.append(new Arc2D.Double(37.5, 100 - 62.5, 25, 25, 79.45, 111.1, Arc2D.OPEN), true);
		upperMapPath.lineTo(0, 90);
		upperMapPath.lineTo(0, 0);

		lowerMapPath = new Path2D.Double();
		lowerMapPath.moveTo(10, 100);
		lowerMapPath.lineTo(47.711, 100 - 37.711);
		lowerMapPath.append(new Arc2D.Double(37.5, 100 - 62.5, 25, 25, -110.55, 111.1, Arc2D.OPEN), true);
		// TODO: one of these is misaligned. fix me
		lowerMapPath.lineTo(100, 10);
		lowerMapPath.lineTo(100, 100);
		lowerMapPath.lineTo(0, 100);

		upperMapPath.append(lowerMapPath, false);
		// mapPath.closePath();
		// mapPath.closePath();
	}

	@Override
	public void paint(Graphics graphics) {
		super.paint(graphics);
		Graphics2D g = (Graphics2D) graphics;
		g.draw(upperMapPath);
		g.drawRect(0, 0, 100, 100);
	}
}
