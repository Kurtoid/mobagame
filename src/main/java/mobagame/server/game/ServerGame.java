package mobagame.server.game;

import mobagame.core.game.Game;
import mobagame.core.game.InGamePlayer;
import mobagame.core.game.maps.MainMap;

public class ServerGame extends Game {

	public ServerGame(MainMap m) {
		this.map = m;
	}
	@Override
	public void update() {
//		System.out.println("updating game");
		for (InGamePlayer player : players) {
			player.mover.update();
		}
	}

}
