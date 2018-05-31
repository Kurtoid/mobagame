package mobagame.launcher;

import java.awt.*;
import java.awt.image.RGBImageFilter;

public class TeamColorFilter extends RGBImageFilter {
	public final int black = 0xFF000000;
	static Color teamColor;
	public TeamColorFilter(Color c){
		teamColor = c;
	}
	@Override
	public int filterRGB(int x, int y, int rgb) {
		if((rgb) == black){
			return teamColor.getRGB();
		}else{
			return rgb;
		}
	}
}
