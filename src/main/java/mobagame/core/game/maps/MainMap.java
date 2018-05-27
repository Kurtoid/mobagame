package mobagame.core.game.maps;

import mobagame.core.game.GameTeams;
import mobagame.core.game.Tower;
import mobagame.launcher.MapPanel;

import java.awt.geom.Arc2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;
import java.util.ArrayList;

public class MainMap {
	private Path2D map;
	public int height;
	public int width;
	public ArrayList<Tower> towers = new ArrayList<>();
	public MainMap() {
	}
	public MainMap(int w, int h) {
		width = w;
		height = h;
	}

	public Path2D getMap() {
		return map;
	}

	public MainMap makeMap() {
		if (width == 0 || height == 0) {
			System.err.println("set the size dumbass");
			return null;
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

		Tower coreTop = new Tower(1000);
		coreTop.x = normalizeWidth(87.5);
		coreTop.y = normalizeWidth(100-87.5);
		coreTop.type = Tower.TowerType.CORE;
		coreTop.team = GameTeams.highTeam;
		towers.add(coreTop);

		Tower respawnTop = new Tower(500);
		respawnTop.x = normalizeWidth(77);
		respawnTop.y = normalizeWidth(100-77);
		respawnTop.type = Tower.TowerType.RESPAWN;
		respawnTop.team = GameTeams.highTeam;
		towers.add(respawnTop);

		Tower normalTop = new Tower(500);
		normalTop.x = normalizeWidth(66.667);
		normalTop.y = normalizeWidth(100-66.667);
		normalTop.type = Tower.TowerType.NORMAL;
		normalTop.team = GameTeams.highTeam;
		towers.add(normalTop);

		normalTop = new Tower(500);
		normalTop.x = normalizeWidth(51.556);
		normalTop.y = normalizeWidth(100-58.356);
		normalTop.type = Tower.TowerType.NORMAL;
		normalTop.team = GameTeams.highTeam;
		towers.add(normalTop);

		normalTop = new Tower(500);
		normalTop.x = normalizeWidth(58.356);
		normalTop.y = normalizeWidth(100-51.556);
		normalTop.type = Tower.TowerType.NORMAL;
		normalTop.team = GameTeams.highTeam;
		towers.add(normalTop);


		Tower coreBottom = new Tower(1000);
		coreBottom.x = normalizeWidth(100-87.5);
		coreBottom.y = normalizeWidth(87.5);
		coreBottom.type = Tower.TowerType.CORE;
		coreBottom.team = GameTeams.lowTeam;
		towers.add(coreBottom);

		Tower respawnBottom = new Tower(500);
		respawnBottom.x = normalizeWidth(100-77);
		respawnBottom.y = normalizeWidth(77);
		respawnBottom.type = Tower.TowerType.RESPAWN;
		respawnBottom.team = GameTeams.lowTeam;
		towers.add(respawnBottom);

		Tower normalBottom = new Tower(500);
		normalBottom.x = normalizeWidth(100-66.667);
		normalBottom.y = normalizeWidth(66.667);
		normalBottom.type = Tower.TowerType.NORMAL;
		normalBottom.team = GameTeams.lowTeam;
		towers.add(normalBottom);

		normalBottom = new Tower(500);
		normalBottom.x = normalizeWidth(100-51.556);
		normalBottom.y = normalizeWidth(58.356);
		normalBottom.type = Tower.TowerType.NORMAL;
		normalBottom.team = GameTeams.lowTeam;
		towers.add(normalBottom);

		normalBottom = new Tower(500);
		normalBottom.x = normalizeWidth(100-58.356);
		normalBottom.y = normalizeWidth(51.556);
		normalBottom.type = Tower.TowerType.NORMAL;
		normalBottom.team = GameTeams.lowTeam;
		towers.add(normalBottom);


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
		return this;

	}

	public void setServerMode() {
		width = 1000;
		height = 1000;
	}

	public void setSize(int w, int h) {
		width = h;
		height = h;
	}

	private double normalizeWidth(double input) {
		return (input / 100) * height;
	}

	private double normalizeHeight(double input) {
		return (input / 100) * height;
	}

	// make another version of this in MapPanel to convert from server to client
	public static double normalizeWidth(double input, double width) {
		return (input / 100) * width;
	}

	public static double normalizeHeight(double input, double height) {
		return (input / 100) * height;
	}

}
