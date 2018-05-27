package mobagame.core.game;

public class Tower extends GameObject{
	public TowerType type;
	int health;
	public  enum TowerType{
		CORE, RESPAWN, NORMAL
	}
	public Tower(int i) {
		super();
		health = i;
	}
}
