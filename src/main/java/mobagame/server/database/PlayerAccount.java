package mobagame.server.database;

import java.awt.Component;
import java.util.*;

/**
 * represents a signed-up player stored in the database
 *
 * @author Kurt Wilson
 *
 */
public class PlayerAccount {
	public String username;
	public int id;
	public int level;
	public PlayerAccount(){

	}

	/**
	 * only for debug use
	 * @param name
	 */
	public PlayerAccount(String name){
		username = name;
	}

	public String getUsername() {
		return username;
	}

	public String getKDARatio() {
//		return kills + "/" + deaths + "/" + assists;
		return "0/0/0";
	}

	public String getWLRatio() {
//		return wins + "/" + loses;
		return "0/0";
	}

	public String getFavoritChar() {
//		return favChar.getName;
		return "Reaper";
	}
}
