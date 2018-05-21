package mobagame.launcher;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;

public interface MobaGameLauncher {

	public final Dimension SCREEN_SIZE = Toolkit.getDefaultToolkit().getScreenSize();

	public final int WINDOW_HEIGHT = SCREEN_SIZE.height * 4 / 7;
	public final int WINDOW_WIDTH = (int) (WINDOW_HEIGHT * 1.875);
	
	public final static String GAME_NAME = "[INSERT AWESOME GAME NAME HERE]";	

	// setFonts
	public final int FONT_SIZE = (int) ((WINDOW_WIDTH / 90) * 1.5);
	public final static String FONT = "Old English Text MT";
	public final Font MENU_FONT = new Font(FONT, Font.PLAIN, FONT_SIZE);
	public final Font GAME_FONT = new Font("Times New Roman", Font.BOLD, (int) (SCREEN_SIZE.getWidth() / 100));
	public final Font CHAT_FONT = new Font("Times New Roman", Font.PLAIN, (int) (SCREEN_SIZE.getWidth() / 100 * 3 / 2));

}
