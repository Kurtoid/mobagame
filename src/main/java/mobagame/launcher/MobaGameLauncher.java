package mobagame.launcher;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.border.Border;

import mobagame.core.game.Item;
import mobagame.core.game.ItemType;

public interface MobaGameLauncher {

	public final Dimension SCREEN_SIZE = Toolkit.getDefaultToolkit().getScreenSize();

	public final int WINDOW_HEIGHT = SCREEN_SIZE.height * 4 / 7;
	public final int WINDOW_WIDTH = (int) (WINDOW_HEIGHT * 1.875);

	public final static String GAME_NAME = "[INSERT AWESOME GAME NAME HERE]";

	// make borders
	public final static Border red = BorderFactory.createLineBorder(Color.RED, 1);
	public final static Border orange = BorderFactory.createLineBorder(Color.ORANGE, 1);
	public final static Border yellow = BorderFactory.createLineBorder(Color.YELLOW, 1);
	public final static Border green = BorderFactory.createLineBorder(Color.GREEN, 1);
	public final static Border blue = BorderFactory.createLineBorder(Color.BLUE, 1);
	public final static Border purple = BorderFactory.createLineBorder(Color.MAGENTA, 1);
	public final static Border raisedBevel = BorderFactory.createRaisedBevelBorder();
	public final static Border loweredBevel = BorderFactory.createLoweredBevelBorder();
	public final static Border frame = BorderFactory.createCompoundBorder(raisedBevel, loweredBevel);

	// set fonts
	public final int FONT_SIZE = (int) ((WINDOW_WIDTH / 90) * 1.5);
	public final static String FONT = "Old English Text MT";
	public final Font MENU_FONT = new Font(FONT, Font.PLAIN, FONT_SIZE);
	public final Font GAME_FONT = new Font("Times New Roman", Font.BOLD, (int) (SCREEN_SIZE.getWidth() / 100));
	public final Font CHAT_FONT = new Font("Times New Roman", Font.PLAIN, (int) (SCREEN_SIZE.getWidth() / 100 * 3 / 2));
}