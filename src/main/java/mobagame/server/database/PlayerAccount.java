package mobagame.server.database;

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
}
