package mobagame.launcher;

import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import mobagame.core.game.Item;

public class Shop extends JFrame {
//DNW
	private Item[] items;

	private Dimension SCREEN_SIZE = getToolkit().getScreenSize();
	
	public Shop() {
		super("Shop");
		
		JPanel panel = new JPanel();
		int x = 10;
		items = new Item[x];
		addToItems(new Item("knife", "resources/Items/knife.png", 500, 0, false), x);
		for (Item y: items){
			panel.add(new MyCanvas(y.getImageLocation(), 32));
		}
		JScrollPane chat = new JScrollPane(panel);
		chat.setSize(SCREEN_SIZE.height - SCREEN_SIZE.height/100, SCREEN_SIZE.width/3);
		chat.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		add(chat);
	}

	private void addToItems(Item item, int x) {
		if(x > 0) {
			items[x-1] = item;
			addToItems(item, x-1);
		}
	}
	
	public static void main(String[] args) {
		new Shop();
	}

}
