package mobagame.core.game;

import java.awt.*;
import java.awt.geom.Point2D;

public abstract class GameObject {
	Point2D.Double pos = new Point2D.Double(0,0);
	// this can be null, or a team
	public Team team;
	public double getX(){
		return pos.getX();
	}
	public double getY(){
		return pos.getY();
	}

	public void setX(double v) {
		pos.x = v;
	}
	public void setY(double i){
		pos.y = i;
	}
}
