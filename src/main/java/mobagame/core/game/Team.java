package mobagame.core.game;

import java.awt.*;
import java.awt.geom.Point2D;

public class Team {
	String name;
	public Color color;
	public Team(String name, Color c, Point2D.Double spawn) {
		this.name = name;
		this.color = c;
		spawnPoint = spawn;
	}

	public Point2D.Double spawnPoint;
}
