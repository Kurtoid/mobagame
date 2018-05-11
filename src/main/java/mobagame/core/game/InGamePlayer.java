package mobagame.core.game;

public class InGamePlayer {
	int playerID;
	private double x;
	private double y;
	public double getX() {
		return x;
	}
	public double getY() {
		return y;
	}
	
	public int getPlayerID() {
		return playerID;
	}
	
	public InGamePlayer(int playerID) {
		this.playerID = playerID;
	}
}
