package mobagame.launcher.game;

import mobagame.core.game.Game;
import mobagame.core.game.maps.MainMap;

public class ClientGame extends Game {

	public ClientGame(MainMap mainMap) {
		super(mainMap);
	}

	public ClientGame() {
		super();
	}
	public ClientGame(int gameID) {
		this.gameID = gameID;
	}

	@Override
	public void update() {

	}

}
